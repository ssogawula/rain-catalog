package za.co.rain.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import za.co.rain.domian.Catalog;
import za.co.rain.domian.CatalogElement;
import za.co.rain.domian.Customer;
import za.co.rain.domian.LifeCycleStatus;
import za.co.rain.domian.repositories.CatalogElementRepository;
import za.co.rain.domian.repositories.CatalogRepository;
import za.co.rain.domian.repositories.CustomerRepository;


@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CatalogElementControllerIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CatalogRepository catalogRepository;
	
	
	private final CatalogElement catalogElement =  new CatalogElement();;


	@Order(1)
	@Test
	public void should_save_catalogElement_withStatus_in_study() throws Exception{
		
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
		catalogRepository.save(catalog);
		
		mockMvc.perform(post("/api/v1/catalog-elements")
				.content(objectMapper.writeValueAsString(catalogElement))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value(Matchers.is("IN_STUDY")))
			.andDo(MockMvcResultHandlers.print());
		
	
	}
	
	@Order(2)
	@Test
	public void should_update_catalogElement_withStatus_in_design() throws Exception{

		LifeCycleStatus status = LifeCycleStatus.IN_DESIGN;
		
		mockMvc.perform(patch("/api/v1/catalog-elements/{id}/accept-catalog-element", 1L)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(status)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status").value(Matchers.is("IN_DESIGN")))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Order(3)
	@Test
	public void should_update_catalogElement_withStatus_in_test() throws Exception{
		
		LifeCycleStatus status = LifeCycleStatus.IN_TEST;

		mockMvc.perform(patch("/api/v1/catalog-elements/{id}/approve-catalog-element-design", 1L)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(status)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status").value(Matchers.is("IN_TEST")))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Order(4)
	@Test
	public void should_update_catalogElement_withStatus_active_when_testOk() throws Exception{
		
		LifeCycleStatus status = LifeCycleStatus.ACTIVE;
		
		mockMvc.perform(patch("/api/v1/catalog-elements/{id}/test-catalog-element-design?isTestOk=true", 1L)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(status)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status").value(Matchers.is("ACTIVE")))
		.andDo(MockMvcResultHandlers.print());
	}
	
	
	@Order(5)
	@Test
	public void should_sell_catalog_element() throws Exception{
		
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setCustomerName("Test");
		customerRepository.save(customer);
		
		mockMvc.perform(patch("/api/v1/catalog-elements/{id}/sell-catalog-element?customerId=1", 1L))
		.andExpect(status().isOk());
	}
	
	@Order(6)
	@Test
	public void should_begin_catalog_element() throws Exception{
		
		LifeCycleStatus newStatus = LifeCycleStatus.LAUNCHED;
		
		mockMvc.perform(patch("/api/v1/catalog-elements/{id}/begin-catalog-element-marketing", 1L)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(newStatus)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status").value(Matchers.is("LAUNCHED")))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Order(7)
	@Test
	public void should_cancel_catalog_element() throws Exception{
		
		Customer customer  = customerRepository.findById(1L).orElse(null);
		
		mockMvc.perform(patch("/api/v1/catalog-elements/{catalogId}/cancel-catalog-element", 1L)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(customer)))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}

	@Order(8)
	@Test
	public void should_update_catalogElement_withStatus_rejected_when_not_testOk() throws Exception{
		
		LifeCycleStatus status = LifeCycleStatus.REJECTED;
		
		mockMvc.perform(patch("/api/v1/catalog-elements/{id}/test-catalog-element-design?isTestOk=false", 1L)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(status)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status").value(Matchers.is("REJECTED")))
		.andDo(MockMvcResultHandlers.print());
	}
}
