package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.application.bookingcategory.BookingCategoryApplicationService;
import de.dhbw.ems.application.bookingcategory.BookingCategoryAttributeData;
import de.dhbw.ems.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.mediator.colleage.BookingCategoryColleague;
import de.dhbw.ems.application.mediator.service.impl.BookingCategoryServicePort;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookingCategoryOperationService extends BookingCategoryColleague implements BookingCategoryServicePort {

    private final ConcreteApplicationMediator mediator;
    private final FinancialLedgerOperationService financialLedgerOperationService;
    private final FinancialLedgerApplicationService financialLedgerApplicationService;
    private final BookingCategoryApplicationService bookingCategoryApplicationService;

    public BookingCategoryOperationService(
            final ConcreteApplicationMediator mediator,
            final FinancialLedgerOperationService financialLedgerOperationService,
            final FinancialLedgerApplicationService financialLedgerApplicationService,
            final BookingCategoryApplicationService bookingCategoryApplicationService
            ) {
        super(mediator, bookingCategoryApplicationService);
        this.mediator = mediator;
        this.financialLedgerOperationService = financialLedgerOperationService;
        this.financialLedgerApplicationService = financialLedgerApplicationService;
        this.bookingCategoryApplicationService = bookingCategoryApplicationService;
    }

    public Optional<BookingCategory> find(UUID id, UUID financialLedgerId, UUID bookingCategoryId){
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerOperationService.find(id, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        return optionalFinancialLedger.get().getBookingCategories().stream().filter(b -> b.getId().equals(bookingCategoryId)).findFirst();
    }

    public boolean exists(UUID id, UUID financialLedgerId, UUID bookingCategoryId){
        return find(id, financialLedgerId, bookingCategoryId).isPresent();
    }

    public boolean delete(UUID id, UUID financialLedgerId, UUID bookingCategoryId){
        Optional<BookingCategory> optionalBookingCategory = find(id, financialLedgerId, bookingCategoryId);
        if (optionalBookingCategory.isPresent()) {
            mediator.onDeleteBookingCategory(optionalBookingCategory.get(), this);
            onDeleteBookingCategory(optionalBookingCategory.get());
            return true;
        }
        return false;
    }

    public Optional<BookingCategory> create(UUID id, UUID financialLedgerId, BookingCategoryAttributeData attributeData){
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerOperationService.find(id, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        Optional<BookingCategory> optionalBookingCategory = bookingCategoryApplicationService.createByAttributeData(optionalFinancialLedger.get(), attributeData);
        if (optionalBookingCategory.isPresent()){
            FinancialLedger financialLedger = optionalFinancialLedger.get();
            financialLedger.getBookingCategories().add(optionalBookingCategory.get());
            financialLedgerApplicationService.save(financialLedger);
            return find(id, financialLedgerId, optionalBookingCategory.get().getId());
        }
        return Optional.empty();

    }



}
