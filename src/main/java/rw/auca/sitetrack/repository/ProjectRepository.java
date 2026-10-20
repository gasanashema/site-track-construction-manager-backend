package rw.auca.sitetrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.auca.sitetrack.model.Project;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findByDistrictIgnoreCaseOrderByProjectNameAsc(String district);

    @Query("SELECT DISTINCT d.project FROM DailyReport d WHERE d.siteManager.id = :siteManagerId")
    List<Project> findProjectsBySiteManagerId(@Param("siteManagerId") UUID siteManagerId);
}
