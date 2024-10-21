package ru.oop.task1;

/**
 * Машина
 */
public interface Car {

    /**
     * Везет переданного человека в указанное место
     */
    void moveTo(Person person, Position position);
}
