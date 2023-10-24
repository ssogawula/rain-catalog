package za.co.rain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import za.co.rain.domian.Catalog;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {

}
