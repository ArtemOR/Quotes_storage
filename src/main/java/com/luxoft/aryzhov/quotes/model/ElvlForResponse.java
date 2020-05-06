package com.luxoft.aryzhov.quotes.model;

import java.util.Objects;

public class ElvlForResponse {

    private String isin;
    private String elvl;

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getElvl() {
        return elvl;
    }

    public void setElvl(String elvl) {
        this.elvl = elvl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElvlForResponse that = (ElvlForResponse) o;
        return Objects.equals(isin, that.isin) &&
                Objects.equals(elvl, that.elvl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isin, elvl);
    }

    @Override
    public String toString() {
        return "ElvlForResponse{" +
                "isin='" + isin + '\'' +
                ", elvl='" + elvl + '\'' +
                '}';
    }

}
