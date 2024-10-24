package ru.oop.task2;

/**
 * Транспорт
 */
public interface Transport extends Positioned {

    /**
     * Везет человека в указанное место
     */
    void moveTo(Position destination, Person person);
}
