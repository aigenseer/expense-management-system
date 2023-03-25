package de.dhbw.ems.domain.financialledger.aggregate;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerLink;
import de.dhbw.ems.domain.user.User;
import lombok.*;
import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "financial_ledger_aggregate")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FinancialLedgerAggregate {

    @Id
    @Column(name = "id", nullable = false)
    @Type(type="uuid-char")
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="financialLedgerAggregate", targetEntity = BookingAggregate.class, cascade = CascadeType.REMOVE)
    private Set<BookingAggregate> bookingAggregates;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="financialLedgerAggregate", targetEntity = BookingCategoryAggregate.class, cascade = CascadeType.REMOVE)
    private Set<BookingCategoryAggregate> bookingCategoriesAggregates;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "financialLedgerAggregate", targetEntity = UserFinancialLedgerLink.class, cascade = CascadeType.REMOVE)
    private Set<UserFinancialLedgerLink> userFinancialLedgerLinks;

    public Set<User> getAuthorizedUser(){
        return userFinancialLedgerLinks.stream().map(UserFinancialLedgerLink::getUser).collect(Collectors.toSet());
    }

    public static FinancialLedgerAggregateBuilder builder() {
        return new CustomBuilder();
    }

    private static class CustomBuilder extends FinancialLedgerAggregate.FinancialLedgerAggregateBuilder {
        public FinancialLedgerAggregate build() {
            FinancialLedgerAggregate object = super.build();
            Validate.notNull(object.getId());
            Validate.notBlank(object.getTitle());
            return object;
        }
    }

}
