package za.co.rain.services;

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
	 * @param id - CatalogElement ID 	 
	 * @param status - CatalogElemet LifeCycleStatus
	 * @return {@link CatalogElement} entity
	 */
	CatalogElement acceptCatalogElementConception(Long id, LifeCycleStatus status);
	
	/**
	 * This method is used to approve catalog element
	 * conception
	 * @param id - CatalogElement ID 	 
	 * @param status - CatalogElemet LifeCycleStatus
	 * @return {@link CatalogElement} entity
	 */
	CatalogElement approveCatalogElementDesign(Long id, LifeCycleStatus status);
	
	/**
	 * This method is used to test catalog element and update CatalogElement status
	 * to {@link LifeCycleStatus#ACTIVE} or {@link LifeCycleStatus#REJECTED} depending on the test results
	 * @param id - CatalogElement ID 	 
	 * @param isTestOk - Flag that indicate if CatalogElement testing if OK or KO
	 * @param status - CatalogElemet LifeCycleStatus
	 * @return {@link CatalogElement} entity
	 */
	CatalogElement testCatalogElement(Long id, boolean isTestOk, LifeCycleStatus status);
	
	/**
	 * This method is used to begin  catalog element catalog marketing process 
	 * and update CatalogElement status to {@link LifeCycleStatus#LAUNCHED}
	 * @param id - CatalogElement ID 	 
	 * @param status - CatalogElemet LifeCycleStatus
	 * @return {@link CatalogElement} entity
	 */
	CatalogElement beginCatalogElementMarketing(Long id, LifeCycleStatus newStatus);
	
	/**
	 * This method is used to end catalog element marketing and update CatalogElement status
	 * to {@link LifeCycleStatus#RETIRED}
	 * @param id - CatalogElement ID 	 
	 * @param status - CatalogElemet LifeCycleStatus
	 */
	
	void endCatalogElementMarketing(Long id, LifeCycleStatus newStatus);
	
	/**
	 * This method is used to sell catalog element to customers.
	 * @param id - CatalogElement ID 	 
	 * @param customerId - Existing customer ID.
	 */
	void sellCatalogElement(Long id, Long customerId);
	
	/**
	 * This method is used to cancel catalog element.
	 * @param id - CatalogElement ID 	 
	 * @param customerId - Existing customer ID.
	 */
	CatalogElement cancelCustomerCatalogElement(Long id, Long customerId);
}
