package org.sid.dao;

import org.sid.entities.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface SalleRepository extends JpaRepository<Salle, Long> {
}
