package com.bootcamp.project.credit.repository;

import com.bootcamp.project.credit.entity.CreditEntity;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CreditRepository extends ReactiveCrudRepository<CreditEntity, ObjectId> {
}
