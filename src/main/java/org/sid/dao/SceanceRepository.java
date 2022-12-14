package org.sid.dao;

import org.sid.entities.Sceance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface SceanceRepository extends JpaRepository<Sceance, Long> {
}
