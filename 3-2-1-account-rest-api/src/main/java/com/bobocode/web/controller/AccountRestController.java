package com.bobocode.web.controller;

import com.bobocode.dao.AccountDao;
import com.bobocode.dao.impl.InMemoryAccountDao;
import com.bobocode.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * todo: 1. Configure rest controller that handles requests with url "/accounts"
 * todo: 2. Inject {@link AccountDao} implementation
 * todo: 3. Implement method that handles GET request and returns a list of accounts
 * todo: 4. Implement method that handles GET request with id as path variable and returns account by id
 * todo: 5. Implement method that handles POST request, receives account as request body, saves account and returns it
 * todo:    Configure HTTP response status code 201 - CREATED
 * todo: 6. Implement method that handles PUT request with id as path variable and receives account as request body.
 * todo:    It check if account id and path variable are the same and throws {@link IllegalStateException} otherwise.
 * todo:    Then it saves received account. Configure HTTP response status code 204 - NO CONTENT
 * todo: 7. Implement method that handles DELETE request with id as path variable removes an account by id
 * todo:    Configure HTTP response status code 204 - NO CONTENT
 */

@RestController
@RequestMapping("/accounts")
public class AccountRestController {

    private final AccountDao accountDao;

    @Autowired
    public AccountRestController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @GetMapping
    public List<Account> getAll() {
        return accountDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable Long id) {
        Account account = accountDao.findById(id);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        accountDao.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        if (!account.getId().equals(id)) {
            throw new IllegalStateException("Account ID mismatch");
        }
        accountDao.save(account);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id){
        Account account = accountDao.findById(id);
        accountDao.remove(account);
        return ResponseEntity.noContent().build();
    }

}