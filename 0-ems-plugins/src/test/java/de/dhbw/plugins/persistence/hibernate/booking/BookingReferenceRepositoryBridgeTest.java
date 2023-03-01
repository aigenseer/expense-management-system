package de.dhbw.plugins.persistence.hibernate.booking;

import de.dhbw.ems.domain.booking.reference.BookingReference;
import de.dhbw.ems.domain.booking.reference.BookingReferenceId;
import de.dhbw.plugins.persistence.hibernate.booking.reference.BookingRepositoryReferenceBridge;
import de.dhbw.plugins.persistence.hibernate.booking.reference.SpringDataBookingReferenceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.Silent.class)
@ComponentScan("de.dhbw.plugins.persistence.hibernate")
public class BookingReferenceRepositoryBridgeTest {

    @Mock
    private SpringDataBookingReferenceRepository springDataRepository;

    @InjectMocks
    private BookingRepositoryReferenceBridge repositoryBridge;

    private final BookingReference entity = BookingReference.builder().id(
            BookingReferenceId.builder().userId(UUID.randomUUID()).bookingAggregateId(UUID.randomUUID()).build()
    ).build();
    private final List<BookingReference> entities = new ArrayList<BookingReference>(){{ add(entity); }};

    @Before()
    public void setup(){
        when(springDataRepository.findAllByIds(entity.getId().getUserId(), entity.getId().getBookingAggregateId())).thenReturn(entities);
        when(springDataRepository.findByUserId(entity.getId().getUserId())).thenReturn(entities);
        when(springDataRepository.findByBookingAggregateId(entity.getId().getBookingAggregateId())).thenReturn(entities);
        when(springDataRepository.existsById(entity.getId())).thenReturn(true);
    }

    @Test
    public void testFindByIds() {
        repositoryBridge.findByIds(entity.getId().getUserId(), entity.getId().getBookingAggregateId());
        verify(springDataRepository).findAllByIds(entity.getId().getUserId(), entity.getId().getBookingAggregateId());
    }

    @Test
    public void testFindByUserId() {
        repositoryBridge.findByUserId(entity.getId().getUserId());
        verify(springDataRepository).findByUserId(entity.getId().getUserId());
    }

    @Test
    public void testFindByBookingAggregateId() {
        repositoryBridge.findByBookingAggregateId(entity.getId().getBookingAggregateId());
        verify(springDataRepository).findByBookingAggregateId(entity.getId().getBookingAggregateId());
    }

    @Test
    public void testExistsByIds() {
        repositoryBridge.exists(entity.getId().getUserId(), entity.getId().getBookingAggregateId());
        verify(springDataRepository).existsById(entity.getId());
    }

}
