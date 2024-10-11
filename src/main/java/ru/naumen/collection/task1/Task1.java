package ru.naumen.collection.task1;

import java.util.HashMap;
import java.util.Map;

/**
 * Дано:
 * <pre>
 * public class Ticket {
 *     private long id;
 *     private String client;
 *     …
 * }</pre>
 * <p>Разработать программу для бармена в холле огромного концертного зала.
 * Зрители в кассе покупают билет (класс Ticket), на котором указан идентификатор билета (id) и имя зрителя.
 * При этом, есть возможность докупить наборы разных товаров (комбо-обед): нет товаров, напитки, еда и напитки.
 * Доп. услуги оформляются через интернет и привязываются к билету, но хранятся отдельно от билета
 * (нельзя добавить товары в класс Ticket).</p>
 * <p>Бармен сканирует билет и получает объект Ticket. По этому объекту нужно уметь
 * находить необходимые товары по номеру билета. И делать это нужно очень быстро,
 * ведь нужно как можно быстрее всех накормить.</p>
 * <p>
 * См. {@link Ticket}
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Task1
{
    public enum Goods {
        /**
         * нет товаров
         */
        EMPTY,
        /**
         * напитки
         */
        DRINKS,
        /**
         * еда и напитки
         */
        FOOD_AND_DRINKS
    }
    // HashMap хранит пары ключ значение, а также обеспечивает сложность получения и вставки элемента O(1),
    // что идеально подходит под нашу задачу
    private Map<Long, Goods> tickets = new HashMap<>();

    /**
     * Получить товары по билету
     * <p>Сложность алгоритма O(1)</p>
     *
     * <p><b>Мы не забыли определить equals и hashcode у класса {@link Ticket}</b></p>
     * <p>Достаточно их определить только для id, т.к. он уникален</p>
     */
    public Goods getGoods(Ticket ticket) {
        // O(1) обращение по индексу с помощью хэш кода, который для Long переопределен
        return tickets.get(ticket.getId());
    }

    public static void main(String[] args) {
        Task1 task1 = new Task1();
        Ticket ticket1 = new Ticket(1,"Petya");
        Ticket ticket2 = new Ticket(2,"Sasha");
        task1.tickets.put(ticket1.getId(), Goods.DRINKS);
        task1.tickets.put(ticket2.getId(), Goods.EMPTY);
        System.out.println(task1.getGoods(ticket1));
    }
}
