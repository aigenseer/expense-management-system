package de.dhbw.plugins.mapper.financialledger;

import de.dhbw.ems.adapter.model.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import de.dhbw.ems.adapter.model.financialledger.model.FinancialLedgerModel;
import de.dhbw.ems.adapter.model.financialledger.model.FinancialLedgerToFinancialLedgerModelAdapterMapper;
import de.dhbw.ems.adapter.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.plugins.mapper.booking.BookingsToBookingPreviewCollectionMapper;
import de.dhbw.plugins.mapper.bookingcategory.BookingCategoriesToBookingCategoryPreviewCollectionMapper;
import de.dhbw.plugins.mapper.user.UsersToUserPreviewCollectionMapper;
import de.dhbw.plugins.rest.bookingcategories.BookingCategoriesController;
import de.dhbw.plugins.rest.bookings.BookingsController;
import de.dhbw.plugins.rest.financialledger.FinancialLedgerController;
import de.dhbw.plugins.rest.financialledger.users.FinancialLedgerUsersController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class FinancialLedgerModelFactory {

    private final FinancialLedgerToFinancialLedgerModelAdapterMapper modelMapper;
    private final UsersToUserPreviewCollectionMapper usersToUserPreviewCollectionMapper;
    private final BookingCategoriesToBookingCategoryPreviewCollectionMapper bookingCategoriesToBookingCategoryPreviewCollectionMapper;
    private final BookingsToBookingPreviewCollectionMapper bookingsToBookingPreviewCollectionMapper;

    public FinancialLedgerModel create(final UUID userId, final FinancialLedger financialLedger) {
        FinancialLedgerModel model = modelMapper.apply(financialLedger);

        UserPreviewCollectionModel userPreviewCollectionModel = usersToUserPreviewCollectionMapper.apply(financialLedger.getAuthorizedUser());
        Link selfLink = linkTo(methodOn(FinancialLedgerUsersController.class).listAll(userId, financialLedger.getId())).withSelfRel();
        userPreviewCollectionModel.add(selfLink);

        BookingCategoryPreviewCollectionModel bookingCategoryPreviewCollectionModel = bookingCategoriesToBookingCategoryPreviewCollectionMapper
                .apply(BookingCategoriesToBookingCategoryPreviewCollectionMapper.Context.builder()
                        .userId(userId)
                        .bookingCategories(financialLedger.getBookingCategories())
                        .build());
        selfLink = linkTo(methodOn(BookingCategoriesController.class).listAll(userId, financialLedger.getId())).withSelfRel();
        bookingCategoryPreviewCollectionModel.add(selfLink);

        BookingPreviewCollectionModel bookingPreviewCollectionModel = bookingsToBookingPreviewCollectionMapper.apply(BookingsToBookingPreviewCollectionMapper
                .Context.builder()
                .userId(userId)
                .bookings(financialLedger.getBookings())
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
