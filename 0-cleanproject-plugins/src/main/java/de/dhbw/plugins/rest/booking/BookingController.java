package de.dhbw.plugins.rest.booking;

import de.dhbw.cleanproject.adapter.booking.data.BookingUnsafeDataToBookingAttributeDataAdapterMapper;
import de.dhbw.cleanproject.adapter.booking.data.BookingUpdateData;
import de.dhbw.cleanproject.adapter.booking.model.BookingModel;
import de.dhbw.cleanproject.adapter.bookingcategory.data.BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.application.booking.BookingApplicationService;
import de.dhbw.cleanproject.application.booking.BookingAttributeData;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.plugins.mapper.booking.BookingToBookingModelMapper;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
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
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/booking/{bookingId}", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class BookingController {

    private final UserOperationService userOperationService;
    private final BookingApplicationService bookingApplicationService;
    private final BookingToBookingModelMapper bookingToBookingModelMapper;
    private final BookingUnsafeDataToBookingAttributeDataAdapterMapper dataAdapterMapper;

    @GetMapping
    public ResponseEntity<BookingModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId) {
        Optional<Booking> optionalBooking = userOperationService.getBooking(userId, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        BookingModel model = bookingToBookingModelMapper.apply(BookingToBookingModelMapper
                .Context.builder()
                .userId(userId)
                .booking(optionalBooking.get())
                .build());
        model.removeLinks();
        Link selfLink = linkTo(methodOn(getClass()).findOne(userId, financialLedgerId, bookingId)).withSelfRel()
                .andAffordance(afford(methodOn(getClass()).update(userId, financialLedgerId, bookingId, null)))
                .andAffordance(afford(methodOn(getClass()).delete(userId, financialLedgerId, bookingId)));
        model.add(selfLink);

        return ResponseEntity.ok(model);
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId, @Valid @RequestBody BookingUpdateData data) {
        Optional<Booking> optionalBooking = userOperationService.getBooking(userId, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Booking booking = optionalBooking.get();
        BookingAttributeData attributeData = dataAdapterMapper.apply(data);
        optionalBooking = bookingApplicationService.update(booking, attributeData);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = linkTo(methodOn(this.getClass()).findOne(userId, financialLedgerId, bookingId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId) {
        if (!userOperationService.deleteBookingById(userId, financialLedgerId, bookingId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



}
