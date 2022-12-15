package com.bootcamp.project.credit.service;

import com.bootcamp.project.credit.entity.CreditEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface CreditService {

    public Mono<CreditEntity> getOne(String creditNumber);
    public Flux<CreditEntity> getAll();
    public Mono<CreditEntity> save(CreditEntity colEnt);
    public Mono<CreditEntity> update(String creditNumber, double currentDebt);
    public Mono<Void> delete(String creditNumber);
    public Mono<CreditEntity> payCredit(String creditNumber, double amount);
    public Mono<CreditEntity> addCreditCardDebt(String creditNumber, double amount);
    public Mono<Double> getCurrentDebt(String creditNumber);
    public Flux<CreditEntity> getByClient(String clientDocumentNumber);
    public Flux<CreditEntity> getCreditCardsByClient(String clientDocumentNumber);
    public Mono<CreditEntity> registerPersonalCredit(CreditEntity colEnt);
    public Mono<CreditEntity> registerCompanyCredit(CreditEntity colEnt);
    public Mono<Double> getAverageDebt(String clientDocumentNumber);
    public Mono<Boolean> checkDueDebtByClient(String clientDocumentNumber);
    public Flux<CreditEntity> getByClientAndDates(String clientDocumentNumber, Date initialDate, Date finalDate);
}
