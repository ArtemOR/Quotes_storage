package com.luxoft.aryzhov.quotes.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity(name = "elvl")
@Table(name = "elvls")
public class Elvl extends BaseEntity{
    @Column(name = "isin")
    @Size(min = 12, max = 12)
    private String isin;

    @Column(name = "elvl")
    private BigDecimal elvl;

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public BigDecimal getElvl() {
        return elvl;
    }

    public void setElvl(BigDecimal elvl) {
        this.elvl = elvl;
    }
}
