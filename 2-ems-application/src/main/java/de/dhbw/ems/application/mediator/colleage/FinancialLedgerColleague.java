package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.financialledger.aggregate.FinancialLedgerAggregateDomainService;
import de.dhbw.ems.application.financialledger.entity.FinancialLedgerDomainService;
import de.dhbw.ems.application.financialledger.link.UserFinancialLedgerLinkDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerLink;
import de.dhbw.ems.domain.user.User;

import java.util.Optional;

public class FinancialLedgerColleague extends Colleague {

    private final FinancialLedgerAggregateDomainService financialLedgerAggregateDomainService;
    private final FinancialLedgerDomainService financialLedgerDomainService;
    private final UserFinancialLedgerLinkDomainService userFinancialLedgerLinkDomainService;

    public FinancialLedgerColleague(
            final ConcreteApplicationMediator mediator,
            final FinancialLedgerAggregateDomainService financialLedgerAggregateDomainService,
            final FinancialLedgerDomainService financialLedgerDomainService,
            final UserFinancialLedgerLinkDomainService userFinancialLedgerLinkDomainService
    ) {
        super(mediator);
        this.financialLedgerAggregateDomainService = financialLedgerAggregateDomainService;
        this.financialLedgerDomainService = financialLedgerDomainService;
        this.userFinancialLedgerLinkDomainService = userFinancialLedgerLinkDomainService;
    }

    @Override
    public void onLinkUserToFinancialLedger(User user, FinancialLedgerAggregate financialLedgerAggregate) {
        Optional<UserFinancialLedgerLink> optionalUserFinancialLedgerLink = userFinancialLedgerLinkDomainService.create(user.getId(), financialLedgerAggregate.getId());
        if (optionalUserFinancialLedgerLink.isPresent()){
            financialLedgerAggregate.getUserFinancialLedgerLinks().add(optionalUserFinancialLedgerLink.get());
            financialLedgerAggregateDomainService.save(financialLedgerAggregate);
        }
    }

    @Override
    public void onUnlinkUserToFinancialLedger(User user, FinancialLedgerAggregate financialLedgerAggregate) {
        Optional<UserFinancialLedgerLink> optionalUserFinancialLedgerLink = userFinancialLedgerLinkDomainService.findById(user.getId(), financialLedgerAggregate.getId());
        if (optionalUserFinancialLedgerLink.isPresent()){
            financialLedgerAggregate.getUserFinancialLedgerLinks().remove(optionalUserFinancialLedgerLink.get());
            financialLedgerAggregateDomainService.save(financialLedgerAggregate);
            userFinancialLedgerLinkDomainService.deleteById(user.getId(), financialLedgerAggregate.getId());
        }
        if (financialLedgerAggregate.getUserFinancialLedgerLinks().size() == 0){
            getMediator().onDeleteFinancialLedger(financialLedgerAggregate, this);
            onDeleteFinancialLedger(financialLedgerAggregate);
        }
    }

    @Override
    public void onDeleteUser(User user) {
        userFinancialLedgerLinkDomainService.findByUserId(user.getId()).forEach(r -> onUnlinkUserToFinancialLedger(r.getUser(), r.getFinancialLedgerAggregate()));
    }

    @Override
    public void onDeleteFinancialLedger(FinancialLedgerAggregate financialLedgerAggregate) {
        financialLedgerAggregate.getUserFinancialLedgerLinks().forEach(link ->  {
            userFinancialLedgerLinkDomainService.deleteById(link.getUser().getId(), link.getFinancialLedgerAggregate().getId());
        });
        financialLedgerAggregateDomainService.deleteById(financialLedgerAggregate.getId());
        financialLedgerDomainService.deleteById(financialLedgerAggregate.getFinancialLedgerId());
    }

    @Override
    public void onDeleteBookingCategory(BookingCategoryAggregate bookingCategoryAggregate) {
        FinancialLedgerAggregate financialLedgerAggregate = bookingCategoryAggregate.getFinancialLedgerAggregate();
        financialLedgerAggregate.getBookingCategoriesAggregates().remove(bookingCategoryAggregate);
        financialLedgerAggregateDomainService.save(financialLedgerAggregate);
    }

    @Override
    public void onDeleteBooking(BookingAggregate bookingAggregate) {
        FinancialLedgerAggregate financialLedgerAggregate = bookingAggregate.getFinancialLedgerAggregate();
        financialLedgerAggregate.getBookingAggregates().remove(bookingAggregate);
        financialLedgerAggregateDomainService.save(financialLedgerAggregate);
    }

}
