package rw.auca.sitetrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.auca.sitetrack.model.MaterialUsage;

import java.util.UUID;

@Repository
public interface MaterialUsageRepository extends JpaRepository<MaterialUsage, UUID> {
}
