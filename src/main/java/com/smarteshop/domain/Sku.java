package com.smarteshop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smarteshop.common.entity.BusinessObjectEntity;
import com.smarteshop.domain.enumeration.StatusEnum;

/**
 * A Sku.
 */
@Entity
@Table(name = "sku")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sku")
public class Sku extends BusinessObjectEntity<Long, Sku>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;


    @NotNull
    @Column(name = "RETAIL_PRICE", precision = 19, scale = 5,nullable=false)
    protected BigDecimal retailPrice;

    @NotNull
    @Column(name = "SALE_PRICE", precision = 19, scale = 5,nullable=false)
    private BigDecimal salePrice;

    @Embedded
    protected Dimension dimension = new Dimension();

    @Embedded
    protected Weight weight = new Weight();


    @Column(name = "default_sku")
    private Boolean defaultSKU;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status = StatusEnum.ACTIVITY;

    @Column(name = "ACTIVE_START_DATE")
    protected ZonedDateTime activeStartDate;

    @Column(name = "ACTIVE_END_DATE")
    protected ZonedDateTime activeEndDate;

    @OneToOne(optional = true, targetEntity = Product.class, cascade = {CascadeType.ALL})
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "DEFAULT_PRODUCT_ID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blProducts")
    protected Product defaultProduct;

    /**
     * This relationship will be non-null if and only if this Sku is contained in the list of
     * additional Skus for a Product (for Skus based on ProductOptions)
     */
    @ManyToOne(optional = true, targetEntity = Product.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "ADDL_PRODUCT_ID")
    protected Product product;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Sku code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Sku name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRetailPrice() {
      return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
      this.retailPrice = retailPrice;
    }

    public BigDecimal getSalePrice() {
      return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
      this.salePrice = salePrice;
    }

    public Dimension getDimension() {
      return dimension;
    }

    public void setDimension(Dimension dimension) {
      this.dimension = dimension;
    }

    public Weight getWeight() {
      return weight;
    }

    public void setWeight(Weight weight) {
      this.weight = weight;
    }

    public Boolean getDefaultSKU() {
      return defaultSKU;
    }

    public void setDefaultSKU(Boolean defaultSKU) {
      this.defaultSKU = defaultSKU;
    }

    public StatusEnum getStatus() {
      return status;
    }

    public void setStatus(StatusEnum status) {
      this.status = status;
    }

    public Product getDefaultProduct() {
      return defaultProduct;
    }

    public void setDefaultProduct(Product defaultProduct) {
      this.defaultProduct = defaultProduct;
    }

    public Product getProduct() {
      return product;
    }

    public void setProduct(Product product) {
      this.product = product;
    }

}
