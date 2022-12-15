package com.bootcamp.project.credit.service;

import com.bootcamp.project.credit.entity.CreditEntity;
import com.bootcamp.project.credit.exception.CustomInformationException;
import com.bootcamp.project.credit.exception.CustomNotFoundException;
import com.bootcamp.project.credit.repository.CreditRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class CreditServiceImplementation implements CreditService{
    private static Logger Log = Logger.getLogger(CreditServiceImplementation.class);
    @Autowired
    private CreditRepository creditRepository;

    @Override
    public Flux<CreditEntity> getAll() {
        return creditRepository.findAll().switchIfEmpty(Mono.error(new CustomNotFoundException("Credits not found")));
    }
    @Override
    public Mono<CreditEntity> getOne(String creditNumber) {
        return creditRepository.findAll().filter(x -> x.getCreditNumber().equals(creditNumber)).next();
    }

    @Override
    public Mono<CreditEntity> save(CreditEntity colEnt) {
        return creditRepository.save(colEnt);
    }

    @Override
    public Mono<CreditEntity> update(String creditNumber, double currentDebt) {
        return getOne(creditNumber).flatMap(c -> {
            c.setCurrentDebt(currentDebt);
            c.setModifyDate(new Date());
            return creditRepository.save(c);
        }).switchIfEmpty(Mono.error(new CustomNotFoundException("Credit not found")));
    }

    @Override
    public Mono<Void> delete(String creditNumber) {
        return getOne(creditNumber)
                .switchIfEmpty(Mono.error(new CustomNotFoundException("Credit not found")))
                .flatMap(c -> {
                    return creditRepository.delete(c);
                });
    }
    @Override
    public Flux<CreditEntity> getCreditCardsByClient(String clientDocumentNumber)
    {
        return creditRepository.findAll().filter(x -> x.getClientDocumentNumber().equals(clientDocumentNumber)
                && x.getProductCode().equals("TC"));
    }
    @Override
    public Mono<CreditEntity> payCredit(String creditNumber, double amount)
    {
        return getOne(creditNumber).flatMap(c -> {
            c.setCurrentDebt(c.getCurrentDebt() - amount);
            c.setModifyDate(new Date());
            return creditRepository.save(c);
        }).switchIfEmpty(Mono.error(new CustomNotFoundException("Credit not found")));
    }
    @Override
    public Mono<Double> getCurrentDebt(String creditNumber) {
        //get current debt
        return getOne(creditNumber)
                .map(x -> x.getCurrentDebt())
                .switchIfEmpty(Mono.error(new CustomNotFoundException("Credit not found")));
    }
    @Override
    public Mono<CreditEntity> addCreditCardDebt(String creditNumber, double amount)
    {
        return getOne(creditNumber).filter(x -> x.getProductCode().equals("TC")).flatMap(c -> {
            if(c.getCreditLimit() >= amount + c.getCurrentDebt())
            {
                c.setCurrentDebt(c.getCurrentDebt() + amount);
            }
            else
            {
                Mono.error(new CustomInformationException("Credit limit reached"));
            }
            c.setModifyDate(new Date());
            return creditRepository.save(c);
        }).switchIfEmpty(Mono.error(new CustomNotFoundException("Credit not found")));
    }
    @Override
    public Mono<CreditEntity> registerPersonalCredit(CreditEntity colEnt) {

            if(colEnt.getProductCode().equals("P"))
            {
                return creditRepository.findAll().filter(x -> x.getClientDocumentNumber().equals(colEnt.getClientDocumentNumber())
                        && x.getProductCode().equals("P"))
                        .next()
                        .switchIfEmpty(creditRepository.save(colEnt));
            }
            else if(colEnt.getProductCode().equals("TC"))
            {
                return creditRepository.save(colEnt);
            }
            else
            {
                return Mono.error(new CustomInformationException("Personal clients can only register personal credits and credit cards"));
            }
    }
    @Override
    public Mono<CreditEntity> registerCompanyCredit(CreditEntity colEnt) {

            if(colEnt.getProductCode().equals("E") && colEnt.getProductCode().equals("TC"))
            {
                return creditRepository.save(colEnt);

            }
            else
            {
                return Mono.error(new CustomInformationException("Company clients can only register company credits and credit cards"));
            }
        }
    @Override
    public Mono<Double> getAverageDebt(String clientDocumentNumber) {
        return creditRepository.findAll().filter(x -> x.getClientDocumentNumber().equals(clientDocumentNumber))
                .collect(Collectors.averagingDouble(CreditEntity::getCurrentDebt))
                .switchIfEmpty(Mono.error(new CustomNotFoundException("The client does not have a credit")));

    }
    @Override
    public Mono<Boolean> checkDueDebtByClient(String clientDocumentNumber)
    {
        Date today = new Date();
        return creditRepository.findAll().filter(x -> x.getClientDocumentNumber().equals(clientDocumentNumber)
        && x.getDueDate().before(today) && x.getCurrentDebt() >0)
                .hasElements()
                .switchIfEmpty(Mono.error(new CustomNotFoundException("The client does not have a credit")));
    }
    @Override
    public Flux<CreditEntity> getByClient(String clientDocumentNumber)
    {
        return creditRepository.findAll().filter(x -> x.getClientDocumentNumber().equals(clientDocumentNumber))
                .switchIfEmpty(Mono.error(new CustomNotFoundException("The client does not have a credit")));
    }
    @Override
    public Flux<CreditEntity> getByClientAndDates(String clientDocumentNumber, Date initialDate, Date finalDate)
    {
        return creditRepository.findAll().filter(x -> x.getClientDocumentNumber().equals(clientDocumentNumber)
                && x.getCreateDate().after(initialDate) && x.getCreateDate().before(finalDate))
                .switchIfEmpty(Mono.error(new CustomNotFoundException("The client does not have a credit")));
    }
}
