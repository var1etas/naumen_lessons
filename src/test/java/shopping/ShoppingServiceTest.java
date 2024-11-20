package shopping;

import customer.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import product.Product;
import product.ProductDao;

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
     * Тест получения корзины покупателя
     */
    @Test
    public void getCartTest(){
        cart.add(product, 1);

        Cart customerCart = shoppingService.getCart(customer);

        Assertions.assertTrue(customerCart.getProducts().containsKey(product));
        Assertions.assertEquals(cart.getProducts().size(), customerCart.getProducts().size());
    }

    /**
     * Тест успешного получения всех продуктов
     */
    @Test
    public void getAllProductsTest_Success(){
    }

    /**
     * Тест успешного получения продуктов по названию
     */
    @Test
    public void getProductByNameTest(){
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
    public void buyProductTest_BuyException() throws BuyException {
        cart.add(product, 1);
        cart.add(product, 1);

        Assertions.assertTrue(shoppingService.buy(cart));
        Assertions.assertTrue(shoppingService.buy(cart));
        Assertions.assertThrows(BuyException.class, () -> shoppingService.buy(cart),
                "В наличии нет необходимого количества товара " + product.getName());

        Mockito.verify(productDao, Mockito.times(2)).save(product);
    }

    /**
     * Тест на отсутствие товаров в корзине, если товары уже купили
     */
    @Test
    public void buyProductTest_CartProducts() throws BuyException {
        Assertions.assertTrue(cart.getProducts().isEmpty());
        cart.add(product, 1);
        Assertions.assertEquals(1, cart.getProducts().size());
        shoppingService.buy(cart);
        Assertions.assertEquals(0, cart.getProducts().size());
    }

    /**
     * Тест на уменьшение кол-ва товаров в корзине после покупки
     */
    @Test
    public void buyProductTest_ProductCount() throws BuyException {
        Assertions.assertTrue(cart.getProducts().isEmpty());
        cart.add(product, 1);
        Assertions.assertEquals(1, cart.getProducts().get(product));
        shoppingService.buy(cart);
        Assertions.assertEquals(0, cart.getProducts().get(product));
    }

    /**
     * Тест на покупку отрицательного кол-ва продуктов
     */
    @Test
    public void buyProductTest_BadIncrease() throws BuyException {
        Assertions.assertEquals(2, product.getCount());

        cart.add(product, -3);
        shoppingService.buy(cart);

        Assertions.assertEquals(2, product.getCount());
    }
}

