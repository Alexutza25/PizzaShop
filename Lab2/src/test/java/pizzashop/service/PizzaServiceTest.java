package pizzashop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PizzaServiceTest {

    private PaymentType type;
    private PizzaService pizzaService;
    @BeforeEach
    void setUp() {
        type = PaymentType.CARD; // sau orice alt tip valid din enumul tău
        pizzaService = new PizzaService() {
            @Override
            public List<Payment> getPayments() {
                return Arrays.asList(
                        new Payment(2,PaymentType.CARD, 10.5),
                        new Payment(3,PaymentType.CASH, 7.0),
                        new Payment(2,PaymentType.CARD, 5.0)
                );
            }
        };
    }  void tearDown() {
        pizzaService = null;
        type = null;
    }

    @Test
    void getTotalAmount() {
        double expected = 15.5;
        double actual = pizzaService.getTotalAmount(type);
        assertEquals(expected, actual, 0.001);
    }

    @Test
    void testGetTotalAmount_ValidCARD_ECP() {
        double expected = 15.5; // 10.5 + 5.0 pentru CARD
        double actual = pizzaService.getTotalAmount(PaymentType.CARD);
        assertEquals(expected, actual, 0.001);
    }

    @Test
    void testGetTotalAmount_ValidCASH_ECP() {
        double expected = 7.0; // doar una pentru CASH
        double actual = pizzaService.getTotalAmount(PaymentType.CASH);
        assertEquals(expected, actual, 0.001);
    }

    @Test
    void testGetTotalAmount_NullType_ECP() { //am luat null ca o margine inferioara, adica 0
        double result = pizzaService.getTotalAmount(null);
        assertEquals(0.0,result, 0.001);
    }

    @Test
    void testGetTotalAmount_InvalidEnum_ECP() {
        // Simulare indirectă: testăm un tip care nu există în listă (ex: dacă adăugăm PaymentType.VOUCHER, dar nu e în plăți)
        PaymentType fakeType = PaymentType.valueOf("CARD"); // presupunem că nu e folosit în listă
        // Schimbăm lista pentru acest test:
        pizzaService = new PizzaService() {
            @Override
            public List<Payment> getPayments() {
                return Arrays.asList(
                        new Payment(1, PaymentType.CASH, 10.0) // doar CASH
                );
            }
        };
        double actual = pizzaService.getTotalAmount(PaymentType.CARD); // CARD nu apare
        assertEquals(0.0, actual, 0.001);
    }

    @Test
    void testGetTotalAmount_MinValue_BVE_Valid() {
        pizzaService = new PizzaService() {
            @Override
            public List<Payment> getPayments() {
                return Arrays.asList(
                        new Payment(1, PaymentType.CARD, 0.0)
                );
            }
        };
        double actual = pizzaService.getTotalAmount(PaymentType.CARD);
        assertEquals(0.0, actual, 0.001);
    }

    @Test
    void testGetTotalAmount_MaxValue_BVE_Valid() {
        pizzaService = new PizzaService() {
            @Override
            public List<Payment> getPayments() {
                return Arrays.asList(
                        new Payment(1, PaymentType.CARD, 99999.99)
                );
            }
        };
        double actual = pizzaService.getTotalAmount(PaymentType.CARD);
        assertEquals(99999.99, actual, 0.001);
    }



    @Test
    void testGetTotalAmount_NegativeValue_BVE_Invalid() {
        pizzaService = new PizzaService() {
            @Override
            public List<Payment> getPayments() {
                return Arrays.asList(
                        new Payment(1, PaymentType.CARD, -10.0)
                );
            }
        };
        double actual = pizzaService.getTotalAmount(PaymentType.CARD);
        assertEquals(-10.0, actual, 0.001);
    }

    @Test
    void testGetTotalAmount_TypeNull_BVE_Invalid() { //am luat null ca un tip invalid
        double result = pizzaService.getTotalAmount(null);
        assertEquals(0.0,result, 0.001);    }

}