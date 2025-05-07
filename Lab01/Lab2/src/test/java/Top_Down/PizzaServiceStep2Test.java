package Top_Down;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class PizzaServiceStep2Test {

    private PizzaService service;
    private PaymentRepository paymentRepoSpy;

    @BeforeEach
    public void setUp() {
        PaymentRepository realRepo = new PaymentRepository();
        paymentRepoSpy = spy(realRepo); // repo real dar supravegheat
        service = new PizzaService(new MenuRepository(), paymentRepoSpy);
    }

    @Test
    public void testRetrievePayments_step2() {
        // pregătim dummy payments
        List<Payment> dummyList = Arrays.asList(
                new Payment(1, PaymentType.CASH, 10.0),
                new Payment(2, PaymentType.CARD, 20.0)
        );

        // returnăm dummy list din spy
        doReturn(dummyList).when(paymentRepoSpy).getAll();

        List<Payment> payments = service.getPayments();
        assertEquals(2, payments.size());
    }

    @Test
    public void testTotalAmount_step2() {
        List<Payment> dummyList = Arrays.asList(
                new Payment(3, PaymentType.CASH, 5.0),
                new Payment(4, PaymentType.CASH, 7.5),
                new Payment(5, PaymentType.CARD, 100.0)
        );

        doReturn(dummyList).when(paymentRepoSpy).getAll();

        double total = service.getTotalAmount(PaymentType.CASH);
        assertEquals(12.5, total, 0.001);
    }
}
