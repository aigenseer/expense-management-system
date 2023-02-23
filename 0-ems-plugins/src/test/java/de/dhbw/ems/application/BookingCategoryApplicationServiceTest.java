package de.dhbw.ems.application;

import de.dhbw.ems.application.bookingcategory.BookingCategoryApplicationService;
import de.dhbw.ems.application.bookingcategory.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
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
public class BookingCategoryApplicationServiceTest {

    @Autowired
    private BookingCategoryApplicationService applicationService;

    private final BookingCategory entity1 = BookingCategory.builder()
            .id(UUID.fromString("12345678-1234-1234-a123-123456789021"))
            .title("Example-Category-1")
            .build();

    private final BookingCategory entity2 = BookingCategory.builder()
            .id(UUID.fromString("12345678-1234-1234-a123-123456789022"))
            .title("Example-Category-2")
            .build();

    private final BookingCategoryAttributeData attributeData = BookingCategoryAttributeData.builder()
            .title("NewTitle")
            .build();

    @Test
    public void testSave() {
        applicationService.save(entity2);
        Optional<BookingCategory> result = applicationService.findById(entity2.getId());
        assertTrue(result.isPresent());
        checkEntity(entity2, result.get());
    }

    @Test
    public void testDeleteById() {
        applicationService.deleteById(entity1.getId());
        Optional<BookingCategory> result = applicationService.findById(entity1.getId());
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindById() {
        Optional<BookingCategory> result = applicationService.findById(entity1.getId());
        assertTrue(result.isPresent());
        checkEntity(entity1, result.get());
    }

    private void checkEntity(BookingCategory expectedEntity, BookingCategory actualEntity) {
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getTitle(), actualEntity.getTitle());
    }

    @Test
    public void testCreateByAttributeData() {
        Optional<BookingCategory> optionalBookingCategory = applicationService.createByAttributeData(entity1.getFinancialLedger(), attributeData);
        assertTrue(optionalBookingCategory.isPresent());
        checkAttributeData(attributeData, optionalBookingCategory.get());
        assertEquals(entity1.getFinancialLedger(), optionalBookingCategory.get().getFinancialLedger());
    }

    @Test
    public void testUpdateByAttributeData() {
        Optional<BookingCategory> optionalBookingCategory = applicationService.updateByAttributeData(entity1, attributeData);
        assertTrue(optionalBookingCategory.isPresent());
        checkAttributeData(attributeData, optionalBookingCategory.get());
        optionalBookingCategory = applicationService.findById(entity1.getId());
        assertTrue(optionalBookingCategory.isPresent());
        checkAttributeData(attributeData, optionalBookingCategory.get());
    }

    private void checkAttributeData(BookingCategoryAttributeData attributeData, BookingCategory actualEntity) {
        assertEquals(attributeData.getTitle(), actualEntity.getTitle());
    }

}
