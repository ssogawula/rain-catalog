package za.co.rain.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import za.co.rain.domian.Catalog;
import za.co.rain.domian.CatalogElement;
import za.co.rain.domian.Customer;
import za.co.rain.domian.LifeCycleStatus;
import za.co.rain.repositories.CatalogElementRepository;
import za.co.rain.repositories.CustomerRepository;
import za.co.rain.service.impl.CatalogElementServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CatalogElementServiceTest {

	@Mock
	private CatalogElementRepository catalogElementRepository;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private CatalogElementServiceImpl catalogElementService;
	
	private CatalogElement catalogElement = new CatalogElement();
	private Customer customer = new Customer();
	
	@BeforeEach
	public void setUp() {
		catalogElement.setId(1L);
		catalogElement.setStatus(LifeCycleStatus.IN_STUDY);
		catalogElement.setMarketingStartDate(new Date());
		catalogElement.setActive(false);
		LocalDate localDate = LocalDate.of(2023, 10, 25);
		catalogElement.setMarketingEndDate(Date.from(localDate.atStartOfDay(ZoneId.systemDefault())
				.toInstant()));
		
		Catalog catalog = new Catalog();
		catalog.setId(1L);
		catalog.setCatalogDescription("2023 Router and SIM Catalog");
		catalog.setCatalogName("Router and SIM Catalog");
		catalog.getCatalogElements().add(catalogElement);
		
		customer.setId(1L);
		customer.setCustomerName("Test");
	}
	
	@Test
	void testStartCatalogElementConception() {
		
		when(catalogElementRepository.save(catalogElement)).thenReturn(catalogElement);
		catalogElementService.startCatalogElementConception(catalogElement);
		verify(catalogElementRepository, times(1)).save(catalogElement);
	}
	
	@Test
	void testAcceptCatalogConception() {
		
		LifeCycleStatus newSatus = LifeCycleStatus.IN_DESIGN;
		catalogElement.setStatus(LifeCycleStatus.IN_STUDY);
		when(catalogElementRepository.findById(1L)).thenReturn(Optional.of(catalogElement));
		catalogElementService.acceptCatalogElementConception(1L, newSatus);
		Optional<CatalogElement> catalogElementOptional = catalogElementRepository.findById(1L);
		CatalogElement catalogElement = catalogElementOptional.get();
		Assertions.assertNotNull(catalogElement);
		Assertions.assertEquals(catalogElement.getStatus(), LifeCycleStatus.IN_DESIGN);
		verify(catalogElementRepository, times(2)).findById(1L);
	}
	
	@Test
	void testApproveCatalogConception() {
		
		LifeCycleStatus newSatus = LifeCycleStatus.IN_TEST;
		catalogElement.setStatus(LifeCycleStatus.IN_DESIGN);
		when(catalogElementRepository.findById(1L)).thenReturn(Optional.of(catalogElement));
		catalogElementService.approveCatalogElementDesign(1L, newSatus);
		Optional<CatalogElement> catalogElementOptional = catalogElementRepository.findById(1L);
		CatalogElement catalogElement = catalogElementOptional.get();
		Assertions.assertNotNull(catalogElement);
		Assertions.assertEquals(catalogElement.getStatus(), LifeCycleStatus.IN_TEST);
		verify(catalogElementRepository, times(2)).findById(1L);
	}
	
	@Test
	void testCatalogTesting() {
		
		LifeCycleStatus newSatus = LifeCycleStatus.ACTIVE;
		catalogElement.setStatus(LifeCycleStatus.IN_TEST);
		when(catalogElementRepository.findById(1L)).thenReturn(Optional.of(catalogElement));
		catalogElementService.testCatalogElement(1L, true, newSatus);
		Optional<CatalogElement> catalogElementOptional = catalogElementRepository.findById(1L);
		CatalogElement catalogElement = catalogElementOptional.get();
		Assertions.assertNotNull(catalogElement);
		Assertions.assertEquals(catalogElement.getStatus(), LifeCycleStatus.ACTIVE);
		verify(catalogElementRepository, times(2)).findById(1L);
	}
	
	@Test
	void testCatalogElementMarketing() {
		
		LifeCycleStatus newSatus = LifeCycleStatus.LAUNCHED;
		catalogElement.setStatus(LifeCycleStatus.ACTIVE);
		when(catalogElementRepository.findById(1L)).thenReturn(Optional.of(catalogElement));
		catalogElementService.beginCatalogElementMarketing(1L, newSatus);
		Optional<CatalogElement> catalogElementOptional = catalogElementRepository.findById(1L);
		CatalogElement catalogElement = catalogElementOptional.get();
		Assertions.assertNotNull(catalogElement);
		Assertions.assertEquals(catalogElement.getStatus(), LifeCycleStatus.LAUNCHED);
		verify(catalogElementRepository, times(2)).findById(1L);
	}
	
	@Test
	void testCatalogElementSales() {
		
		catalogElement.setStatus(LifeCycleStatus.RETIRED);
		when(catalogElementRepository.findById(1L)).thenReturn(Optional.of(catalogElement));
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		catalogElementService.sellCatalogElement(1L, 1L);
		Optional<CatalogElement> catalogElementOptional = catalogElementRepository.findById(1L);
		CatalogElement catalogElement = catalogElementOptional.get();
		Assertions.assertNotNull(catalogElement);
		verify(catalogElementRepository, times(2)).findById(1L);
	}
	
	@Test
	void testCatalogElementEndMarketing() {
		
		LifeCycleStatus newStatus = LifeCycleStatus.RETIRED;
		catalogElement.setStatus(LifeCycleStatus.RETIRED);
		when(catalogElementRepository.findById(1L)).thenReturn(Optional.of(catalogElement));
		catalogElementService.endCatalogElementMarketing(1L, newStatus);
		Optional<CatalogElement> catalogElementOptional = catalogElementRepository.findById(1L);
		CatalogElement catalogElement = catalogElementOptional.get();
		Assertions.assertNotNull(catalogElement);
		Assertions.assertEquals(catalogElement.getStatus(), LifeCycleStatus.RETIRED);
		verify(catalogElementRepository, times(2)).findById(1L);
	}
	
	@Test
	void testCancelCatalogElement() {
		
		catalogElement.getCustomers().add(customer);
		when(catalogElementRepository.findById(1L)).thenReturn(Optional.of(catalogElement));
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		catalogElementService.cancelCustomerCatalogElement(1L, customer.getId());
		Optional<CatalogElement> catalogElementOptional = catalogElementRepository.findById(1L);
		CatalogElement catalogElement = catalogElementOptional.get();
		Assertions.assertNotNull(catalogElement);
		Assertions.assertEquals(catalogElement.getStatus(), LifeCycleStatus.OBSOLETE);
				
		verify(catalogElementRepository, times(2)).findById(1L);
	}
	
}
