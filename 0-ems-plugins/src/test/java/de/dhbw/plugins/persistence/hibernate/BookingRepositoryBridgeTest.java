package de.dhbw.plugins.persistence.hibernate;

import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.plugins.persistence.hibernate.booking.BookingRepositoryBridge;
import de.dhbw.plugins.persistence.hibernate.booking.SpringDataBookingRepository;
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


@RunWith(MockitoJUnitRunner.class)
@ComponentScan("de.dhbw.plugins.persistence.hibernate")
public class BookingRepositoryBridgeTest {

    @Mock
    private SpringDataBookingRepository springDataRepository;

    @InjectMocks
    private BookingRepositoryBridge repositoryBridge;

    private final UUID financialLedgerId = UUID.randomUUID();
    private final Booking entity = Booking.builder().id(UUID.randomUUID()).financialLedgerId(financialLedgerId).build();
    private final List<UUID> entityIds = new ArrayList<UUID>(){{ add(entity.getId()); }};
    private final List<Booking> entities = new ArrayList<Booking>(){{ add(entity); }};

    @Before()
    public void setup(){
        when(springDataRepository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(springDataRepository.findAllById(entityIds)).thenReturn(entities);
        when(springDataRepository.findAll()).thenReturn(entities);
        when(springDataRepository.findAllWithFinancialLedgerId(financialLedgerId)).thenReturn(new ArrayList<Booking>(){{add(entity);}});
    }

    @Test
    public void testFindAllWithFinancialLedgerId(){
        List<Booking> result = repositoryBridge.findAllWithFinancialLedgerId(financialLedgerId);
        assertEquals(1, result.size());
        assertTrue(result.contains(entity));
        verify(springDataRepository).findAllWithFinancialLedgerId(financialLedgerId);
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
        Optional<Booking> result = repositoryBridge.findById(entity.getId());
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(springDataRepository).findById(entity.getId());
    }

}
