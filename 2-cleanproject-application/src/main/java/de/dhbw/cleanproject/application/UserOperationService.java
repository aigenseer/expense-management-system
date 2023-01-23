package de.dhbw.cleanproject.application;

import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserOperationService {

    private final UserApplicationService userApplicationService;
    private final FinancialLedgerApplicationService financialLedgerApplicationService;

    public Optional<FinancialLedger> findFinancialLedgerByUserId(UUID id, UUID financialLedgerId){
        Optional<User> userOptional = userApplicationService.findById(id);
        if (userOptional.isPresent()){
            return userOptional.get().getFinancialLedgers().stream().filter(f -> f.getId().equals(financialLedgerId)).findFirst();
        }
        return Optional.empty();
    }

    public boolean hasUserPermissionToFinancialLedger(UUID id, UUID financialLedgerId){
        return findFinancialLedgerByUserId(id, financialLedgerId).isPresent();
    }

    public boolean appendUserToFinancialLedger(UUID id, UUID financialLedgerId){
        Optional<User> userOptional = userApplicationService.findById(id);
        if (userOptional.isPresent()){
            Optional<FinancialLedger> financialLedgerOptional = financialLedgerApplicationService.findById(financialLedgerId);
            if (financialLedgerOptional.isPresent()){
                User user = userOptional.get();
                user.getFinancialLedgers().add(financialLedgerOptional.get());
                userApplicationService.save(user);
                return true;
            }
        }
        return false;
    }

    public Optional<FinancialLedger> addFinancialLedgerByUserId(UUID id, FinancialLedger financialLedger){
        Optional<User> userOptional = userApplicationService.findById(id);
        if (userOptional.isPresent()){
            financialLedger = financialLedgerApplicationService.save(financialLedger);
            User user = userOptional.get();
            user.getFinancialLedgers().add(financialLedger);
            userApplicationService.save(user);
            return findFinancialLedgerByUserId(id, financialLedger.getId());
        }
        return Optional.empty();
    }

    public Optional<Booking> getBooking(UUID id, UUID financialLedgerId, UUID bookingId){
        Optional<FinancialLedger> optionalFinancialLedger = findFinancialLedgerByUserId(id, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        return optionalFinancialLedger.get().getBookings().stream().filter(b -> b.getId().equals(bookingId)).findFirst();
    }

}
