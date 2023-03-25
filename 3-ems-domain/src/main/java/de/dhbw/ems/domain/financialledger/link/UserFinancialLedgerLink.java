package de.dhbw.ems.domain.financialledger.link;

import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.ems.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ems_user_to_financial_ledger_aggregate")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserFinancialLedgerLink {

    @EmbeddedId
    private UserFinancialLedgerAggregateId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ems_user_id", nullable = false, updatable = false, insertable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "financial_ledger_aggregate_id", nullable = false, updatable = false, insertable = false)
    private FinancialLedgerAggregate financialLedgerAggregate;

}
