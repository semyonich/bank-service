package com.pet.bankservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    public enum TransactionType {
        INCOMING,
        OUTCOMING;
    }

    public enum StatusType {
        OK,
        ERROR;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;
    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Override
    public String toString() {
        return "Transaction{"
                + "id=" + id
                + ", fromAccount=" + fromAccount.getAccountNumber()
                + ", toAccount=" + toAccount.getAccountNumber()
                + ", dateTime=" + dateTime
                + ", amount=" + amount
                + ", type=" + type
                + ", status='" + status + '\''
                + '}';
    }
}
