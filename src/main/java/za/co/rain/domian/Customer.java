package za.co.rain.domian;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "CUSTOMER_NAME")
	private String customerName;
	
	public Customer() {}
	
	@ManyToMany(mappedBy = "customers", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<CatalogElement> catalogElements = new HashSet<CatalogElement>();
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public Set<CatalogElement> getCatalogElements() {
		return catalogElements;
	}
	
	public void setCatalogElements(Set<CatalogElement> catalogElements) {
		this.catalogElements = catalogElements;
	}
}
