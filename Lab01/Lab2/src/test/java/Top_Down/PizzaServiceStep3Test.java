package Top_Down;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PizzaServiceStep3Test {

    private PizzaService service;

    @BeforeEach
    public void setUp() {
        // folosim fisier dedicat testului
        String testFile = "C:\\Users\\Alexandra\\Documents\\GitHub\\PizzaShop\\Lab01\\Lab2\\data\\test_payments.txt";

        // ștergem fișierul de test daca există deja
        File f = new File(testFile);
        if (f.exists()) {
            f.delete();
        }

        PaymentRepository repo = new PaymentRepository(testFile);
        MenuRepository menuRepo = new MenuRepository();
        service = new PizzaService(menuRepo, repo);
    }

    @Test
    public void testAddAndRetrievePayments_step3() {
        service.addPayment(1, PaymentType.CASH, 10.0);
        service.addPayment(2, PaymentType.CARD, 20.0);

        List<Payment> payments = service.getPayments();
        assertEquals(2, payments.size());
    }

    @Test
    public void testTotalAmount_step3() {
        service.addPayment(3, PaymentType.CASH, 5.0);
        service.addPayment(4, PaymentType.CASH, 7.5);
        service.addPayment(5, PaymentType.CARD, 100.0);

        double total = service.getTotalAmount(PaymentType.CASH);
        assertEquals(12.5, total, 0.001);
    }
}
