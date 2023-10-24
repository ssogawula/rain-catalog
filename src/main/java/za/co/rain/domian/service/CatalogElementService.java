package za.co.rain.domian.service;

import za.co.rain.domian.CatalogElement;
import za.co.rain.domian.LifeCycleStatus;

public interface CatalogElementService {

	
	/**
	 * This method will start catalog element conception
	 * and this initial life cycle status will be equals to
	 * {@link LifeCycleStatus#IN_STUDY}
	 * 
	 * @param catalogElement - Object to be created
	 * @return TODO
	 * 
	 */
	CatalogElement startCatalogElementConception(CatalogElement catalogElement);
	/**
	 * This method is used to accept catalog element
	 * conception
	 * @param id
	 * @param status TODO
	 * @return TODO
	 */
	CatalogElement acceptCatalogElementConception(Long id, LifeCycleStatus status);
	
	/**
	 * 
	 * @param id
	 * @param status TODO
	 * @return TODO
	 */
	CatalogElement approveCatalogElementDesign(Long id, LifeCycleStatus status);
	
	/**
	 * 
	 * @param id
	 * @param isTestOk
	 * @param status TODO
	 * @return TODO
	 */
	CatalogElement testCatalogElement(Long id, boolean isTestOk, LifeCycleStatus status);
	
	/**
	 * 
	 * @param id
	 * @param newStatus TODO
	 * @return TODO
	 */
	CatalogElement beginCatalogElementMarketing(Long id, LifeCycleStatus newStatus);
	
	void endCatalogElementMarketing(Long id, LifeCycleStatus newStatus);
	
	/**
	 * 
	 * @param catalogElementId
	 * @param customerId
	 */
	void sellCatalogElement(Long catalogElementId, Long customerId);
	
	/**
	 * 
	 * @param catalogId
	 * @param customerId TODO
	 * @return TODO
	 */
	CatalogElement cancelCustomerCatalogElement(Long catalogId, Long customerId);
}
