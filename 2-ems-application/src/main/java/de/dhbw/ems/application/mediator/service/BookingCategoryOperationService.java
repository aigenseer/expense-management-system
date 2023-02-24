package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.application.bookingcategory.aggregate.BookingCategoryAggregateDomainService;
import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryDomainService;
import de.dhbw.ems.application.financialledger.FinancialLedgerDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.mediator.colleage.BookingCategoryColleague;
import de.dhbw.ems.application.mediator.service.impl.BookingCategoryService;
import de.dhbw.ems.application.mediator.service.impl.FinancialLedgerService;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookingCategoryOperationService extends BookingCategoryColleague implements BookingCategoryService {

    private final FinancialLedgerService financialLedgerService;
    private final FinancialLedgerDomainService financialLedgerDomainService;
    private final BookingCategoryAggregateDomainService bookingCategoryAggregateDomainService;

    public BookingCategoryOperationService(
            final ConcreteApplicationMediator mediator,
            final FinancialLedgerService financialLedgerService,
            final FinancialLedgerDomainService financialLedgerDomainService,
            final BookingCategoryAggregateDomainService bookingCategoryAggregateDomainService,
            final BookingCategoryDomainService bookingCategoryDomainService
            ) {
        super(mediator, bookingCategoryAggregateDomainService, bookingCategoryDomainService);
        this.financialLedgerService = financialLedgerService;
        this.financialLedgerDomainService = financialLedgerDomainService;
        this.bookingCategoryAggregateDomainService = bookingCategoryAggregateDomainService;
    }

    public Optional<BookingCategoryAggregate> find(UUID id, UUID financialLedgerId, UUID bookingCategoryAggregateId){
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerService.find(id, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        return optionalFinancialLedger.get().getBookingCategoriesAggregates().stream().filter(b -> b.getId().equals(bookingCategoryAggregateId)).findFirst();
    }

    public boolean exists(UUID id, UUID financialLedgerId, UUID bookingCategoryAggregateId){
        return find(id, financialLedgerId, bookingCategoryAggregateId).isPresent();
    }

    public boolean delete(UUID id, UUID financialLedgerId, UUID bookingCategoryAggregateId){
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = find(id, financialLedgerId, bookingCategoryAggregateId);
        if (optionalBookingCategoryAggregate.isPresent()) {
            getMediator().onDeleteBookingCategory(optionalBookingCategoryAggregate.get(), this);
            onDeleteBookingCategory(optionalBookingCategoryAggregate.get());
            return true;
        }
        return false;
    }

    public Optional<BookingCategoryAggregate> create(UUID id, UUID financialLedgerId, BookingCategoryAttributeData attributeData){
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerService.find(id, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryAggregateDomainService.createByAttributeData(optionalFinancialLedger.get(), attributeData);
        if (optionalBookingCategoryAggregate.isPresent()){
            FinancialLedger financialLedger = optionalFinancialLedger.get();
            financialLedger.getBookingCategoriesAggregates().add(optionalBookingCategoryAggregate.get());
            financialLedgerDomainService.save(financialLedger);
            return find(id, financialLedgerId, optionalBookingCategoryAggregate.get().getId());
        }
        return Optional.empty();

    }



}
