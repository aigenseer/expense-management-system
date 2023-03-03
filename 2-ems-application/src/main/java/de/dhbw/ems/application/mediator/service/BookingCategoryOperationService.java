package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.application.domain.service.bookingcategory.aggregate.BookingCategoryAggregateDomainService;
import de.dhbw.ems.application.domain.service.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.application.domain.service.bookingcategory.entity.BookingCategoryDomainService;
import de.dhbw.ems.application.domain.service.financialledger.aggregate.FinancialLedgerAggregateDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.mediator.colleage.BookingCategoryColleague;
import de.dhbw.ems.application.mediator.service.impl.BookingCategoryService;
import de.dhbw.ems.application.mediator.service.impl.FinancialLedgerService;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookingCategoryOperationService extends BookingCategoryColleague implements BookingCategoryService {

    private final FinancialLedgerService financialLedgerService;
    private final FinancialLedgerAggregateDomainService financialLedgerAggregateDomainService;
    private final BookingCategoryAggregateDomainService bookingCategoryAggregateDomainService;

    public BookingCategoryOperationService(
            final ConcreteApplicationMediator mediator,
            final FinancialLedgerService financialLedgerService,
            final FinancialLedgerAggregateDomainService financialLedgerAggregateDomainService,
            final BookingCategoryAggregateDomainService bookingCategoryAggregateDomainService,
            final BookingCategoryDomainService bookingCategoryDomainService
            ) {
        super(mediator, bookingCategoryAggregateDomainService, bookingCategoryDomainService);
        this.financialLedgerService = financialLedgerService;
        this.financialLedgerAggregateDomainService = financialLedgerAggregateDomainService;
        this.bookingCategoryAggregateDomainService = bookingCategoryAggregateDomainService;
    }

    public Optional<BookingCategoryAggregate> find(UUID userId, UUID financialLedgerAggregateId, UUID bookingCategoryAggregateId){
        Optional<FinancialLedgerAggregate> optionalFinancialLedger = financialLedgerService.find(userId, financialLedgerAggregateId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        return optionalFinancialLedger.get().getBookingCategoriesAggregates().stream().filter(b -> b.getId().equals(bookingCategoryAggregateId)).findFirst();
    }

    public boolean exists(UUID userId, UUID financialLedgerAggregateId, UUID bookingCategoryAggregateId){
        return find(userId, financialLedgerAggregateId, bookingCategoryAggregateId).isPresent();
    }

    public boolean delete(UUID userId, UUID financialLedgerAggregateId, UUID bookingCategoryAggregateId){
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = find(userId, financialLedgerAggregateId, bookingCategoryAggregateId);
        if (optionalBookingCategoryAggregate.isPresent()) {
            getMediator().onDeleteBookingCategory(optionalBookingCategoryAggregate.get(), this);
            onDeleteBookingCategory(optionalBookingCategoryAggregate.get());
            return true;
        }
        return false;
    }

    public Optional<BookingCategoryAggregate> create(UUID userId, UUID financialLedgerAggregateId, BookingCategoryAttributeData attributeData){
        Optional<FinancialLedgerAggregate> optionalFinancialLedger = financialLedgerService.find(userId, financialLedgerAggregateId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryAggregateDomainService.createByAttributeData(optionalFinancialLedger.get(), attributeData);
        if (optionalBookingCategoryAggregate.isPresent()){
            FinancialLedgerAggregate financialLedgerAggregate = optionalFinancialLedger.get();
            financialLedgerAggregate.getBookingCategoriesAggregates().add(optionalBookingCategoryAggregate.get());
            financialLedgerAggregateDomainService.save(financialLedgerAggregate);
            return find(userId, financialLedgerAggregateId, optionalBookingCategoryAggregate.get().getId());
        }
        return Optional.empty();

    }



}
