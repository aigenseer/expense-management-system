package de.dhbw.ems.domain.financialledger.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "financial_ledger")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FinancialLedger {

    @Id
    @Column(name = "id", nullable = false)
    @Type(type="uuid-char")
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

}
