package za.co.rain.domian;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
/**
 * 
 */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "catalogs")
public class Catalog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CATALOG_NAME")
	private String catalogName;
	
	@Column(name = "CATALOG_DESC")
	private String catalogDescription;
	
	@Column(name = "TRAN_DATE")
	private LocalDateTime tranDate;
	
	@Column(name = "EXPIRY_DATE")
	private LocalDate expiryDate;
	
	@OneToMany(mappedBy = "catalog",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<CatalogElement> catalogElements = new HashSet<CatalogElement>();

	
	public Catalog() {}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCatalogName() {
		return catalogName;
	}


	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}


	public String getCatalogDescription() {
		return catalogDescription;
	}


	public void setCatalogDescription(String catalogDescription) {
		this.catalogDescription = catalogDescription;
	}


	public LocalDateTime getTranDate() {
		return tranDate;
	}


	public void setTranDate(LocalDateTime tranDate) {
		this.tranDate = tranDate;
	}


	public LocalDate getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}


	public Set<CatalogElement> getCatalogElements() {
		return catalogElements;
	}


	public void setCatalogElements(Set<CatalogElement> catalogElements) {
		this.catalogElements = catalogElements;
	}
}
