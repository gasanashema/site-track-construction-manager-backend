package rw.auca.sitetrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.auca.sitetrack.model.SiteManager;

import java.util.List;
import java.util.UUID;

@Repository
public interface SiteManagerRepository extends JpaRepository<SiteManager, UUID> {

    @Query("SELECT DISTINCT sm FROM SiteManager sm JOIN sm.trades t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<SiteManager> findByTradeName(@Param("name") String name);
}
