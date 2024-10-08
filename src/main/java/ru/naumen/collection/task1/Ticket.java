package ru.naumen.collection.task1;

/**
 * Билет
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Ticket {
    private long id;
    private String name;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Ticket(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ticket() {
        super();
    }
}
