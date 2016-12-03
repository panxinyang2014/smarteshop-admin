package com.smarteshop.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Currency.
 */
@Entity
@Table(name = "currency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "currency")
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "symbol_left")
    private String symbolLeft;

    @Column(name = "symbol_right")
    private String symbolRight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Currency title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSymbolLeft() {
        return symbolLeft;
    }

    public Currency symbolLeft(String symbolLeft) {
        this.symbolLeft = symbolLeft;
        return this;
    }

    public void setSymbolLeft(String symbolLeft) {
        this.symbolLeft = symbolLeft;
    }

    public String getSymbolRight() {
        return symbolRight;
    }

    public Currency symbolRight(String symbolRight) {
        this.symbolRight = symbolRight;
        return this;
    }

    public void setSymbolRight(String symbolRight) {
        this.symbolRight = symbolRight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Currency currency = (Currency) o;
        if(currency.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, currency.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Currency{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", symbolLeft='" + symbolLeft + "'" +
            ", symbolRight='" + symbolRight + "'" +
            '}';
    }
}
