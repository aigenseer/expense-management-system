package de.dhbw.plugins.rest.controller.bookingcategories;

import de.dhbw.ems.adapter.application.bookingcategory.BookingCategoryApplicationAdapter;
import de.dhbw.ems.adapter.application.financialledger.FinancialLedgerApplicationAdapter;
import de.dhbw.ems.adapter.mapper.data.bookingcategory.BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper;
import de.dhbw.ems.application.domain.service.bookingcategory.data.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.plugins.rest.controller.bookingcategory.BookingCategoryController;
import de.dhbw.plugins.rest.controller.bookingcategory.data.BookingCategoryData;
import de.dhbw.plugins.rest.controller.utils.WebMvcLinkBuilderUtils;
import de.dhbw.plugins.rest.mapper.controller.factory.bookingcategory.BookingCategoryPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.mapper.model.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
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

    private final FinancialLedgerApplicationAdapter financialLedgerApplicationAdapter;
    private final BookingCategoryApplicationAdapter bookingCategoryApplicationAdapter;
    private final BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper bookingCategoryDataToBookingCategoryAttributeDataAdapterMapper;
    private final BookingCategoryPreviewCollectionModelFactory bookingCategoryPreviewCollectionModelFactory;

    @GetMapping
    public ResponseEntity<BookingCategoryPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationAdapter.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(bookingCategoryPreviewCollectionModelFactory.create(userId, financialLedgerId, optionalFinancialLedger.get().getBookingCategoriesAggregates()));
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @Valid @RequestBody BookingCategoryData data) {
        BookingCategoryAttributeData attributeData = bookingCategoryDataToBookingCategoryAttributeDataAdapterMapper.apply(data);
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryApplicationAdapter.create(userId, financialLedgerId, attributeData);
        if (!optionalBookingCategoryAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(BookingCategoryController.class).findOne(userId, financialLedgerId, optionalBookingCategoryAggregate.get().getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
