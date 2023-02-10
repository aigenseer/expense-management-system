package de.dhbw.plugins.rest.bookingcategories;

import de.dhbw.cleanproject.adapter.model.bookingcategory.data.BookingCategoryData;
import de.dhbw.cleanproject.adapter.model.bookingcategory.data.BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper;
import de.dhbw.cleanproject.adapter.model.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import de.dhbw.cleanproject.application.bookingcategory.BookingCategoryAttributeData;
import de.dhbw.cleanproject.application.mediator.service.impl.BookingCategoryService;
import de.dhbw.cleanproject.application.mediator.service.impl.FinancialLedgerService;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.plugins.mapper.bookingcategory.BookingCategoryPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.bookingcategory.BookingCategoryController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/bookingcategories", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingCategoriesController {

    private final FinancialLedgerService financialLedgerService;
    private final BookingCategoryService bookingCategoryService;
    private final BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper bookingCategoryDataToBookingCategoryAttributeDataAdapterMapper;
    private final BookingCategoryPreviewCollectionModelFactory bookingCategoryPreviewCollectionModelFactory;

    @GetMapping
    public ResponseEntity<BookingCategoryPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerService.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(bookingCategoryPreviewCollectionModelFactory.create(userId, financialLedgerId, optionalFinancialLedger.get().getBookingCategories()));
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @Valid @RequestBody BookingCategoryData data) {
        BookingCategoryAttributeData attributeData = bookingCategoryDataToBookingCategoryAttributeDataAdapterMapper.apply(data);
        Optional<BookingCategory> optionalBookingCategory = bookingCategoryService.create(userId, financialLedgerId, attributeData);
        if (!optionalBookingCategory.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(BookingCategoryController.class).findOne(userId, financialLedgerId, optionalBookingCategory.get().getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
