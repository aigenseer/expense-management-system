package de.dhbw.plugins.rest.controller.financialledger.user;

import de.dhbw.ems.adapter.application.financialledger.FinancialLedgerApplicationAdapter;
import de.dhbw.ems.adapter.application.user.UserApplicationAdapter;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.rest.mapper.controller.factory.financialledger.FinancialLedgerUserPreviewModelFactory;
import de.dhbw.plugins.rest.mapper.model.user.preview.UserPreview;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/financialledger/{financialLedgerId}/user/{financialLedgerUserId}", produces = "application/vnd.siren+json")
@AllArgsConstructor

public class FinancialLedgerUserController {

    private final UserApplicationAdapter userApplicationAdapter;
    private final FinancialLedgerApplicationAdapter financialLedgerApplicationAdapter;
    private final FinancialLedgerUserPreviewModelFactory financialLedgerUserPreviewModelFactory;

    @GetMapping
    public ResponseEntity<UserPreview> findOne(@PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("financialLedgerUserId") UUID financialLedgerUserId) {
        Optional<User> optionalUser = userApplicationAdapter.findById(financialLedgerUserId);
        if (!optionalUser.isPresent() || !financialLedgerApplicationAdapter.hasUserPermission(financialLedgerUserId, financialLedgerId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(financialLedgerUserPreviewModelFactory.create(financialLedgerId, optionalUser.get()));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("financialLedgerUserId") UUID financialLedgerUserId) {
        if (!financialLedgerApplicationAdapter.unlinkUser(financialLedgerUserId, financialLedgerId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
