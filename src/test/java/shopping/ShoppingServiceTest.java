package shopping;

import customer.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import product.Product;
import product.ProductDao;

import java.util.List;

/**
 * Тесты сервиса ShoppingService
 */
public class ShoppingServiceTest {

    private final ProductDao productDao = Mockito.mock(ProductDao.class);

    private final ShoppingService shoppingService = new ShoppingServiceImpl(productDao);

    private Product product;
    private Customer customer;
    private Cart cart;

    @BeforeEach
    public void setUp() {
        customer = new Customer(1, "+780005553535");
        product = new Product("milk", 2);
        cart = new Cart(customer);
    }

    /**
     * Тест получения корзины
     */
    @Test
    public void getCartTest(){
        Cart cart = shoppingService.getCart(customer);

        Assertions.assertTrue(cart.getProducts().keySet().isEmpty());
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
        cart.add(product, 1);

        Assertions.assertTrue(shoppingService.buy(cart));
        Mockito.verify(productDao, Mockito.times(1)).save(product);
    }

    /**
     * Тест покупки продуктов, которых нет в корзине - покупка не состоится
     */
    @Test
    public void buyProductTest_EmptyCart() throws BuyException {
        Assertions.assertTrue(cart.getProducts().isEmpty());
        Assertions.assertEquals(2, product.getCount());
        Assertions.assertFalse(shoppingService.buy(cart));
    }

    /**
     * Тест выбрасывания исключения, если товар закончился
     */
    @Test
    public void buyProductTest_BuyException() throws BuyException {cart.add(product, 1);
        cart.add(product, 1);

        Assertions.assertTrue(shoppingService.buy(cart));
        Assertions.assertTrue(shoppingService.buy(cart));
        Assertions.assertThrows(BuyException.class, () -> shoppingService.buy(cart),
                "В наличии нет необходимого количества товара " + product.getName());

        Mockito.verify(productDao, Mockito.times(2)).save(product);
    }
}

