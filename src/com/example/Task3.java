package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class MyObjects {
    volatile int incrementInteger = 0;
    final Object monitor = new Object();

    final ReentrantLock lock = new ReentrantLock();


    volatile List<String> strings = new ArrayList<>();
    volatile List<Integer> integers = new ArrayList<>();
}

class IncrementIntegerThread implements Runnable {
    MyObjects res;


    IncrementIntegerThread(MyObjects res) {
        this.res = res;
    }

    @Override
    public void run() {
        res.lock.lock();
        try {
            res.incrementInteger++;
            System.out.println("Инкрементация числа: " + res.incrementInteger);

            res.strings.add(String.format("Bob %d", res.incrementInteger));
            System.out.println("Список строк: \n" + res.strings);

            res.integers.add(res.incrementInteger);
            System.out.println("Список чисел: \n" + res.integers);

            System.out.println("Поиск элемента по параметру строка: " + res.strings.contains("Bob 15"));

            int elementToSearch = 13;
            boolean isElementExist = searchElementsIntegers(elementToSearch);
            if (isElementExist) {
                System.out.println("В списке содержиться число: " + elementToSearch);
            } else {
                System.out.printf("Число со значением %d не найдено\n", elementToSearch);
            }

            averageValue();

            summ();

            System.out.println("----------------------------------------------------------------");

        } finally {
            res.lock.unlock();
        }


    }

    private void averageValue() {
        OptionalDouble average = res.integers.stream().mapToInt(e -> e).average();
        if (average.isPresent()) {
            System.out.println("Среднее значение всех элементов из коллекции: " + average.getAsDouble());
        }
    }

    private void summ() {
        int sum = 0;
        for (int i : res.integers) {
            sum += i;
        }
        System.out.println("Сумма всех элементов из коллекции: " + sum);
    }

    private boolean searchElementsIntegers(int elementToSearch) {
        boolean isElementExist = false;
        for (int i = 0; i < res.integers.size(); i++) {
            if (res.integers.get(i) == elementToSearch) {
                isElementExist = true;
                break;
            }
        }
        return isElementExist;
    }

}

public class Task3 {
    static final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

    public static void main(String[] args) {
        MyObjects commonObject = new MyObjects();
        for (int i = 0; i < 20; i++) {
            executor.execute(new IncrementIntegerThread(commonObject));
        }
    }
}
