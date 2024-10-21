package ru.oop.task1;

/**
 * Транспортное средство
 */
public interface Transport {

    /**
     * Везет переданного человека в указанное место
     */
    void moveTo(Person person, Position position);
}
