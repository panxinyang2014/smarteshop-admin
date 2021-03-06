package com.smarteshop.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smarteshop.common.entity.BusinessObjectEntity;
import com.smarteshop.domain.common.BusinessObjectInterface;

/**
 * A Variant.
 */
@Entity
@Table(name = "variant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "variant")
public class Variant extends BusinessObjectEntity<Long, Variant> implements BusinessObjectInterface {
    /**
   *
   */
  private static final long serialVersionUID = 7301922458198250569L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "display_order")
    private Integer displayOrder;

    @OneToMany(mappedBy = "variant", fetch=FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VariantValue> variantValues = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Variant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Variant description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public Variant displayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Set<VariantValue> getVariantValues() {
        return variantValues;
    }

    public Variant variantValues(Set<VariantValue> variantValues) {
        this.variantValues = variantValues;
        return this;
    }

    public Variant addVariantValue(VariantValue variantValue) {
        variantValues.add(variantValue);
        variantValue.setVariant(this);
        return this;
    }

    public Variant removeVariantValue(VariantValue variantValue) {
        variantValues.remove(variantValue);
        variantValue.setVariant(null);
        return this;
    }

    public void setVariantValues(Set<VariantValue> variantValues) {
        this.variantValues = variantValues;
    }

}