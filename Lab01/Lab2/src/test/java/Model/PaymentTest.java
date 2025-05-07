package Model;

import org.junit.jupiter.api.Test;
import pizzashop.model.*;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    public void testConstructorAndGetters() {
        Payment payment = new Payment(5, PaymentType.CASH, 45.0);

        assertEquals(5, payment.getTableNumber());
        assertEquals(PaymentType.CASH, payment.getType());
        assertEquals(45.0, payment.getAmount(), 0.001);
    }

    @Test
    public void testSetters() {
        Payment payment = new Payment(1, PaymentType.CARD, 10.0);

        payment.setTableNumber(3);
        payment.setType(PaymentType.CASH);
        payment.setAmount(99.99);

        assertEquals(3, payment.getTableNumber());
        assertEquals(PaymentType.CASH, payment.getType());
        assertEquals(99.99, payment.getAmount(), 0.001);
    }

    @Test
    public void testToString() {
        Payment payment = new Payment(7, PaymentType.CASH, 20.0);
        String expected = "7,CASH,20.0";
        assertEquals(expected, payment.toString());
    }
}
