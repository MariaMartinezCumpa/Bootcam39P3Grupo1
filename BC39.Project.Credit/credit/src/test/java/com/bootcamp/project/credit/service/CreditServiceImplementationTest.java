package com.bootcamp.project.credit.service;

import com.bootcamp.project.credit.entity.CreditEntity;
import com.bootcamp.project.credit.repository.CreditRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreditServiceImplementationTest {
    @InjectMocks
    CreditServiceImplementation creditServiceImplementation;

    @Mock
    CreditRepository creditRepository;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void checkDueDebtByClient() {
        CreditEntity creditEntity = new CreditEntity();
        creditEntity.setClientDocumentNumber("17593382");
        creditEntity.setDueDate(new Date());
        creditEntity.setCurrentDebt(12.0);
        List<CreditEntity> creditEntityList = new ArrayList<>();
        creditEntityList.add(creditEntity);
        Mockito.when(creditRepository.findAll()).thenReturn(Flux.fromIterable(creditEntityList));
        Mono<Boolean> result = creditServiceImplementation.checkDueDebtByClient("17593382");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(true, result.block().booleanValue());
    }

    @Test
    void getByClient() {
        CreditEntity creditEntity = new CreditEntity();
        creditEntity.setClientDocumentNumber("17593382");
        List<CreditEntity> creditEntityList = new ArrayList<>();
        creditEntityList.add(creditEntity);
        Mockito.when(creditRepository.findAll()).thenReturn(Flux.fromIterable(creditEntityList));
        Flux<CreditEntity> result = creditServiceImplementation.getByClient("17593382");
        Assertions.assertNotNull(result);
    }

    @Test
    void getByClientAndDates() {
        CreditEntity creditEntity = new CreditEntity();
        creditEntity.setClientDocumentNumber("17593382");
        List<CreditEntity> creditEntityList = new ArrayList<>();
        creditEntityList.add(creditEntity);
        Mockito.when(creditRepository.findAll()).thenReturn(Flux.fromIterable(creditEntityList));
        Flux<CreditEntity> result = creditServiceImplementation.getByClientAndDates("17593382", new Date(), new Date());
        Assertions.assertNotNull(result);
    }
}