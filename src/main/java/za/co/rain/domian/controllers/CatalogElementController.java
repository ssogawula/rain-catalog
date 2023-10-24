package za.co.rain.domian.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import za.co.rain.domian.CatalogElement;
import za.co.rain.domian.Customer;
import za.co.rain.domian.LifeCycleStatus;
import za.co.rain.domian.service.CatalogElementService;

@RestController
@RequestMapping("/api/v1")
public class CatalogElementController {

	private final CatalogElementService catalogElementService;
	
	
	public CatalogElementController(CatalogElementService catalogElementService) {
		this.catalogElementService = catalogElementService;
	}
	
	@PostMapping("/catalog-elements")
	public ResponseEntity<?> startCatalogElementConception(@RequestBody CatalogElement catalogElement) {
		return ResponseEntity.ok(catalogElementService.startCatalogElementConception(catalogElement));
	}
	
	@PatchMapping("/catalog-elements/{id}/accept-catalog-element")
	public ResponseEntity<?> acceptCatalogElementConception(@PathVariable Long id, @RequestBody LifeCycleStatus newStatus) {
		return ResponseEntity.ok(catalogElementService.acceptCatalogElementConception(id, newStatus));
	}
	
	@PatchMapping("/catalog-elements/{id}/approve-catalog-element-design")
	public ResponseEntity<CatalogElement> approveCatalogElementDesign(@PathVariable Long id, @RequestBody LifeCycleStatus newStatus) {
		return ResponseEntity.ok(catalogElementService.approveCatalogElementDesign(id, newStatus));
	}
	
	@PatchMapping("/catalog-elements/{id}/test-catalog-element-design")
	public ResponseEntity<CatalogElement> testCatalogElementDesign(@PathVariable Long id, @RequestParam(required = true, name = "isTestOk") Boolean isTestOk, @RequestBody LifeCycleStatus status) {
		CatalogElement catalogElement = catalogElementService.testCatalogElement(id, isTestOk, status);
		return ResponseEntity.ok(catalogElement);
	}
	
	
	@PatchMapping("/catalog-elements/{id}/begin-catalog-element-marketing")
	public ResponseEntity<CatalogElement> beginCatalogElementMarketing(@PathVariable Long id, @RequestBody LifeCycleStatus newStatus) {
		CatalogElement catalogElement = catalogElementService.beginCatalogElementMarketing(id, newStatus);
		return ResponseEntity.ok(catalogElement);
	}
	
	@PatchMapping("/catalog-elements/{id}/end-catalog-element-marketing")
	public ResponseEntity<CatalogElement> endCatalogElementMarketing(@PathVariable Long id) {
		
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/catalog-elements/{id}/sell-catalog-element")
	public ResponseEntity<?> sellCatalogElement(@PathVariable(name = "id") Long catalogId, @RequestParam(required = true) Long customerId) {
		
		catalogElementService.sellCatalogElement(catalogId, customerId);
		
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/catalog-elements/{catalogId}/cancel-catalog-element")
	public ResponseEntity<?> cancelCustomerCatalog(@PathVariable Long catalogId, @RequestBody Customer customer) {
		
		CatalogElement catalogElement = catalogElementService.cancelCustomerCatalogElement(catalogId, customer.getId());
		
		return ResponseEntity.ok(catalogElement);
	}
}
