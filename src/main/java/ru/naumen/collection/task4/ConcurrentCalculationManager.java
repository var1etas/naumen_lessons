package ru.naumen.collection.task4;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Класс управления расчётами
 */
public class ConcurrentCalculationManager<T> {
    // ArrayBlockingQueue помимо реализации принципов FIFO гарантирует стабильную работу с несколькими потоками,
    // т.к. под капотом операции put() и take() содержат ReentrantLock,
    // который блокирует доступ к изменению очереди остальным потокам
    BlockingQueue<Future<T>> queue = new ArrayBlockingQueue<>(3);

    /**
     * Добавить задачу на параллельное вычисление
     */
    public void addTask(Supplier<T> task) throws InterruptedException {
        Future<T> result = CompletableFuture.supplyAsync(task);
        // вставка в конец O(1)
        queue.put(result);
    }

    /**
     * Получить результат вычисления.
     * Возвращает результаты в том порядке, в котором добавлялись задачи.
     */
    public T getResult() throws InterruptedException, ExecutionException {
        // получение первого элемента O(n) - т.к. все элементы сдвигаются на один влево
        return queue.take().get();
    }
}