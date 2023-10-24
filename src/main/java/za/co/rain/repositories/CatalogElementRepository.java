package za.co.rain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import za.co.rain.domian.CatalogElement;

public interface CatalogElementRepository extends JpaRepository<CatalogElement, Long> {

}
