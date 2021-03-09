package com.pet.bankservice.controller;

import com.pet.bankservice.entity.Account;
import com.pet.bankservice.entity.Transaction;
import com.pet.bankservice.entity.dto.AccountRequestDto;
import com.pet.bankservice.entity.dto.AccountResponseDto;
import com.pet.bankservice.entity.dto.TransactionRequestDto;
import com.pet.bankservice.entity.dto.TransactionResponseDto;
import com.pet.bankservice.service.AccountService;
import com.pet.bankservice.service.TransactionService;
import com.pet.bankservice.service.UserService;
import com.pet.bankservice.service.mapper.AccountMapper;
import com.pet.bankservice.service.mapper.TransactionMapper;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;

    @ApiOperation(value = "Create Account", notes = "Create new Account for user")
    @PostMapping
    public ResponseEntity<AccountResponseDto>
            createAccount(@Valid @RequestBody AccountRequestDto requestDto) {
        Account account = accountMapper.makeEntity(requestDto);
        account.setIsActive(true);
        account.setUser(userService.get(requestDto.getUserId()));
        accountService.save(account);
        AccountResponseDto responseDto = accountMapper.makeDto(account);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get Accounts", notes = "Retrieve all Accounts of User with phoneNumber")
    @GetMapping("/by-phone")
    public ResponseEntity<List<AccountResponseDto>>
                findByPhoneNumber(@RequestParam String phoneNumber) {
        List<AccountResponseDto> responseDtos = accountService
                .getByUserPhoneNumber(phoneNumber).stream()
                .map(accountMapper::makeDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDtos, HttpStatus.FOUND);
    }

    @ApiOperation(value = "Create Transaction",
            notes = "Transfer money from one Account to another")
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDto>
                transferMoney(@Valid @RequestBody TransactionRequestDto requestDto) {
        Transaction transaction = accountService.transferMoney(requestDto.getFromAccount(),
                        requestDto.getToAccount(), requestDto.getAmount());
        TransactionResponseDto responseDto = transactionMapper.makeDto(transaction);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get Transactions",
            notes = "Retrieve all transactions of Account with accountNumber,"
                    + " sorted by Date, the newest first")
    @GetMapping("/history/{accountNumber}")
    public ResponseEntity<List<String>> getAccountHistory(
                            @RequestParam(required = false, defaultValue = "0") Integer page,
                            @RequestParam(required = false, defaultValue = "10") Integer size,
                            @PathVariable String accountNumber) {
        Account account = accountService.getByAccountNumber(accountNumber);
        List<String> transactions = transactionService.getAllByAccount(page, size, account).stream()
                .map(Transaction::toString)
                .collect(Collectors.toList());
        return new ResponseEntity<>(transactions, HttpStatus.FOUND);
    }

    @ApiOperation(value = "Block Account", notes = "Block Account with accountNumber")
    @PatchMapping("/{accountNumber}")
    public ResponseEntity<AccountResponseDto> blockAccount(@PathVariable String accountNumber) {
        Account account = accountService.getByAccountNumber(accountNumber);
        account.setIsActive(false);
        accountService.save(account);
        AccountResponseDto responseDto = accountMapper.makeDto(account);
        return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
    }
}
