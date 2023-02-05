package de.dhbw.plugins.rest.financialledger;

import de.dhbw.cleanproject.adapter.model.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.model.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.model.financialledger.data.FinancialLedgerData;
import de.dhbw.cleanproject.adapter.model.financialledger.data.FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper;
import de.dhbw.cleanproject.adapter.model.financialledger.model.FinancialLedgerModel;
import de.dhbw.cleanproject.adapter.model.financialledger.model.FinancialLedgerToFinancialLedgerModelAdapterMapper;
import de.dhbw.cleanproject.adapter.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.cleanproject.application.financialledger.FinancialLedgerAttributeData;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.plugins.mapper.booking.BookingsToBookingPreviewCollectionMapper;
import de.dhbw.plugins.mapper.bookingcategory.BookingCategoriesToBookingCategoryPreviewCollectionMapper;
import de.dhbw.plugins.mapper.user.UsersToUserPreviewCollectionMapper;
import de.dhbw.plugins.rest.bookingcategories.BookingCategoriesController;
import de.dhbw.plugins.rest.bookings.BookingsController;
import de.dhbw.plugins.rest.financialledger.users.FinancialLedgerUsersController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/user/{userId}/financialledger/{financialLedgerId}/", produces = "application/vnd.siren+json")
@AllArgsConstructor

public class FinancialLedgerController {

    private final UserOperationService userOperationService;
    private final FinancialLedgerApplicationService financialLedgerApplicationService;
    private final FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper adapterMapper;
    private final FinancialLedgerToFinancialLedgerModelAdapterMapper modelMapper;

    private final UsersToUserPreviewCollectionMapper usersToUserPreviewCollectionMapper;
    private final BookingCategoriesToBookingCategoryPreviewCollectionMapper bookingCategoriesToBookingCategoryPreviewCollectionMapper;
    private final BookingsToBookingPreviewCollectionMapper bookingsToBookingPreviewCollectionMapper;

    @GetMapping
    public ResponseEntity<FinancialLedgerModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.findFinancialLedgerByUserId(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedger.get();
        FinancialLedgerModel model = modelMapper.apply(financialLedger);

        UserPreviewCollectionModel userPreviewCollectionModel = usersToUserPreviewCollectionMapper.apply(financialLedger.getAuthorizedUser());
        Link selfLink = linkTo(methodOn(FinancialLedgerUsersController.class).listAll(userId, financialLedgerId)).withSelfRel();
        userPreviewCollectionModel.add(selfLink);

        BookingCategoryPreviewCollectionModel bookingCategoryPreviewCollectionModel = bookingCategoriesToBookingCategoryPreviewCollectionMapper
                .apply(BookingCategoriesToBookingCategoryPreviewCollectionMapper.Context.builder()
                        .userId(userId)
                        .bookingCategories(financialLedger.getBookingCategories())
                        .build());
        selfLink = linkTo(methodOn(BookingCategoriesController.class).listAll(userId, financialLedgerId)).withSelfRel();
        bookingCategoryPreviewCollectionModel.add(selfLink);

        BookingPreviewCollectionModel bookingPreviewCollectionModel = bookingsToBookingPreviewCollectionMapper.apply(BookingsToBookingPreviewCollectionMapper
                .Context.builder()
                .userId(userId)
                .bookings(financialLedger.getBookings())
                .build());
        selfLink = linkTo(methodOn(BookingsController.class).listAll(userId, financialLedgerId)).withSelfRel();
        bookingPreviewCollectionModel.add(selfLink);

        selfLink = linkTo(methodOn(getClass()).findOne(userId, financialLedgerId)).withSelfRel()
                .andAffordance(afford(methodOn(getClass()).update(userId, financialLedgerId, null)))
                .andAffordance(afford(methodOn(getClass()).delete(userId, financialLedgerId)));
        model.add(selfLink);

        model.setUserPreviewCollectionModel(userPreviewCollectionModel);
        model.setBookingCategoryPreviewCollectionModel(bookingCategoryPreviewCollectionModel);
        model.setBookingPreviewCollectionModel(bookingPreviewCollectionModel);

        return ResponseEntity.ok(model);
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @Valid @RequestBody FinancialLedgerData data) {
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.findFinancialLedgerByUserId(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedgerAttributeData financialLedgerAttributeData = adapterMapper.apply(data);
        optionalFinancialLedger = financialLedgerApplicationService.updateByAttributeData(optionalFinancialLedger.get(), financialLedgerAttributeData);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = linkTo(methodOn(this.getClass()).findOne(userId, financialLedgerId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        if (!userOperationService.deleteFinancialLedgerById(userId, financialLedgerId)) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
