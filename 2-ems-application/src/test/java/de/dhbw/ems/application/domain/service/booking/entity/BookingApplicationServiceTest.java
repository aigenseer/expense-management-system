package de.dhbw.ems.application.domain.service.booking.entity;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.abstractioncode.valueobject.money.Money;
import de.dhbw.ems.application.domain.service.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.application.domain.service.booking.data.BookingAttributeData;
import de.dhbw.ems.domain.booking.entity.Booking;
import de.dhbw.ems.domain.booking.entity.BookingRepository;
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
public class BookingApplicationServiceTest {

    @Mock
    private BookingRepository repository;

    @InjectMocks
    private BookingApplicationService applicationService;

    private final Booking entity = Booking.builder()
            .id(UUID.randomUUID())
            .title("NewTitle")
            .money(new Money(19.99, CurrencyType.EURO))
            .build();

    private final BookingAttributeData attributeData = BookingAggregateAttributeData.builder()
            .title("NewTitle")
            .money(new Money(19.99, CurrencyType.EURO))
            .build();

    @Test
    public void testSave() {
        when(repository.save(entity)).thenReturn(entity);
        Booking result =  applicationService.save(entity);
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
        Optional<Booking> result = applicationService.findById(entity.getId());
        assertTrue(result.isPresent());
        checkEntity(entity, result.get());
        verify(repository).findById(entity.getId());
    }

    @Test
    public void testCreateByAttributeData(){
        when(repository.save(any())).thenReturn(entity);
        Optional<Booking> result =  applicationService.createByAttributeData(attributeData);
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
        Optional<Booking> result =  applicationService.updateByAttributeData(entity, attributeData);
        assertTrue(result.isPresent());
        checkEntity(entity, result.get());
        verify(repository).save(argThat(booking -> {
            checkAttributeData(attributeData, booking);
            return true;
        }));
    }

    private void checkAttributeData(BookingAttributeData attributeData, Booking actualEntity){
        assertEquals(attributeData.getTitle(), actualEntity.getTitle());
        assertEquals(attributeData.getMoney(), actualEntity.getMoney());
    }

    private void checkEntity(Booking expectedEntity, Booking actualEntity){
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getTitle(), actualEntity.getTitle());
    }

}
