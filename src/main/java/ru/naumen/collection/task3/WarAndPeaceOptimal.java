package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class WarAndPeaceOptimal {
    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");

    public static void main(String[] args) {
        Queue<Map.Entry<String, Integer>> top = new PriorityQueue<>(10, Map.Entry.comparingByValue());
        Queue<Map.Entry<String, Integer>> bottom = new PriorityQueue<>(10, Map.Entry.comparingByValue(Comparator.reverseOrder()));
        // LinkedHashMap хранит пары ключ-значение, обеспечивает вставку за O(1), а также быстро итерируется, т.к. хранит
        // ссылку на следующее значение
        Map<String, Integer> wordMap = new LinkedHashMap<>();
        long startTime = System.currentTimeMillis();
        new WordParser(WAR_AND_PEACE_FILE_PATH) // O(n) - проход по всем словам
                .forEachWord(word -> {
                    // put() и get() за O(1), т.к. у с String переопределён hashcode()
                    wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
                });
        // ArrayList, т.к. нам от него, кроме сортировки и получения по индексу ничего не надо
        // копирование в список - O(n), сортировка quickSort - O(n*log(n))
//        List<Map.Entry<String, Integer>> list = wordMap.entrySet().stream()
//                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).toList();

        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            if(top.size() > 9){
                if(entry.getValue() > top.peek().getValue()){
                    top.poll();
                    top.add(entry);
                }
            } else {
                top.add(entry);
            }
            if(bottom.size() > 9){
                if(entry.getValue() < bottom.peek().getValue()){
                    bottom.poll();
                    bottom.add(entry);
                }
            } else {
                bottom.add(entry);
            }
        }

        for(Map.Entry<String, Integer> entry : top) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }


        for(Map.Entry<String, Integer> entry : bottom) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Затраченное время в мс: " + (endTime-startTime));

    }
}
