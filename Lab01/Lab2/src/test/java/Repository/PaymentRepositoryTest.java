package Repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class PaymentRepositoryTest {

    private PaymentRepository repoSpy;


    @BeforeEach
    public void setUp() {
        PaymentRepository realRepo = new PaymentRepository(); // repo real
        repoSpy = spy(realRepo);
    }

    @Test
    public void testAdd_isCalled() {
        Payment payment = new Payment(5, PaymentType.CASH, 40.0);
        repoSpy.add(payment);

        verify(repoSpy, times(1)).add(payment);
    }

    @Test
    public void testGetAll_returnsCorrectData() {
        Payment p = new Payment(3, PaymentType.CARD, 30.0);
        repoSpy.add(p);

        List<Payment> payments = repoSpy.getAll();
        assertTrue(payments.contains(p));
    }

    @Test
    public void testGetPaymentFromLine_validInput() {
        String line = "2,CASH,25.0";
        Payment result = repoSpy.getPayment(line);

        assertNotNull(result);
        assertEquals(2, result.getTableNumber());
        assertEquals(PaymentType.CASH, result.getType());
        assertEquals(25.0, result.getAmount(), 0.001);

    }

}
