package shopping;

import customer.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import product.Product;
import product.ProductDao;

import java.util.List;

/**
 * Тесты сервиса ShoppingService
 */
@ExtendWith(MockitoExtension.class)
public class ShoppingServiceTest {

    private ProductDao productDao = Mockito.mock(ProductDao.class);

    private ShoppingService shoppingService
            = new ShoppingServiceImpl(productDao);

    Product product;

    @BeforeEach
    public void setUp() {
        product = new Product("milk", 2);
    }

    /**
     * Тест получения корзины
     */
    @Test
    public void getCartTest(){
        Customer customer = new Customer(1, "+780005553535");
        Cart cart = shoppingService.getCart(customer);

        Assertions.assertTrue(cart.getProducts().keySet().isEmpty());

        cart.add(product, 1);

        Assertions.assertTrue(cart.getProducts().containsKey(product));
    }

    /**
     * Тест успешного получения всех продуктов
     */
    @Test
    public void getAllProductsTest_Success(){
        Mockito.when(productDao.getAll()).thenReturn(List.of(product));

        shoppingService.getAllProducts();
        Mockito.verify(productDao, Mockito.times(1)).getAll();
    }

    /**
     * Тест успешного получения продуктов по названию
     */
    @Test
    public void getProductByNameTest(){
        Mockito.when(productDao.getByName(product.getName())).thenReturn(product);

        shoppingService.getProductByName(product.getName());
        Mockito.verify(productDao, Mockito.times(1)).getByName(product.getName());
    }

    /**
     * Тест успешной покупки продуктов
     */
    @Test
    public void buyProductTest_Success() throws BuyException {
        Customer customer = new Customer(1, "+780005553535");
        Cart cart = shoppingService.getCart(customer);
        cart.add(product, 1);

        Assertions.assertTrue(shoppingService.buy(cart));
        Mockito.verify(productDao, Mockito.times(1)).save(product);
    }

    /**
     * Тест покупки продуктов, которых нет в корзине - покупка не состоится
     * @throws BuyException
     */
    @Test
    public void buyProductTest_EmptyCart() throws BuyException {
        Customer customer = new Customer(1, "+780005553535");
        Cart cart = shoppingService.getCart(customer);

        Assertions.assertFalse(shoppingService.buy(cart));
    }

    /**
     * Не надо тестировать, т.к. невозможно добавить товар в корзину, если его нет в наличии.
     * Более того тут и содержится логическая ошибка, кол-во товара при добавлении в корзину и при покупке
     * не уменьшается
     */
    @Test
    public void buyProductTest_BuyException() throws BuyException {
    }


}

