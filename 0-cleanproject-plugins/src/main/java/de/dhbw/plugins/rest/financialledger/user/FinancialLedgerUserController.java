package de.dhbw.plugins.rest.financialledger.user;

import de.dhbw.cleanproject.adapter.model.user.preview.UserPreview;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.application.user.UserApplicationService;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.plugins.mapper.financialledger.FinancialLedgerUserPreviewModelFactory;
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

    private final UserApplicationService userApplicationService;
    private final UserOperationService userOperationService;
    private final FinancialLedgerUserPreviewModelFactory financialLedgerUserPreviewModelFactory;

    @GetMapping
    public ResponseEntity<UserPreview> findOne(@PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("financialLedgerUserId") UUID financialLedgerUserId) {
        Optional<User> optionalUser = userApplicationService.findById(financialLedgerUserId);
        if (!optionalUser.isPresent() || !userOperationService.hasUserPermissionToFinancialLedger(financialLedgerUserId, financialLedgerId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(financialLedgerUserPreviewModelFactory.create(financialLedgerId, optionalUser.get()));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("financialLedgerUserId") UUID financialLedgerUserId) {
        if (!userOperationService.unlinkUserToFinancialLedger(financialLedgerUserId, financialLedgerId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
