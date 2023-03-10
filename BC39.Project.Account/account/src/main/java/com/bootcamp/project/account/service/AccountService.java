package com.bootcamp.project.account.service;

import com.bootcamp.project.account.entity.AccountEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface AccountService {

    public Mono<AccountEntity> getOne(String accountNumber);
    public Flux<AccountEntity> getAll();
    public Mono<AccountEntity> save(AccountEntity colEnt);
    public Mono<AccountEntity> update(String accountNumber, double balance);
    public Mono<Void> delete(String accountNumber);


    public Mono<AccountEntity> getByClientAndProduct(String clientDocumentNumber, String productCode);
    public Mono<Double> getBalance(String account);
    public Mono<AccountEntity> depositBalance(String accountNumber, double balance);
    public Mono<AccountEntity> withdrawBalance(String accountNumber, double balance);
    public Mono<AccountEntity> registerPersonalAccount(AccountEntity colEnt);
    public Mono<AccountEntity> registerCompanyAccount(AccountEntity colEnt);
    public Mono<AccountEntity> applyMaintenanceFee(String accountNumber);
    public Mono<AccountEntity> applyCommissionFee(String accountNumber, double amount);
    public Mono<AccountEntity> transferBalance(String sourceAccountNumber, String targetAccountNumber ,double balance);
    public Mono<Boolean> checkMinimumDailyBalance(String account);
    public Mono<Double> getAverageBalance(String clientDocumentNumber);

    public Mono<Boolean> checkDebitCardMainAccount(String debitCardNumber);
    public Mono<Double> getBalanceByDebitCard(String debitCardNumber);
    public Mono<AccountEntity> linkDebitCardMainAccount(String accountNumber, String debitCardNumber);
    public Mono<AccountEntity> linkDebitCardSecondaryAccount(String accountNumber, String debitCardNumber);
    public Flux<AccountEntity> linkDebitCardSecondaryAccounts(String clientDocumentNumber, String debitCardNumber);

    public Mono<AccountEntity> addDebitCardPayment(String debitCardNumber, double amount);
    public Flux<AccountEntity> getByClient(String clientDocumentNumber);
    public Flux<AccountEntity> getByClientAndDates(String clientDocumentNumber, Date initialDate, Date finalDate);

}
