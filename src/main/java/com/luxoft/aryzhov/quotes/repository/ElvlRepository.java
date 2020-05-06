package com.luxoft.aryzhov.quotes.repository;

import com.luxoft.aryzhov.quotes.model.entities.Elvl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface ElvlRepository extends JpaRepository<Elvl, Long> {
    @Query("select e from elvl e where e.isin = :isin")
    Elvl searchElvlByIsin(@Param("isin") String isin);

    @Modifying
    @Transactional
    @Query("delete from elvl e where e.isin = :isin")
    void deleteByIsin(@Param("isin") String isin);
}
