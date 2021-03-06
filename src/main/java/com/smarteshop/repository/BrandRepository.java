package com.smarteshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.smarteshop.domain.catalog.Brand;

/**
 * Spring Data JPA repository for the Brand entity.
 */
@SuppressWarnings("unused")
public interface BrandRepository extends JpaRepository<Brand,Long>,QueryDslPredicateExecutor<Brand>{

}
