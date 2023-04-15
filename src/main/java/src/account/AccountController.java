package src.account;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @NonNull
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public int crateAccount(@RequestParam String name, @RequestParam int balance) {
        logger.info(String.format("Received request to create new account with name %s and balance %d", name, balance));
        return this.accountService.createAccount(name, balance);
    }

    @GetMapping("/{accountId}")
    public Optional<AccountDetails> fetchAccountDetails(@PathVariable int accountId) {
        logger.info(String.format("Fetching account details with accountId: %d", accountId));
        return accountService.fetchAccountDetails(accountId);
    }

}
