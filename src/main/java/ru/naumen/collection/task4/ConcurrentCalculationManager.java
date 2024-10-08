package ru.naumen.collection.task4;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Класс управления расчётами
 */
public class ConcurrentCalculationManager<T> {
    BlockingQueue<T> queue = new ArrayBlockingQueue<>(3);

    /**
     * Добавить задачу на параллельное вычисление
     */
    // добавление в конец очереди O(1)
    public void addTask(Supplier<T> task) throws InterruptedException {
        queue.put(task.get());
    }

    /**
     * Получить результат вычисления.
     * Возвращает результаты в том порядке, в котором добавлялись задачи.
     */
    // извлечение первого элемента - O(n) - т.к. все остальные элементы смещаются на один влево
    public T getResult() throws InterruptedException {
        return queue.take();
    }
}