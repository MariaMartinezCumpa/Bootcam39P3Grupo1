package com.bootcamp.project.account.repository;

import com.bootcamp.project.account.entity.AccountEntity;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveCrudRepository<AccountEntity, ObjectId> {
    Mono<AccountEntity> findByNroDocument(final String nroDocument);
}
