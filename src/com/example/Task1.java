package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;


public class Task1 {

    private static List<String> strings = new ArrayList<>();
    private static List<Integer> integers = new ArrayList<>();

    public static void main(String[] args) {
        addElementToList();
        searElementInTheList();
        averageList();
        sum();
    }

    private static void addElementToList() {
        String lol = "котики";
        String bob = "Bob";
        strings.add(lol);
        strings.add(bob);

        for (int j = 0; j < strings.size(); j++) {
            System.out.println("Содержимое " + j + ": " + strings.get(j));
        }

        //Поиск элемента по параметру "строка"
        System.out.println(strings.contains(bob));

// Добавление элементов в список
        for (int i = 1; i < 12; i++) {
            integers.add(i);
        }

        System.out.println("Список чисел: \n" + integers);
    }

    //Поиск параметра "Число"
    private static void searElementInTheList() {
        for (int i = 1; i < integers.size(); i++) {
            if (integers.get(i) == 5) {
                System.out.println("Найден элемент: " + i);
            } else {
                System.out.println("Элемент не найден :c");
            }
        }
    }

    //Среднее значение
    private static void averageList() {
        OptionalDouble average = integers.stream().mapToInt(e -> e).average();
        if (average.isPresent()) {
            System.out.println("Среднее значение всех элементов из коллекции: " + average.getAsDouble());
        }
    }

    //Сумма всех элементов
    private static void sum() {
        int sum = 0;
        for (int i : integers) {
            sum += i;
        }
        System.out.println("Сумма всех элементов из коллекции: " + sum);
    }
}
