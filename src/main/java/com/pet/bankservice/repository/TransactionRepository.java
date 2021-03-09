package com.pet.bankservice.repository;

import com.pet.bankservice.entity.Account;
import com.pet.bankservice.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("FROM Transaction t WHERE t.fromAccount=?1 OR t.toAccount=?1 "
            + "ORDER BY t.dateTime DESC, t.id DESC")
    Page<Transaction> findAll(Pageable pageable, Account account);
}
