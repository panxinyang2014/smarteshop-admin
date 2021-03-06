package com.smarteshop.domain.catalog;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.smarteshop.common.entity.BusinessObjectEntity;
import com.smarteshop.domain.enumeration.StatusEnum;

/**
 * A Brand.
 */
@Entity
@Table(name = "brand")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "brand")
public class Brand  extends BusinessObjectEntity<Long, Brand>  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Lob
	@Column(name = "brief_desc")
	private String briefDesc;

	@Lob
	@Column(name = "description")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusEnum status;

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

	public Brand name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public Brand description(String description) {
		this.description = description;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public Brand status(StatusEnum status) {
		this.status = status;
		return this;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}



	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Brand brand = (Brand) o;
		if(brand.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, brand.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public String getBriefDesc() {
		return briefDesc;
	}

	public void setBriefDesc(String briefDesc) {
		this.briefDesc = briefDesc;
	}

	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + ", briefDesc=" + briefDesc + ", description=" + description
				+ ", status=" + status + "]";
	}


}
