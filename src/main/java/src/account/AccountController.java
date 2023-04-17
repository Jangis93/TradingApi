package src.account;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
@Slf4j
@AllArgsConstructor
public class AccountController {

    @NonNull
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountDetails> crateAccount(@RequestParam String name, @RequestParam BigDecimal balance) {
        log.info(String.format("Received request to create new account with name %s and balance %f", name, balance));
        return ResponseEntity.ok(this.accountService.createAccount(name, balance));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDetails> fetchAccountDetails(@PathVariable int accountId) {
        log.info(String.format("Fetching account details with accountId: %d", accountId));
        AccountDetails accountDetails = this.accountService.fetchAccountDetails(accountId).orElse(null);
        if(accountDetails != null) {
            return ResponseEntity.ok(accountDetails);
        }
        return ResponseEntity.notFound().build();
    }

}
