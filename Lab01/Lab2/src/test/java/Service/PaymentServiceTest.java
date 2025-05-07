package Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    private PaymentRepository mockPaymentRepo;
    private MenuRepository mockMenuRepo;
    private PizzaService service;

    @BeforeEach
    public void setUp() {
        mockPaymentRepo = mock(PaymentRepository.class);
        mockMenuRepo = mock(MenuRepository.class);
        service = new PizzaService(mockMenuRepo, mockPaymentRepo);
    }

    @Test
    public void testAddPayment() {
        service.addPayment(1, PaymentType.CASH, 20.0);
        verify(mockPaymentRepo, times(1)).add(any(Payment.class));
    }

    @Test
    public void testGetPayments() {
        Payment p1 = new Payment(1, PaymentType.CARD, 30.0);
        when(mockPaymentRepo.getAll()).thenReturn(Collections.singletonList(p1));

        List<Payment> payments = service.getPayments();
        assertEquals(1, payments.size());
        assertEquals(PaymentType.CARD, payments.get(0).getType());
    }

    @Test
    public void testGetTotalAmount() {
        Payment p1 = new Payment(1, PaymentType.CASH, 10.0);
        Payment p2 = new Payment(2, PaymentType.CASH, 5.5);
        Payment p3 = new Payment(3, PaymentType.CARD, 100.0);

        when(mockPaymentRepo.getAll()).thenReturn(Arrays.asList(p1, p2, p3));

        double total = service.getTotalAmount(PaymentType.CASH);
        assertEquals(15.5, total, 0.001);
    }

    @Test
    public void testGetTotalAmount_Null() {
        when(mockPaymentRepo.getAll()).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            service.getTotalAmount(PaymentType.CARD);
        });
    }
}
