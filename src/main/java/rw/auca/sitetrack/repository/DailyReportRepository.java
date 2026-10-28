package rw.auca.sitetrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.auca.sitetrack.model.DailyReport;

import rw.auca.sitetrack.model.DailyReportStatus;
import rw.auca.sitetrack.model.SiteManager;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DailyReportRepository extends JpaRepository<DailyReport, UUID> {

    List<DailyReport> findByStatusOrderByReportDateAsc(DailyReportStatus status);

    List<DailyReport> findByReportDateBetweenOrderByReportDateAsc(LocalDate start, LocalDate end);

    boolean existsBySiteManagerAndReportDateAndStatusNot(SiteManager siteManager, LocalDate reportDate, DailyReportStatus status);

    @Query("SELECT d.status, COUNT(d) FROM DailyReport d GROUP BY d.status")
    List<Object[]> countDailyReportsByStatus();

    @Modifying
    @Query("UPDATE DailyReport d SET d.status = rw.auca.sitetrack.model.DailyReportStatus.REJECTED WHERE d.siteManager.id = :siteManagerId AND d.reportDate = :date AND d.status <> rw.auca.sitetrack.model.DailyReportStatus.APPROVED")
    int cancelDayForSiteManager(@Param("siteManagerId") UUID siteManagerId, @Param("date") LocalDate date);

    long countByReportDate(LocalDate reportDate);

    long countByStatus(DailyReportStatus status);
}
