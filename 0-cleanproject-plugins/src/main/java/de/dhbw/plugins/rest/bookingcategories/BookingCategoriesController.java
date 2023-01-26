package de.dhbw.plugins.rest.bookingcategories;

import de.dhbw.cleanproject.adapter.bookingcategory.data.BookingCategoryData;
import de.dhbw.cleanproject.adapter.bookingcategory.data.BookingCategoryDataToBookingCategoryMapper;
import de.dhbw.cleanproject.adapter.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.bookingcategory.preview.BookingCategoryPreviewModel;
import de.dhbw.cleanproject.adapter.bookingcategory.preview.BookingCategoryToBookingCategoryPreviewModelMapper;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.plugins.rest.booking.BookingController;
import de.dhbw.plugins.rest.bookingcategory.BookingCategoryController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/bookingcategories", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingCategoriesController {

    private final UserOperationService userOperationService;
    private final BookingCategoryToBookingCategoryPreviewModelMapper previewModelMapper;
    private final BookingCategoryDataToBookingCategoryMapper bookingCategoryDataToBookingCategoryMapper;

    @GetMapping
    public ResponseEntity<BookingCategoryPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.findFinancialLedgerByUserId(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedger.get();

        List<BookingCategoryPreviewModel> previewModels = financialLedger.getBookingCategories().stream()
                .map(bookingCategory -> {
                    BookingCategoryPreviewModel preview = previewModelMapper.apply(bookingCategory);
                    Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(BookingCategoryController.class).findOne(userId, financialLedgerId, bookingCategory.getId())).withSelfRel();
                    preview.add(selfLink);
                    return preview;
                })
                .collect(Collectors.toList());

        BookingCategoryPreviewCollectionModel previewCollectionModel = new BookingCategoryPreviewCollectionModel(previewModels);

        Link selfLink = linkTo(methodOn(this.getClass()).listAll(userId, financialLedgerId)).withSelfRel()
                .andAffordance(afford(methodOn(this.getClass()).create(userId, financialLedgerId, null)));
        previewCollectionModel.add(selfLink);

        return ResponseEntity.ok(previewCollectionModel);
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @Valid @RequestBody BookingCategoryData data) {
        BookingCategory bookingCategory = bookingCategoryDataToBookingCategoryMapper.apply(data);
        Optional<BookingCategory> optionalBookingCategory = userOperationService.addBookingCategory(userId, financialLedgerId, bookingCategory);
        if (!optionalBookingCategory.isPresent()) new ResponseEntity<>(HttpStatus.FORBIDDEN);

        bookingCategory = optionalBookingCategory.get();
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(BookingController.class).findOne(userId, financialLedgerId, bookingCategory.getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
