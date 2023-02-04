package de.dhbw.plugins.rest.bookingcategories;

import de.dhbw.cleanproject.adapter.bookingcategory.data.BookingCategoryData;
import de.dhbw.cleanproject.adapter.bookingcategory.data.BookingCategoryDataToBookingCategoryMapper;
import de.dhbw.cleanproject.adapter.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.plugins.mapper.bookingcategory.BookingCategoriesToBookingCategoryPreviewCollectionMapper;
import de.dhbw.plugins.rest.booking.BookingController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/bookingcategories", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingCategoriesController {

    private final UserOperationService userOperationService;
    private final BookingCategoryDataToBookingCategoryMapper bookingCategoryDataToBookingCategoryMapper;
    private final BookingCategoriesToBookingCategoryPreviewCollectionMapper bookingCategoriesToBookingCategoryPreviewCollectionMapper;

    @GetMapping
    public ResponseEntity<BookingCategoryPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.findFinancialLedgerByUserId(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedger.get();

        BookingCategoryPreviewCollectionModel previewCollectionModel = bookingCategoriesToBookingCategoryPreviewCollectionMapper
                .apply(BookingCategoriesToBookingCategoryPreviewCollectionMapper.Context.builder()
                        .userId(userId)
                        .bookingCategories(financialLedger.getBookingCategories())
                        .build());
        Link selfLink = linkTo(methodOn(this.getClass()).listAll(userId, financialLedgerId)).withSelfRel()
                .andAffordance(afford(methodOn(this.getClass()).create(userId, financialLedgerId, null)));
        previewCollectionModel.add(selfLink);

        return ResponseEntity.ok(previewCollectionModel);
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @Valid @RequestBody BookingCategoryData data) {
        BookingCategory bookingCategory = bookingCategoryDataToBookingCategoryMapper.apply(data);
        Optional<BookingCategory> optionalBookingCategory = userOperationService.addBookingCategory(userId, financialLedgerId, bookingCategory);
        if (!optionalBookingCategory.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        bookingCategory = optionalBookingCategory.get();
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(BookingController.class).findOne(userId, financialLedgerId, bookingCategory.getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
