package de.dhbw.ems.domain.financialledger.link;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserFinancialLedgerAggregateId implements Serializable {

    @Column(name="financial_ledger_id", nullable=false)
    @Type(type="uuid-char")
    UUID financialLedgerId;

    @Column(name="ems_user_id", nullable=false)
    @Type(type="uuid-char")
    UUID userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFinancialLedgerAggregateId that = (UserFinancialLedgerAggregateId) o;
        return Objects.equals(financialLedgerId, that.financialLedgerId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(financialLedgerId, userId);
    }

}
