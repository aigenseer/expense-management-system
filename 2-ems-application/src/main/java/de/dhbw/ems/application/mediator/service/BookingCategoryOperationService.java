package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.application.domain.service.bookingcategory.aggregate.BookingCategoryAggregateDomainService;
import de.dhbw.ems.application.domain.service.bookingcategory.data.BookingCategoryAttributeData;
import de.dhbw.ems.application.domain.service.financialledger.entity.FinancialLedgerDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.mediator.colleage.BookingCategoryColleague;
import de.dhbw.ems.application.mediator.service.impl.BookingCategoryService;
import de.dhbw.ems.application.mediator.service.impl.FinancialLedgerService;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
            final BookingCategoryAggregateDomainService bookingCategoryAggregateDomainService
            ) {
        super(mediator, bookingCategoryAggregateDomainService);
        this.financialLedgerService = financialLedgerService;
        this.financialLedgerDomainService = financialLedgerDomainService;
        this.bookingCategoryAggregateDomainService = bookingCategoryAggregateDomainService;
    }

    public Optional<BookingCategoryAggregate> find(UUID userId, UUID financialLedgerId, UUID bookingCategoryAggregateId){
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerService.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        return optionalFinancialLedger.get().getBookingCategoriesAggregates().stream().filter(b -> b.getId().equals(bookingCategoryAggregateId)).findFirst();
    }

    public boolean exists(UUID userId, UUID financialLedgerId, UUID bookingCategoryAggregateId){
        return find(userId, financialLedgerId, bookingCategoryAggregateId).isPresent();
    }

    @Transactional
    public boolean delete(UUID userId, UUID financialLedgerId, UUID bookingCategoryAggregateId){
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = find(userId, financialLedgerId, bookingCategoryAggregateId);
        if (optionalBookingCategoryAggregate.isPresent()) {
            getMediator().onDeleteBookingCategory(optionalBookingCategoryAggregate.get(), this);
            onDeleteBookingCategory(optionalBookingCategoryAggregate.get());
            return true;
        }
        return false;
    }

    @Transactional
    public Optional<BookingCategoryAggregate> create(UUID userId, UUID financialLedgerId, BookingCategoryAttributeData attributeData){
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerService.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryAggregateDomainService.createByAttributeData(optionalFinancialLedger.get(), attributeData);
        if (optionalBookingCategoryAggregate.isPresent()){
            FinancialLedger financialLedger = optionalFinancialLedger.get();
            financialLedger.getBookingCategoriesAggregates().add(optionalBookingCategoryAggregate.get());
            financialLedgerDomainService.save(financialLedger);
            return find(userId, financialLedgerId, optionalBookingCategoryAggregate.get().getId());
        }
        return Optional.empty();

    }



}
