package com.smarteshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.smarteshop.domain.Currency;

/**
 * Spring Data JPA repository for the Currency entity.
 */
@SuppressWarnings("unused")
public interface CurrencyRepository extends JpaRepository<Currency,Long>,
QueryDslPredicateExecutor<Currency> {

}
