package src.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<AccountDetails> fetchAccountDetails(int accountId) {
        return accountRepository.findById(accountId);
    }

    public AccountDetails createAccount(String name, BigDecimal balance) {
        AccountDetails newCustomer = new AccountDetails(name, balance);
        return accountRepository.save(newCustomer);
    }

    public AccountDetails updateAccount(AccountDetails accountDetails) {
        return accountRepository.save(accountDetails);
    }
}
