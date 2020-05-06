package com.luxoft.aryzhov.quotes.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity(name = "quote")
@Table(name = "quotes")
public class Quote extends BaseEntity{

    @Column(name = "isin")
    @Size(min = 12, max = 12)
    private String isin;

    @Column(name = "ask")
    private BigDecimal ask;

    @Column(name = "bid")
    private BigDecimal bid;

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }
}
