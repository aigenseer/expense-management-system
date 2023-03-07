package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.domain.service.booking.aggregate.BookingAggregateDomainService;
import de.dhbw.ems.application.domain.service.booking.reference.BookingReferenceDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.user.User;

public class BookingColleague extends Colleague {

    private final BookingAggregateDomainService bookingAggregateDomainService;
    private final BookingReferenceDomainService bookingReferenceDomainService;

    public BookingColleague(
            final ConcreteApplicationMediator mediator,
            final BookingAggregateDomainService bookingAggregateDomainService,
            final BookingReferenceDomainService bookingReferenceDomainService) {
        super(mediator);
        this.bookingAggregateDomainService = bookingAggregateDomainService;
        this.bookingReferenceDomainService = bookingReferenceDomainService;
    }

    @Override
    public void onReferenceUserToBooking(User user, BookingAggregate bookingAggregate) {
        bookingReferenceDomainService.create(user.getId(), bookingAggregate.getId());
    }

    @Override
    public void onDeleteReferenceUserToBooking(User user, BookingAggregate bookingAggregate) {
        if (bookingReferenceDomainService.exists(user.getId(), bookingAggregate.getId())){
            bookingReferenceDomainService.deleteById(user.getId(), bookingAggregate.getId());
            bookingAggregateDomainService.save(bookingAggregate);
        }
    }

    @Override
    public void onDeleteBookingCategory(BookingCategoryAggregate bookingCategoryAggregate) {
        bookingCategoryAggregate.getBookingAggregates().forEach(bookingAggregate -> {
            bookingAggregate.setCategoryAggregateId(null);
            bookingAggregate.setCategoryAggregate(null);
            bookingAggregateDomainService.save(bookingAggregate);
        });
    }


    public void onDeleteBooking(BookingAggregate bookingAggregate) {
        bookingAggregateDomainService.deleteById(bookingAggregate.getId());
    }

}
