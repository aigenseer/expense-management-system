package de.dhbw.ems.application.domain.service.booking.aggregate;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.abstractioncode.valueobject.money.Money;
import de.dhbw.ems.application.domain.service.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregateRepository;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.ems.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class BookingAggregateApplicationServiceTest {

    private final BookingAggregateRepository repository = Mockito.mock(BookingAggregateRepository.class);
    private BookingAggregateApplicationService aggregateApplicationService;

    private final User userMock = Mockito.mock(User.class);
    private final FinancialLedgerAggregate financialLedgerAggregateMock = Mockito.mock(FinancialLedgerAggregate.class);
    private final BookingCategoryAggregate bookingCategoryAggregateMock = Mockito.mock(BookingCategoryAggregate.class);

    private final BookingAggregate aggregate = BookingAggregate.builder()
            .id(UUID.randomUUID())
            .title("NewTitle")
            .money(new Money(19.99, CurrencyType.EURO))
            .categoryAggregateId(UUID.randomUUID())
            .categoryAggregate(bookingCategoryAggregateMock)
            .creationDate(LocalDate.now())
            .creatorId(UUID.randomUUID())
            .creator(userMock)
            .financialLedgerId(UUID.randomUUID())
            .financialLedgerAggregate(financialLedgerAggregateMock)
            .build();

    private final BookingAggregateAttributeData attributeAggregateData = BookingAggregateAttributeData.builder()
            .title("NewTitle")
            .money(new Money(19.99, CurrencyType.EURO))
            .bookingCategoryAggregate(null)
            .bookingCategoryAggregateActive(true)
            .build();

    @Before
    public void setup(){
        aggregateApplicationService = Mockito.spy(new BookingAggregateApplicationService(repository));
        when(repository.save(aggregate)).thenReturn(aggregate);
        when(repository.findById(aggregate.getId())).thenReturn(Optional.of(aggregate));
        when(userMock.getId()).thenReturn(aggregate.getCreatorId());
        when(bookingCategoryAggregateMock.getId()).thenReturn(aggregate.getCategoryAggregateId());
        when(financialLedgerAggregateMock.getId()).thenReturn(aggregate.getFinancialLedgerId());
    }

    @Test
    public void testSave() {
        aggregateApplicationService.save(aggregate);
        verify(repository).save(aggregate);
    }

    @Test
    public void testDeleteById() {
        aggregateApplicationService.deleteById(aggregate.getId());
        verify(repository).deleteById(aggregate.getId());
    }

    @Test
    public void testFindById(){
        Optional<BookingAggregate> result = aggregateApplicationService.findById(aggregate.getId());
        assertTrue(result.isPresent());
        checkAggregate(aggregate, result.get());
    }

    private void checkAggregate(BookingAggregate expectedAggregate, BookingAggregate actualAggregate){
        assertEquals(expectedAggregate.getId(), actualAggregate.getId());
        assertEquals(expectedAggregate.getTitle(), actualAggregate.getTitle());
    }

    @Test
    public void testCreateByAttributeData() {
        when(repository.save(any())).thenReturn(aggregate);

        Optional<BookingAggregate> optionalBookingAggregate = aggregateApplicationService.createByAttributeData(aggregate.getCreator(), aggregate.getFinancialLedgerAggregate(), attributeAggregateData);
        assertTrue(optionalBookingAggregate.isPresent());

        verify(repository).save(argThat(aggregate -> {
            checkAttributeData(attributeAggregateData, aggregate);
            return true;
        }));
    }

    @Test
    public void testUpdateByAttributeData() {
        when(repository.save(any())).thenReturn(aggregate);

        Optional<BookingAggregate> optionalBookingAggregate = aggregateApplicationService.updateByAttributeData(aggregate, attributeAggregateData);
        assertTrue(optionalBookingAggregate.isPresent());

        verify(repository).save(argThat(aggregate -> {
            checkAttributeData(attributeAggregateData, aggregate);
            return true;
        }));
    }

    private void checkAttributeData(BookingAggregateAttributeData attributeData, BookingAggregate aggregate) {
        assertEquals(attributeData.getTitle(), aggregate.getTitle());
        assertEquals(attributeData.getMoney(), aggregate.getMoney());
        assertEquals(attributeData.getBookingCategoryAggregate(), aggregate.getCategoryAggregate());
    }

}
