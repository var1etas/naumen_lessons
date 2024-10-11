package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.*;

/**
 * <p>Написать консольное приложение, которое принимает на вход произвольный текстовый файл в формате txt.
 * Нужно собрать все встречающийся слова и посчитать для каждого из них количество раз, сколько слово встретилось.
 * Морфологию не учитываем.</p>
 * <p>Вывести на экран наиболее используемые (TOP) 10 слов и наименее используемые (LAST) 10 слов</p>
 * <p>Проверить работу на романе Льва Толстого “Война и мир”</p>
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class WarAndPeace
{
    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");

    public static void main(String[] args) {
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
        List<Map.Entry<String, Integer>> list = wordMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).toList();

        String[] top = new String[10];
        String[] last = new String[10];

        for(int i = 0; i < 10; i++){
            // получение по индексу O(1)
            top[i] = list.get(i).getKey();
        }

        int n = list.size();
        for(int i = 0; i < 10; i++){
            // получение по индексу O(1)
            last[i] = list.get(n-i-1).getKey();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Самые популярные слова: " + Arrays.toString(top));
        System.out.println("Самые редкие слова: " + Arrays.toString(last));
        System.out.println("Затраченное время в мс: " + (endTime - startTime));
    }
}