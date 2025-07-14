package com.divyanshu.FinanceLedger.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data

public class transactions {
    @Id    @GeneratedValue
    private Long id;

    private Double amount;
    private String type; // deposit, withdraw, transfer
    private LocalDateTime timestamp;

    private boolean locationChange;
    private long fromAccount;
    private long toAccount;

    public transactions(Long id, Double amount, String type, LocalDateTime timestamp, boolean locationChange, long fromAccount, long toAccount) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.locationChange = locationChange;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public transactions() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isLocationChange() {
        return locationChange;
    }

    public void setLocationChange(boolean locationChange) {
        this.locationChange = locationChange;
    }

    public long getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Long fromAccount) {
        this.fromAccount = fromAccount;
    }

    public long getToAccount() {
        return toAccount;
    }

    public void setToAccount(long toAccount) {
        this.toAccount = toAccount;
    }

    @Override
    public String toString() {
        return "transactions{" +
                "id=" + id +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", timestamp=" + timestamp +
                ", locationChange=" + locationChange +
                ", fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                '}';
    }
}