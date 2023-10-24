package za.co.rain.scheduler;

import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import za.co.rain.domian.CatalogElement;
import za.co.rain.domian.LifeCycleStatus;
import za.co.rain.repositories.CatalogElementRepository;
import za.co.rain.repositories.CustomerRepository;

@Component
public class CatalogElementScheduler {

	private final CatalogElementRepository catalogElementRepository;
	
	private final CustomerRepository customerRepository;
	
	public CatalogElementScheduler(CatalogElementRepository catalogElementRepository, CustomerRepository customerRepository) {
		this.catalogElementRepository = catalogElementRepository;
		this.customerRepository = customerRepository;
	}
	
	@Scheduled(cron = "0 0 * * * 1")
	public void scheduleCatalogElement() {
	
		List<CatalogElement> catalogElements = catalogElementRepository.findAll();
		
		if (!catalogElements.isEmpty()) {
			
			catalogElements.forEach(catalogElement -> {
				
				if (catalogElement.getMarketingStartDate() != null && catalogElement.getMarketingStartDate().after(new Date())) {
					catalogElement.setStatus(LifeCycleStatus.RETIRED);
					catalogElementRepository.save(catalogElement);
				}
				
				if (catalogElement.getMarketingEndDate() != null && catalogElement.getMarketingEndDate().equals(new Date())) {
					catalogElement.setStatus(LifeCycleStatus.RETIRED);
					catalogElementRepository.save(catalogElement);
				}
			});
		}
		
	}
}
