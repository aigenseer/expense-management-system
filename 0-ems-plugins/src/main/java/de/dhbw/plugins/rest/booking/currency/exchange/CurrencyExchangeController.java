package de.dhbw.plugins.rest.booking.currency.exchange;

import de.dhbw.ems.adapter.api.currency.exchange.CurrencyExchangeContract;
import de.dhbw.ems.adapter.api.currency.exchange.CurrencyExchangeContractToCurrencyExchangeRequestAdapterMapper;
import de.dhbw.ems.adapter.application.booking.BookingApplicationAdapter;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeRequest;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.plugins.rest.booking.BookingController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/{userId}/financialledger/{financialLedgerId}/booking/{bookingId}/currency/exchange", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final BookingApplicationAdapter bookingApplicationAdapter;
    private final CurrencyExchangeContractToCurrencyExchangeRequestAdapterMapper adapterMapper;

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("bookingId") UUID bookingId, @Valid @RequestBody CurrencyExchangeContract contract) {
        Optional<Booking> optionalBooking = bookingApplicationAdapter.find(userId, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        CurrencyExchangeRequest currencyExchangeRequest = adapterMapper.apply(contract);
        if (!bookingApplicationAdapter.exchangeCurrencyOfBooking(userId, financialLedgerId, bookingId, currencyExchangeRequest.getTargetCurrencyType())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = linkTo(methodOn(BookingController.class).findOne(userId, financialLedgerId, bookingId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.ACCEPTED);
    }

}
