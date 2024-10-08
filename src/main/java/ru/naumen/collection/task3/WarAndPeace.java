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
        Map<String, Integer> wordMap = new HashMap<>();
        long startTime = System.currentTimeMillis();
            new WordParser(WAR_AND_PEACE_FILE_PATH) // O(n) - проход по всем словам
                    .forEachWord(word -> {
        // у contains сложность O(1), т.к. вычисляется хэш ключа и обращение идет сразу к конкретной ячейке
        if (wordMap.containsKey(word)) {
            // тут тоже O(1) вычисляется хэш и в полученную ячейку добавляется значение
            wordMap.put(word, wordMap.get(word) + 1);
        }
        else {
            wordMap.put(word, 1);
        }
        });

        String[] top = new String[10];
        String[] last = new String[10];
        // копирование в список - O(n)
        List<Map.Entry<String, Integer>> list = new ArrayList<>(wordMap.entrySet().stream().toList());
        // сортировка quickSort - O(n*log(n))
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        // get() в ArrayList имеет сложность O(1)
        for(int i = 0; i < 10; i++){
            top[i] = list.get(i).getKey();
        }

        int n = list.size();
        for(int i = 0; i < 10; i++){
            last[i] = list.get(n-i-1).getKey();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Самые популярные слова: " + Arrays.toString(top));
        System.out.println("Самые редкие слова: " + Arrays.toString(last));
        System.out.println("Затраченное время в секундах: " + (endTime - startTime) / 1000.0);
    }
}