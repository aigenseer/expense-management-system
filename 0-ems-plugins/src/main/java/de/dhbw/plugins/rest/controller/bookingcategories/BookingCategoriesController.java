package de.dhbw.plugins.rest.controller.bookingcategories;

import de.dhbw.ems.adapter.application.bookingcategory.BookingCategoryApplicationAdapter;
import de.dhbw.ems.adapter.application.financialledger.FinancialLedgerApplicationAdapter;
import de.dhbw.ems.adapter.model.bookingcategory.data.BookingCategoryData;
import de.dhbw.ems.adapter.model.bookingcategory.data.BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper;
import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.plugins.mapper.bookingcategory.factory.BookingCategoryPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.controller.bookingcategory.BookingCategoryController;
import de.dhbw.plugins.rest.controller.utils.WebMvcLinkBuilderUtils;
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
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerAggregateId}/bookingcategories", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingCategoriesController {

    private final FinancialLedgerApplicationAdapter financialLedgerApplicationAdapter;
    private final BookingCategoryApplicationAdapter bookingCategoryApplicationAdapter;
    private final BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper bookingCategoryDataToBookingCategoryAttributeDataAdapterMapper;
    private final BookingCategoryPreviewCollectionModelFactory bookingCategoryPreviewCollectionModelFactory;

    @GetMapping
    public ResponseEntity<BookingCategoryPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId) {
        Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerApplicationAdapter.find(userId, financialLedgerAggregateId);
        if (!optionalFinancialLedgerAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(bookingCategoryPreviewCollectionModelFactory.create(userId, financialLedgerAggregateId, optionalFinancialLedgerAggregate.get().getBookingCategoriesAggregates()));
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @Valid @RequestBody BookingCategoryData data) {
        BookingCategoryAttributeData attributeData = bookingCategoryDataToBookingCategoryAttributeDataAdapterMapper.apply(data);
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryApplicationAdapter.create(userId, financialLedgerAggregateId, attributeData);
        if (!optionalBookingCategoryAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(BookingCategoryController.class).findOne(userId, financialLedgerAggregateId, optionalBookingCategoryAggregate.get().getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
