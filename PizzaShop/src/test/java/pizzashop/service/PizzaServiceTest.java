package pizzashop.service;

import org.junit.jupiter.api.*;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PizzaServiceTest {

    private PizzaService pizzaService;
    private FakePaymentRepository fakeRepo;

    static class FakePaymentRepository extends PaymentRepository {
        private final List<Payment> paymentList = new ArrayList<>();

       @Override
        public void add(Payment payment) {
            if (payment.getTableNumber() < 1 || payment.getTableNumber() > 8) {
                throw new IllegalArgumentException("Table number out of bounds");
            }
            if (payment.getAmount() <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }
            if (payment.getType() == null) {
                throw new NullPointerException("Payment type cannot be null");
            }
            paymentList.add(payment);
        }

        @Override
        public List<Payment> getAll() {
            return paymentList;
        }
    }

    @BeforeEach
    void setUp() {
        fakeRepo = new FakePaymentRepository();
        pizzaService = new PizzaService(new MenuRepository(), fakeRepo);
    }

    @Nested
    @Tag("BVA")
    class BvaTests {

        @Test
        @DisplayName("BVA Valid: Table = 1")
        void testAddPayment_TableMin() {
            // Arrange
            int table = 1;
            PaymentType type = PaymentType.CASH;
            double amount = 10.0;

            // Act
            pizzaService.addPayment(table, type, amount);

            // Assert
            assertEquals(1, fakeRepo.getAll().size());
        }

        @Test
        @DisplayName("BVA Valid: Table = 8")
        void testAddPayment_TableMax() {
            // Arrange
            int table = 8;
            PaymentType type = PaymentType.CARD;
            double amount = 5.0;

            // Act
            pizzaService.addPayment(table, type, amount);

            // Assert
            assertEquals(1, fakeRepo.getAll().size());
        }

        @Test
        @DisplayName("BVA Invalid: Table = 0")
        void testAddPayment_TableTooLow() {
            // Arrange
            int table = 0;

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () ->
                    pizzaService.addPayment(table, PaymentType.CARD, 10.0));
        }

        @Test
        @DisplayName("BVA Invalid: Table = 9")
        void testAddPayment_TableTooHigh() {
            // Arrange
            int table = 9;

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () ->
                    pizzaService.addPayment(table, PaymentType.CARD, 10.0));
        }
    }

    @Nested
    @Tag("ECP")
    class EcpTests {

        @Test
        @DisplayName("ECP Valid: CARD, 50.0")
        void testAddPayment_ValidCARD() {
            // Arrange
            int table = 7;
            PaymentType type = PaymentType.CARD;
            double amount = 50.0;

            // Act
            pizzaService.addPayment(table, type, amount);

            // Assert
            assertEquals(1, fakeRepo.getAll().size());
            assertEquals(PaymentType.CARD, fakeRepo.getAll().get(0).getType());
        }

        @Test
        @DisplayName("ECP Valid: CARD, 0.01")
        void testAddPayment_MinAmountPositive() {
            // Arrange
            int table = 5;
            double amount = 0.01;

            // Act
            pizzaService.addPayment(table, PaymentType.CARD, amount);

            // Assert
            assertEquals(1, fakeRepo.getAll().size());
            assertEquals(amount, fakeRepo.getAll().get(0).getAmount());
        }

        @Test
        @DisplayName("ECP Invalid: Amount = 0")
        void testAddPayment_ZeroAmount() {
            // Arrange
            int table = 5;
            double amount = 0.0;

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () ->
                    pizzaService.addPayment(table, PaymentType.CASH, amount));
        }

        @Test
        @DisplayName("ECP Invalid: PaymentType = null")
        void testAddPayment_NullType() {
            // Arrange
            int table = 5;
            PaymentType type = null;

            // Act & Assert
            assertThrows(NullPointerException.class, () ->
                    pizzaService.addPayment(table, type, 20.0));
        }
    }
    @Nested
    @Tag("WBT") // White-box testing
    class GetTotalAmountTests {

        @Test
        @DisplayName("Null list should throw IllegalArgumentException")
        void testNullListThrowsException() {
            // Replace the repo with a version that returns null
            PizzaService serviceWithNullList = new PizzaService(new MenuRepository(), new PaymentRepository() {
                @Override
                public List<Payment> getAll() {
                    return null;
                }

                @Override
                public void add(Payment payment) { }
            });

            assertThrows(IllegalArgumentException.class, () -> {
                serviceWithNullList.getTotalAmount(PaymentType.CASH);
            });
        }

        @Test
        @DisplayName("Empty list returns 0.0")
        void testEmptyListReturnsZero() {
            assertEquals(0.0, pizzaService.getTotalAmount(PaymentType.CASH));
        }

        @Test
        @DisplayName("List with no matching type returns 0.0")
        void testNoMatchReturnsZero() {
            fakeRepo.add(new Payment(5, PaymentType.CARD, 20.0));
            fakeRepo.add(new Payment(3, PaymentType.CARD, 30.0));
            assertEquals(0.0, pizzaService.getTotalAmount(PaymentType.CASH));
        }

        @Test
        @DisplayName("List with one matching type returns correct value")
        void testSingleMatchReturnsAmount() {
            fakeRepo.add(new Payment(1, PaymentType.CASH, 15.0));
            fakeRepo.add(new Payment(2, PaymentType.CARD, 40.0));
            assertEquals(15.0, pizzaService.getTotalAmount(PaymentType.CASH));
        }

        @Test
        @DisplayName("List with multiple matching types returns their sum")
        void testMultipleMatchesReturnSum() {
            fakeRepo.add(new Payment(1, PaymentType.CARD, 10.0));
            fakeRepo.add(new Payment(2, PaymentType.CARD, 20.0));
            fakeRepo.add(new Payment(3, PaymentType.CASH, 30.0));
            assertEquals(30.0, pizzaService.getTotalAmount(PaymentType.CARD));
        }

        @Test
        @DisplayName("All payments match the type")
        void testAllMatchReturnsTotal() {
            fakeRepo.add(new Payment(1, PaymentType.CASH, 5.0));
            fakeRepo.add(new Payment(2, PaymentType.CASH, 10.0));
            assertEquals(15.0, pizzaService.getTotalAmount(PaymentType.CASH));
        }
    }

}