package ru.oop.task2;

/**
 * Автобус
 */
public class Bus implements Transport {

    /**
     * Создает автобус с заданным пассажиром
     */
    public Bus(Person person) {

    }

    /**
     * Создает автобус с заданным пассажиром и номером
     */
    public Bus(Person person, String number) {

    }

    /**
     * Ехать в указанное местоположение
     */
    @Override
    public void moveTo(Position destination, Person person) {

    }

    /**
     * Возвращает текущую позицию автобуса
     */
    @Override
    public Position getPosition() {
        return null;
    }
}
