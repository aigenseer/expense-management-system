package de.dhbw.ems.application;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.cleanproject.abstractioncode.valueobject.money.Money;
import de.dhbw.ems.application.booking.BookingApplicationService;
import de.dhbw.ems.application.booking.BookingAttributeData;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest()
@ComponentScan("de.dhbw")
public class BookingApplicationServiceTest {

    @Autowired
    private BookingApplicationService applicationService;

    private final Booking entity1 = Booking.builder()
            .id(UUID.fromString("12345678-1234-1234-a123-123456789031"))
            .financialLedger(FinancialLedger.builder().build())
            .title("Example-Booking")
            .build();

    private final Booking entity2 = Booking.builder()
            .id(UUID.fromString("12345678-1234-1234-a123-123456789032"))
            .title("Example-Booking-2")
            .build();

    private final BookingAttributeData attributeData = BookingAttributeData.builder()
            .title("NewTitle")
            .money(new Money(19.99, CurrencyType.EURO))
            .bookingCategory(null)
            .build();

    @Test
    public void testSave() {
        applicationService.save(entity2);
        Optional<Booking> result = applicationService.findById(entity2.getId());
        assertTrue(result.isPresent());
        checkEntity(entity2, result.get());
    }

    @Test
    public void testDeleteById() {
        applicationService.deleteById(entity1.getId());
        Optional<Booking> result = applicationService.findById(entity1.getId());
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindById(){
        Optional<Booking> result = applicationService.findById(entity1.getId());
        assertTrue(result.isPresent());
        checkEntity(entity1, result.get());
    }

    private void checkEntity(Booking expectedEntity, Booking actualEntity){
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getTitle(), actualEntity.getTitle());
    }

    @Test
    public void testCreateByAttributeData() {
        Optional<Booking> optionalBooking = applicationService.createByAttributeData(entity1.getUser(), entity1.getFinancialLedger(), attributeData);
        assertTrue(optionalBooking.isPresent());
        assertEquals(entity1.getUser(), optionalBooking.get().getUser());
        assertEquals(entity1.getFinancialLedger().getId(), optionalBooking.get().getFinancialLedgerId());
    }

    @Test
    public void testUpdateByAttributeData() {
        Optional<Booking> optionalBooking = applicationService.updateByAttributeData(entity1, attributeData);
        assertTrue(optionalBooking.isPresent());
        checkAttributeData(attributeData, optionalBooking.get());
        optionalBooking = applicationService.findById(entity1.getId());
        assertTrue(optionalBooking.isPresent());
        checkAttributeData(attributeData, optionalBooking.get());
    }

    private void checkAttributeData(BookingAttributeData attributeData, Booking actualEntity) {
        assertEquals(attributeData.getTitle(), actualEntity.getTitle());
        assertEquals(attributeData.getMoney(), actualEntity.getMoney());
        assertEquals(attributeData.getBookingCategory(), actualEntity.getCategory());
    }

}
