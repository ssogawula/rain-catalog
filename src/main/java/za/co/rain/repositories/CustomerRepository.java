package za.co.rain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import za.co.rain.domian.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
