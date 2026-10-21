package rw.auca.sitetrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
