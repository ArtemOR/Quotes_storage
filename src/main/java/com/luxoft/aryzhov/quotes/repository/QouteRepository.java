package com.luxoft.aryzhov.quotes.repository;

import com.luxoft.aryzhov.quotes.model.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QouteRepository extends JpaRepository<Quote, Long> {

    @Modifying
    @Transactional
    @Query("delete from quote q where q.isin = :isin")
    void deleteByIsin(@Param("isin") String isin);
}
