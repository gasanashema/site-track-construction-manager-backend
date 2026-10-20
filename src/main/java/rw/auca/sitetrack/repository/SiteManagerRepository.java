package rw.auca.sitetrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.auca.sitetrack.model.SiteManager;

import java.util.UUID;

@Repository
public interface SiteManagerRepository extends JpaRepository<SiteManager, UUID> {
}
