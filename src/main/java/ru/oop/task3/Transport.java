package ru.oop.task3;

/**
 * Транспорт
 */
public interface Transport extends Positioned {

    /**
     * Ехать в указанное местоположение
     */
    void moveTo(Position destination);
}
