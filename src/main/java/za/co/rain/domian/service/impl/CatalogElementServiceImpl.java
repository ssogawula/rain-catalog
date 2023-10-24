package za.co.rain.domian.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import za.co.rain.domian.CatalogElement;
import za.co.rain.domian.Customer;
import za.co.rain.domian.LifeCycleStatus;
import za.co.rain.domian.repositories.CatalogElementRepository;
import za.co.rain.domian.repositories.CustomerRepository;
import za.co.rain.domian.service.CatalogElementService;
import za.co.rain.exeptions.ServiceLayerExeption;

@Transactional
@Service
public class CatalogElementServiceImpl implements CatalogElementService {

	private final CatalogElementRepository catalogElementRepository;
	
	private final CustomerRepository customerRepository;
	
	
	public CatalogElementServiceImpl(CatalogElementRepository catalogElementRepository, CustomerRepository customerRepository) {
		this.catalogElementRepository = catalogElementRepository;
		this.customerRepository = customerRepository;
	}
	
	@Override
	public CatalogElement startCatalogElementConception(CatalogElement catalogElement) {
		return catalogElementRepository.save(catalogElement);
	}

	@Override
	public CatalogElement acceptCatalogElementConception(Long id, LifeCycleStatus newStatus) {
		
		CatalogElement catalogElement = getCatalogElementById(id);
		
		if (catalogElement.getStatus() == LifeCycleStatus.IN_STUDY) {
			catalogElement.setStatus(newStatus);
			catalogElementRepository.save(catalogElement);
		} else {
			throw new ServiceLayerExeption("Catalog element already accepted");
		}
		
		return catalogElement;
	}

	@Override
	public CatalogElement approveCatalogElementDesign(Long id, LifeCycleStatus newStatus) {
		
		CatalogElement catalogElement = getCatalogElementById(id);
		
		if (catalogElement.getStatus() == LifeCycleStatus.IN_DESIGN) {
			catalogElement.setStatus(newStatus);
			catalogElementRepository.save(catalogElement);
		} else {
			throw new ServiceLayerExeption("Catalog element already approved");
		}
		
		return catalogElement;
	}

	@Override
	public CatalogElement testCatalogElement(Long id, boolean isTestOk, LifeCycleStatus newStatus) {
		
		CatalogElement catalogElement = getCatalogElementById(id);
		
		if (catalogElement.getStatus() == LifeCycleStatus.IN_TEST && isTestOk) {
			catalogElement.setStatus(newStatus);
			catalogElementRepository.save(catalogElement);
		} else {
			catalogElement.setStatus(LifeCycleStatus.REJECTED);
		}
		return catalogElement;
	}

	@Override
	public CatalogElement beginCatalogElementMarketing(Long id, LifeCycleStatus newStatus) {
		
		CatalogElement catalogElement = getCatalogElementById(id);
		if (catalogElement.getStatus() == LifeCycleStatus.ACTIVE || catalogElement.getStatus() == LifeCycleStatus.RETIRED) {
			catalogElement.setStatus(newStatus);
			catalogElementRepository.save(catalogElement);
		} else {
			throw new ServiceLayerExeption("Catalog element must tested before you start marketing id");
		}
		
		return catalogElement;
	}
	
	@Override
	public void endCatalogElementMarketing(Long id, LifeCycleStatus newStatus) {
		
		CatalogElement catalogElement = getCatalogElementById(id);
		
		if (catalogElement.getStatus() == LifeCycleStatus.LAUNCHED) {
			catalogElement.setStatus(newStatus);
			catalogElementRepository.save(catalogElement);
		}
		
	}
	
	@Override
	public void sellCatalogElement(Long catalogElementId, Long customerId) {
		
		CatalogElement catalogElement = getCatalogElementById(catalogElementId);
		
		Optional<Customer> customerOptinal = customerRepository.findById(customerId);
		
		if (!customerOptinal.isPresent() && catalogElement.getStatus() == LifeCycleStatus.RETIRED) {
			throw new ServiceLayerExeption("Retired catalog element is not sold to new customers.");
		}
		
		if (catalogElement.getStatus() == LifeCycleStatus.LAUNCHED || catalogElement.getStatus() == LifeCycleStatus.RETIRED) {
			
			if (customerOptinal.isPresent() ) {
				Customer customer = customerOptinal.get();
				catalogElement.getCustomers().add(customer);
				catalogElement.setActive(true);
				catalogElementRepository.save(catalogElement);
				
			} else if(catalogElement.getStatus() == LifeCycleStatus.RETIRED) {
				
				throw new ServiceLayerExeption("New customers are not allowed to buy this item");
			}
		} else {
			
			throw new ServiceLayerExeption(catalogElement.getName() +"is not sold yet.");
		}
	}
	
	@Override
	public CatalogElement cancelCustomerCatalogElement(Long catalogId, Long customerId) {
		
		CatalogElement catalogElement = catalogElementRepository.findById(catalogId).orElse(null);
		Customer customer = customerRepository.findById(customerId).orElse(null);
		
		if (!catalogElement.getCustomers().isEmpty()  && customer != null) {
			catalogElement.getCustomers().remove(customer);
		}
		
		if (catalogElement.getCustomers().isEmpty()) {
			catalogElement.setStatus(LifeCycleStatus.OBSOLETE);
		}
			
		if (catalogElement != null) {
			catalogElement.setActive(false);
			catalogElement.setCatalog(null);
			catalogElementRepository.save(catalogElement);
		}
		
		return catalogElement;
	}
	
	private CatalogElement getCatalogElementById(Long id) {
		return catalogElementRepository.findById(id)
				.orElseThrow(() -> new ServiceLayerExeption("Catalog element not found!"));
	}
}
