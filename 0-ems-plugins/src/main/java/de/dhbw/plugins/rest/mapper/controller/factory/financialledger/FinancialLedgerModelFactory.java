package de.dhbw.plugins.rest.mapper.controller.factory.financialledger;

import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.plugins.rest.controller.bookingcategories.BookingCategoriesController;
import de.dhbw.plugins.rest.controller.bookings.BookingsController;
import de.dhbw.plugins.rest.controller.financialledger.FinancialLedgerController;
import de.dhbw.plugins.rest.controller.financialledger.users.FinancialLedgerUsersController;
import de.dhbw.plugins.rest.mapper.controller.model.booking.BookingsToBookingPreviewCollectionMapper;
import de.dhbw.plugins.rest.mapper.controller.model.bookingcategory.BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper;
import de.dhbw.plugins.rest.mapper.controller.model.user.UsersToUserPreviewCollectionMapper;
import de.dhbw.plugins.rest.mapper.model.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.plugins.rest.mapper.model.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import de.dhbw.plugins.rest.mapper.model.financialledger.model.FinancialLedgerToFinancialLedgerModelMapper;
import de.dhbw.plugins.rest.mapper.model.financialledger.model.FinancialLedgerModel;
import de.dhbw.plugins.rest.mapper.model.user.preview.UserPreviewCollectionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class FinancialLedgerModelFactory {

    private final FinancialLedgerToFinancialLedgerModelMapper modelMapper;
    private final UsersToUserPreviewCollectionMapper usersToUserPreviewCollectionMapper;
    private final BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper bookingCategoriesToBookingCategoryPreviewCollectionMapper;
    private final BookingsToBookingPreviewCollectionMapper bookingsToBookingPreviewCollectionMapper;

    public FinancialLedgerModel create(final UUID userId, final FinancialLedger financialLedger) {
        FinancialLedgerModel model = modelMapper.apply(financialLedger);

        UserPreviewCollectionModel userPreviewCollectionModel = usersToUserPreviewCollectionMapper.apply(financialLedger.getAuthorizedUser());
        Link selfLink = linkTo(methodOn(FinancialLedgerUsersController.class).listAll(userId, financialLedger.getId())).withSelfRel();
        userPreviewCollectionModel.add(selfLink);

        BookingCategoryPreviewCollectionModel bookingCategoryPreviewCollectionModel = bookingCategoriesToBookingCategoryPreviewCollectionMapper
                .apply(BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper.Context.builder()
                        .userId(userId)
                        .bookingCategoryAggregates(financialLedger.getBookingCategoriesAggregates())
                        .build());
        selfLink = linkTo(methodOn(BookingCategoriesController.class).listAll(userId, financialLedger.getId())).withSelfRel();
        bookingCategoryPreviewCollectionModel.add(selfLink);

        BookingPreviewCollectionModel bookingPreviewCollectionModel = bookingsToBookingPreviewCollectionMapper.apply(BookingsToBookingPreviewCollectionMapper
                .Context.builder()
                .userId(userId)
                .bookingAggregates(financialLedger.getBookingAggregates())
                .build());
        selfLink = linkTo(methodOn(BookingsController.class).listAll(userId, financialLedger.getId())).withSelfRel();
        bookingPreviewCollectionModel.add(selfLink);

        selfLink = linkTo(methodOn(FinancialLedgerController.class).findOne(userId, financialLedger.getId())).withSelfRel()
                .andAffordance(afford(methodOn(FinancialLedgerController.class).update(userId, financialLedger.getId(), null)))
                .andAffordance(afford(methodOn(FinancialLedgerController.class).delete(userId, financialLedger.getId())));
        model.add(selfLink);

        model.setUserPreviewCollectionModel(userPreviewCollectionModel);
        model.setBookingCategoryPreviewCollectionModel(bookingCategoryPreviewCollectionModel);
        model.setBookingPreviewCollectionModel(bookingPreviewCollectionModel);

        return model;
    }


}
