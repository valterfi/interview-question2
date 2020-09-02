package com.backbase.numbers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backbase.numbers.model.NumberContainer;

@Repository
public interface NumbersJpaRepository extends JpaRepository<NumberContainer, Long> {

}
