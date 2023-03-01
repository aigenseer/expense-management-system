package de.dhbw.plugins.mapper.financialledger;

import de.dhbw.ems.adapter.model.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import de.dhbw.ems.adapter.model.financialledger.model.FinancialLedgerAggregateToFinancialLedgerModelAdapterMapper;
import de.dhbw.ems.adapter.model.financialledger.model.FinancialLedgerModel;
import de.dhbw.ems.adapter.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.plugins.mapper.booking.BookingsToBookingPreviewCollectionMapper;
import de.dhbw.plugins.mapper.bookingcategory.BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper;
import de.dhbw.plugins.mapper.user.UsersToUserPreviewCollectionMapper;
import de.dhbw.plugins.rest.controller.bookingcategories.BookingCategoriesController;
import de.dhbw.plugins.rest.controller.bookings.BookingsController;
import de.dhbw.plugins.rest.controller.financialledger.FinancialLedgerController;
import de.dhbw.plugins.rest.controller.financialledger.users.FinancialLedgerUsersController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class FinancialLedgerModelFactory {

    private final FinancialLedgerAggregateToFinancialLedgerModelAdapterMapper modelMapper;
    private final UsersToUserPreviewCollectionMapper usersToUserPreviewCollectionMapper;
    private final BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper bookingCategoriesToBookingCategoryPreviewCollectionMapper;
    private final BookingsToBookingPreviewCollectionMapper bookingsToBookingPreviewCollectionMapper;

    public FinancialLedgerModel create(final UUID userId, final FinancialLedgerAggregate financialLedgerAggregate) {
        FinancialLedgerModel model = modelMapper.apply(financialLedgerAggregate);

        UserPreviewCollectionModel userPreviewCollectionModel = usersToUserPreviewCollectionMapper.apply(financialLedgerAggregate.getAuthorizedUser());
        Link selfLink = linkTo(methodOn(FinancialLedgerUsersController.class).listAll(userId, financialLedgerAggregate.getId())).withSelfRel();
        userPreviewCollectionModel.add(selfLink);

        BookingCategoryPreviewCollectionModel bookingCategoryPreviewCollectionModel = bookingCategoriesToBookingCategoryPreviewCollectionMapper
                .apply(BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper.Context.builder()
                        .userId(userId)
                        .bookingCategoryAggregates(financialLedgerAggregate.getBookingCategoriesAggregates())
                        .build());
        selfLink = linkTo(methodOn(BookingCategoriesController.class).listAll(userId, financialLedgerAggregate.getId())).withSelfRel();
        bookingCategoryPreviewCollectionModel.add(selfLink);

        BookingPreviewCollectionModel bookingPreviewCollectionModel = bookingsToBookingPreviewCollectionMapper.apply(BookingsToBookingPreviewCollectionMapper
                .Context.builder()
                .userId(userId)
                .bookingAggregates(financialLedgerAggregate.getBookingAggregates())
                .build());
        selfLink = linkTo(methodOn(BookingsController.class).listAll(userId, financialLedgerAggregate.getId())).withSelfRel();
        bookingPreviewCollectionModel.add(selfLink);

        selfLink = linkTo(methodOn(FinancialLedgerController.class).findOne(userId, financialLedgerAggregate.getId())).withSelfRel()
                .andAffordance(afford(methodOn(FinancialLedgerController.class).update(userId, financialLedgerAggregate.getId(), null)))
                .andAffordance(afford(methodOn(FinancialLedgerController.class).delete(userId, financialLedgerAggregate.getId())));
        model.add(selfLink);

        model.setUserPreviewCollectionModel(userPreviewCollectionModel);
        model.setBookingCategoryPreviewCollectionModel(bookingCategoryPreviewCollectionModel);
        model.setBookingPreviewCollectionModel(bookingPreviewCollectionModel);

        return model;
    }


}
