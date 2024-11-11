package example.container;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты добавления и удаления элементов в классе Container
 */
public class ContainerTests {
    private Container container;

    @BeforeEach
    public void setUp() {
        container = new Container();
    }

    /**
     * Тест добавления элемента в контейнер
     */
    @Test
    public void addTest_Success(){
        Item item = new Item(1);

        Assertions.assertTrue(container.add(item));
        Assertions.assertTrue(container.contains(item));
        Assertions.assertEquals(container.get(0), item);
    }

    /**
     * Тест удаления элемента из контейнера
     */
    @Test
    public void removeTest_Success(){
        Item item = new Item(1);
        container.add(item);

        Assertions.assertTrue(container.remove(item));
        Assertions.assertFalse(container.contains(item));
        Assertions.assertEquals(0, container.size());
    }

    /**
     * Тест удаления элемента, которого нет в контейнере
     */
    @Test
    public void removeTest_Failure(){
        Item item1 = new Item(1);
        Item item2 = new Item(2);
        container.add(item1);

        Assertions.assertFalse(container.remove(item2));
        Assertions.assertEquals(1, container.size());
    }
}
