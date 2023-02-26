package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.booking.aggregate.BookingAggregateDomainService;
import de.dhbw.ems.application.booking.entity.BookingDomainService;
import de.dhbw.ems.application.booking.reference.BookingReferenceDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.booking.reference.BookingReference;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.ems.domain.user.User;

import java.util.Optional;

public class BookingColleague extends Colleague {

    private final BookingDomainService bookingDomainService;
    private final BookingAggregateDomainService bookingAggregateDomainService;
    private final BookingReferenceDomainService bookingReferenceDomainService;

    public BookingColleague(
            final ConcreteApplicationMediator mediator,
            final BookingAggregateDomainService bookingAggregateDomainService,
            final BookingDomainService bookingDomainService,
            final BookingReferenceDomainService bookingReferenceDomainService) {
        super(mediator);
        this.bookingAggregateDomainService = bookingAggregateDomainService;
        this.bookingDomainService = bookingDomainService;
        this.bookingReferenceDomainService = bookingReferenceDomainService;
    }

    @Override
    public void onReferenceUserToBooking(User user, BookingAggregate bookingAggregate) {
        Optional<BookingReference> optionalBookingReference = bookingReferenceDomainService.create(user.getId(), bookingAggregate.getId());
        bookingAggregate.getBookingReferences().add(optionalBookingReference.get());
        bookingAggregateDomainService.save(bookingAggregate);
    }

    @Override
    public void onDeleteReferenceUserToBooking(User user, BookingAggregate bookingAggregate) {
        if (bookingReferenceDomainService.exists(user.getId(), bookingAggregate.getId())){
            bookingReferenceDomainService.deleteById(user.getId(), bookingAggregate.getId());
            bookingAggregate.getBookingReferences().removeIf(r -> r.getId().getUserId().equals(user.getId()));
            bookingAggregateDomainService.save(bookingAggregate);
        }
    }

    @Override
    public void onDeleteUser(User user) {
        bookingAggregateDomainService.findByCreatorId(user.getId()).forEach(bookingAggregate -> {
            getMediator().onDeleteBooking(bookingAggregate, this);
            onDeleteBooking(bookingAggregate);
        });
        bookingReferenceDomainService.findByUserId(user.getId()).forEach(bookingReference -> {
            getMediator().onDeleteReferenceUserToBooking(user, bookingReference.getBookingAggregate(), this);
            onDeleteReferenceUserToBooking(user, bookingReference.getBookingAggregate());
        });
    }

    @Override
    public void onDeleteFinancialLedger(FinancialLedgerAggregate financialLedgerAggregate) {
        financialLedgerAggregate.getBookingAggregates().forEach(bookingAggregate -> {
            getMediator().onDeleteBooking(bookingAggregate, this);
            onDeleteBooking(bookingAggregate);
        });
    }

    @Override
    public void onDeleteBookingCategory(BookingCategoryAggregate bookingCategoryAggregate) {
        bookingCategoryAggregate.getBookingAggregates().forEach(bookingAggregate -> {
            bookingAggregate.setCategoryAggregate(null);
            bookingAggregateDomainService.save(bookingAggregate);
        });
    }

    @Override
    public void onDeleteBooking(BookingAggregate bookingAggregate) {
        bookingReferenceDomainService.findByBookingAggregateId(bookingAggregate.getId()).forEach(bookingReference -> {
            getMediator().onDeleteReferenceUserToBooking(bookingReference.getUser(), bookingReference.getBookingAggregate(), this);
            onDeleteReferenceUserToBooking(bookingReference.getUser(), bookingReference.getBookingAggregate());
        });
        bookingAggregateDomainService.deleteById(bookingAggregate.getId());
        bookingDomainService.deleteById(bookingAggregate.getBooking().getId());
    }

}
