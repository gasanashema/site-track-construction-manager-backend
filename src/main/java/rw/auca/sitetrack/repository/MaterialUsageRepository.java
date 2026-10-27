package rw.auca.sitetrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.auca.sitetrack.model.MaterialUsage;

import java.util.List;
import java.util.UUID;

@Repository
public interface MaterialUsageRepository extends JpaRepository<MaterialUsage, UUID> {

    @Query("SELECT m FROM MaterialUsage m WHERE m.dailyReport.project.projectCode = :projectCode ORDER BY m.recordedDate DESC")
    List<MaterialUsage> findByProjectCode(@Param("projectCode") String projectCode);
}
