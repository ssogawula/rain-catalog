package za.co.rain.domian.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import za.co.rain.domian.CatalogElement;

public interface CatalogElementRepository extends JpaRepository<CatalogElement, Long> {

}
