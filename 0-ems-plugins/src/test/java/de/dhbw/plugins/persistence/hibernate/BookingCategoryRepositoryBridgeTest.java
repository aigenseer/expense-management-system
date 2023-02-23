package de.dhbw.plugins.persistence.hibernate;

import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import de.dhbw.plugins.persistence.hibernate.bookingcategory.BookingCategoryRepositoryBridge;
import de.dhbw.plugins.persistence.hibernate.bookingcategory.SpringDataBookingCategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.Silent.class)
@ComponentScan("de.dhbw.plugins.persistence.hibernate")
public class BookingCategoryRepositoryBridgeTest {

    @Mock
    private SpringDataBookingCategoryRepository springDataRepository;

    @InjectMocks
    private BookingCategoryRepositoryBridge repositoryBridge;

    private final BookingCategory entity = BookingCategory.builder().id(UUID.randomUUID()).build();
    private final List<UUID> entityIds = new ArrayList<UUID>(){{ add(entity.getId()); }};
    private final List<BookingCategory> entities = new ArrayList<BookingCategory>(){{ add(entity); }};

    @Before()
    public void setup(){
        when(springDataRepository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(springDataRepository.findAllById(entityIds)).thenReturn(entities);
        when(springDataRepository.findAll()).thenReturn(entities);
    }

    @Test
    public void testSave() {
        repositoryBridge.save(entity);
        verify(springDataRepository).save(entity);
    }

    @Test
    public void testDeleteById() {
        repositoryBridge.deleteById(entity.getId());
        verify(springDataRepository).deleteById(entity.getId());
    }

    @Test
    public void testFindById(){
        Optional<BookingCategory> result = repositoryBridge.findById(entity.getId());
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(springDataRepository).findById(entity.getId());
    }

}
