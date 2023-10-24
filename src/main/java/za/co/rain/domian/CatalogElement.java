package za.co.rain.domian;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "CATALOG_ELEMENTS")
public class CatalogElement implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private LifeCycleStatus status;

	@Temporal(TemporalType.DATE)
	@Column(name = "MARKETING_START_DATE")
	private Date marketingStartDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "MARKETING_END_DATE")
	private Date marketingEndDate;

	private Boolean active;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "CUSTOMER_CATALOG_ELEMENT", joinColumns = {
			@JoinColumn(name = "CATALOG_ELEMENT_ID") }, inverseJoinColumns = { @JoinColumn(name = "CUSTOMER_ID") })
	private Collection<Customer> customers = new HashSet<Customer>();

	@ManyToOne
	@JoinColumn(name = "CATALOG_ELEMENT_ID", insertable = false, updatable = false)
	@JsonBackReference
	private Catalog catalog;

	public CatalogElement() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LifeCycleStatus getStatus() {
		return status;
	}

	public void setStatus(LifeCycleStatus status) {
		this.status = status;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public Date getMarketingStartDate() {
		return marketingStartDate;
	}

	public void setMarketingEndDate(Date marketingEndDate) {
		this.marketingEndDate = marketingEndDate;
	}

	public Date getMarketingEndDate() {
		return marketingEndDate;
	}

	public void setMarketingStartDate(Date marketingStartDate) {
		this.marketingStartDate = marketingStartDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Collection<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Collection<Customer> customers) {
		this.customers = customers;
	}
}
