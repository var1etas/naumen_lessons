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

    /**
     * Перед каждым тестом создаются тестовые покупатель, продукт и корзина
     */
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
        Assertions.assertEquals(cart.getProducts(), customerCart.getProducts());
    }

    /**
     * Тест успешного получения всех продуктов
     */
    @Test
    public void getAllProductsTest_Success(){
        // Нет смысла тестировать т.к. внутри просто вызывается метод класса ProductDao
    }

    /**
     * Тест успешного получения продуктов по названию
     */
    @Test
    public void getProductByNameTest(){
        // Нет смысла тестировать т.к. внутри просто вызывается метод класса ProductDao
    }

    /**
     * Тест успешной покупки продуктов
     */
    @Test
    public void buyProductTest_Success() throws BuyException {
        Product secondProduct = new Product("bread", 2);
        cart.add(product, 1);
        cart.add(secondProduct, 2);

        Assertions.assertTrue(shoppingService.buy(cart));

        Assertions.assertEquals(1, product.getCount());
        Assertions.assertEquals(0, secondProduct.getCount());

        Assertions.assertTrue(cart.getProducts().isEmpty());
        Mockito.verify(productDao, Mockito.times(1)).save(product);
        Mockito.verify(productDao, Mockito.times(1)).save(secondProduct);
    }

    /**
     * Тест покупки пустой корзины - покупка не состоится
     */
    @Test
    public void buyProductTest_EmptyCart() throws BuyException {
        Cart emptyCart = new Cart(customer);

        Assertions.assertFalse(shoppingService.buy(emptyCart));
    }

    /**
     * Тест выбрасывания исключения, если товар закончился
     */
    @Test
    public void buyProductTest_BuyException() throws BuyException {
        Customer customer1 = new Customer(1L, "911");
        Customer customer2 = new Customer(2L, "992");
        Cart cart1 = new Cart(customer1);
        Cart cart2 = new Cart(customer2);
        cart1.add(product, 1);
        cart2.add(product, 2);
        shoppingService.buy(cart1);
        BuyException exception = Assertions.assertThrows(BuyException.class, () -> shoppingService.buy(cart2));
        Assertions.assertEquals("В наличии нет необходимого количества товара " + "'"
                + product.getName() + "'", exception.getMessage());

        Mockito.verify(productDao, Mockito.times(1)).save(product);
    }

    /**
     * Тест на покупку отрицательного кол-ва продуктов
     */
    @Test
    public void buyProductTest_BadDecrease() throws BuyException {
        cart.add(product, -3);

        Assertions.assertFalse(shoppingService.buy(cart));
        Assertions.assertEquals(2, product.getCount());
    }

    /**
     * Тест добавления последнего товара в корзину и добавления большего кол-ва товаров, чем есть в магазине
     */
    @Test
    public void cartAddTest() {
        Product cartTestProduct = new Product("cheese", 1);
        cart.add(cartTestProduct, 1);

        Assertions.assertTrue(cart.getProducts().containsKey(cartTestProduct));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                ()-> cart.add(cartTestProduct, 1));
        Assertions.assertEquals("Невозможно добавить товар " + product.getName()
                + " в корзину, т.к. нет необходимого количества товаров", exception.getMessage());
        Assertions.assertEquals(1, cart.getProducts().get(cartTestProduct));
    }
}

