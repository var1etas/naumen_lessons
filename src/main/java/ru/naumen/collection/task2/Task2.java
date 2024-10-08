package ru.naumen.collection.task2;

import java.util.*;

/**
 * Дано:
 * <pre>
 * public class User {
 *     private String username;
 *     private String email;
 *     private byte[] passwordHash;
 *     …
 * }
 * </pre>
 * Нужно реализовать метод
 * <pre>
 * public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB);
 * </pre>
 * <p>который возвращает дубликаты пользователей, которые есть в обеих коллекциях.</p>
 * <p>Одинаковыми считаем пользователей, у которых совпадают все 3 поля: username,
 * email, passwordHash. Дубликаты внутри коллекций collA, collB можно не учитывать.</p>
 * <p>Метод должен быть оптимален по производительности.</p>
 * <p>Пользоваться можно только стандартными классами Java SE.
 * Коллекции collA, collB изменять запрещено.</p>
 *
 * См. {@link User}
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Task2
{
    public static void main(String[] args) {
        List<User> firstList = new ArrayList<>();
        List<User> secondList = new ArrayList<>();
        User user1 = new User("Petya", "gmail", "password1".getBytes());
        User user2 = new User("Petya", "gmail", "password2".getBytes());
        User user3 = new User("Petya", "gmail", "password3".getBytes());
        User user4 = new User("Petya", "gmail", "password1".getBytes());
        firstList.add(user1);
        firstList.add(user2);
        secondList.add(user3);
        secondList.add(user4);
        System.out.println(findDuplicates(firstList, secondList));
    }

    /**
     * Возвращает дубликаты пользователей, которые есть в обеих коллекциях
     */
    public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB) {
        List<User> dublicatesList = new ArrayList<>();
        // копия коллекции O(n)
        Set<User> userSet = new HashSet<>(collA);
        // O(n) - бежим по всей коллекции
        for(User u : collB){
            // contains - O(1) - т.к. обращение по хэш коду,
            // а его вычисление мы переопределили в классе User
            if(userSet.contains(u)){
                // добавление в список O(1)
                dublicatesList.add(u);
            }
        }
        return dublicatesList;
    }
}
