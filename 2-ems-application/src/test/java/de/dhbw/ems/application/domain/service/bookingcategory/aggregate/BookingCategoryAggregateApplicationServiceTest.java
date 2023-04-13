package de.dhbw.ems.application.domain.service.bookingcategory.aggregate;

import de.dhbw.ems.application.domain.service.bookingcategory.data.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregateRepository;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
public class BookingCategoryAggregateApplicationServiceTest {

    private final BookingCategoryAggregateRepository repository = Mockito.mock(BookingCategoryAggregateRepository.class);
    private BookingCategoryAggregateApplicationService aggregateApplicationService;

    private final FinancialLedgerAggregate financialLedgerAggregateMock = Mockito.mock(FinancialLedgerAggregate.class);


    private final BookingCategoryAggregate aggregate = BookingCategoryAggregate.builder()
            .id(UUID.randomUUID())
            .title("NewTitle")
            .financialLedgerId(UUID.randomUUID())
            .financialLedgerAggregate(financialLedgerAggregateMock)
            .build();

    private final BookingCategoryAttributeData attributeData = BookingCategoryAttributeData.builder()
            .title("NewTitle")
            .build();

    @Before
    public void setup(){
        aggregateApplicationService = Mockito.spy(new BookingCategoryAggregateApplicationService(repository));
        when(repository.save(aggregate)).thenReturn(aggregate);
        when(repository.findById(aggregate.getId())).thenReturn(Optional.of(aggregate));
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
        Optional<BookingCategoryAggregate> result = aggregateApplicationService.findById(aggregate.getId());
        assertTrue(result.isPresent());
        checkAggregate(aggregate, result.get());
    }

    private void checkAggregate(BookingCategoryAggregate expectedAggregate, BookingCategoryAggregate actualAggregate){
        assertEquals(expectedAggregate.getId(), actualAggregate.getId());
        assertEquals(expectedAggregate.getFinancialLedgerId(), actualAggregate.getFinancialLedgerId());
    }

    @Test
    public void testCreateByAttributeData() {
        when(repository.save(any())).thenReturn(aggregate);

        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = aggregateApplicationService.createByAttributeData(financialLedgerAggregateMock, attributeData);
        assertTrue(optionalBookingCategoryAggregate.isPresent());

        verify(repository).save(argThat(aggregate -> {
            checkAttributeData(attributeData, aggregate);
            return true;
        }));
    }

    @Test
    public void testUpdateByAttributeData() {
        when(repository.save(any())).thenReturn(aggregate);
        when(repository.findById(aggregate.getId())).thenReturn(Optional.of(aggregate));
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = aggregateApplicationService.updateByAttributeData(aggregate, attributeData);
        assertTrue(optionalBookingCategoryAggregate.isPresent());
        verify(repository).save(argThat(aggregate -> {
            checkAttributeData(attributeData, aggregate);
            return true;
        }));
    }

    private void checkAttributeData(BookingCategoryAttributeData attributeData, BookingCategoryAggregate aggregate) {
        assertEquals(attributeData.getTitle(), aggregate.getTitle());
    }

}
