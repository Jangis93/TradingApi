package src.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<AccountDetails> fetchAccountDetails(int accountId) {
        return accountRepository.findById(accountId);
    }

    public int createAccount(String name, int balance) {
        return accountRepository.save(new AccountDetails(name, balance)).getAccountId();
    }

    public void reduceBalance(BigDecimal reduction) {

    }
}
