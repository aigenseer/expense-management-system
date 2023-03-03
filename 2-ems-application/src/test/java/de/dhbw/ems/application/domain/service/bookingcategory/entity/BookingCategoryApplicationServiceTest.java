package de.dhbw.ems.application.domain.service.bookingcategory.entity;

import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class BookingCategoryApplicationServiceTest {

    @Mock
    private BookingCategoryRepository repository;

    @InjectMocks
    private BookingCategoryApplicationService applicationService;

    private final BookingCategory entity = BookingCategory.builder()
            .id(UUID.randomUUID())
            .title("NewTitle")
            .build();

    private final BookingCategoryAttributeData attributeData = BookingCategoryAttributeData.builder()
            .title("NewTitle")
            .build();

    @Test
    public void testSave() {
        when(repository.save(entity)).thenReturn(entity);
        BookingCategory result =  applicationService.save(entity);
        checkEntity(entity, result);
        verify(repository).save(entity);
    }

    @Test
    public void testDeleteById() {
        applicationService.deleteById(entity.getId());
        verify(repository).deleteById(entity.getId());
    }

    @Test
    public void testFindById(){
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        Optional<BookingCategory> result = applicationService.findById(entity.getId());
        assertTrue(result.isPresent());
        checkEntity(entity, result.get());
        verify(repository).findById(entity.getId());
    }

    @Test
    public void testCreateByAttributeData(){
        when(repository.save(any())).thenReturn(entity);
        Optional<BookingCategory> result =  applicationService.createByAttributeData(attributeData);
        assertTrue(result.isPresent());
        checkEntity(entity, result.get());
        verify(repository).save(argThat(booking -> {
            checkAttributeData(attributeData, booking);
            return true;
        }));
    }

    @Test
    public void testUpdateByAttributeData(){
        when(repository.save(any())).thenReturn(entity);
        Optional<BookingCategory> result =  applicationService.updateByAttributeData(entity, attributeData);
        assertTrue(result.isPresent());
        checkEntity(entity, result.get());
        verify(repository).save(argThat(booking -> {
            checkAttributeData(attributeData, booking);
            return true;
        }));
    }

    private void checkAttributeData(BookingCategoryAttributeData attributeData, BookingCategory actualEntity){
        assertEquals(attributeData.getTitle(), actualEntity.getTitle());
    }

    private void checkEntity(BookingCategory expectedEntity, BookingCategory actualEntity){
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getTitle(), actualEntity.getTitle());
    }

}
