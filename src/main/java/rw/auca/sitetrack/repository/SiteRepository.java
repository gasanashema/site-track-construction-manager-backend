package rw.auca.sitetrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.auca.sitetrack.model.Site;

import java.util.List;
import java.util.UUID;

@Repository
public interface SiteRepository extends JpaRepository<Site, UUID> {

    @Query("SELECT s FROM Site s WHERE NOT EXISTS (SELECT sm FROM SiteManager sm WHERE sm.site = s) ORDER BY s.siteNumber ASC")
    List<Site> findFreeSites();
}
