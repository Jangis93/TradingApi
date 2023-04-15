package src.account;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Table(name = "accountDetails")
@Entity
@NoArgsConstructor
public class AccountDetails implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private int accountId;

    @Column
    private String name;

    @Column
    private int balance;

    public AccountDetails(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
