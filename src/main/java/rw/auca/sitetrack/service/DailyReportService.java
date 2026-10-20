package rw.auca.sitetrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.sitetrack.model.DailyReport;
import rw.auca.sitetrack.model.DailyReportStatus;
import rw.auca.sitetrack.repository.DailyReportRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DailyReportService {

    @Autowired
    private DailyReportRepository dailyReportRepository;

    public String save(DailyReport dailyReport) {
        if (dailyReport.getReportDate().isBefore(LocalDate.now())) {
            return "Daily report date cannot be in the past";
        }
        if (dailyReportRepository.existsBySiteManagerAndReportDateAndStatusNot(dailyReport.getSiteManager(), dailyReport.getReportDate(), DailyReportStatus.REJECTED)) {
            return "Site manager already has a daily report for that date";
        }
        dailyReport.setStatus(DailyReportStatus.DRAFT);
        dailyReportRepository.save(dailyReport);
        return "Daily report saved successfully";
    }

    public List<DailyReport> getAll() {
        return dailyReportRepository.findAll();
    }

    public Optional<DailyReport> getById(UUID id) {
        return dailyReportRepository.findById(id);
    }

    public String update(UUID id, DailyReport dailyReport) {
        if (!dailyReportRepository.existsById(id)) {
            return "The daily report with that id does not exist";
        }
        DailyReport existing = dailyReportRepository.findById(id).get();
        dailyReport.setId(id);
        dailyReport.setStatus(existing.getStatus());
        dailyReportRepository.save(dailyReport);
        return "Daily report updated successfully";
    }

    public String updateStatus(UUID id, DailyReportStatus newStatus) {
        Optional<DailyReport> found = dailyReportRepository.findById(id);
        if (found.isEmpty()) {
            return "The daily report with that id does not exist";
        }
        DailyReport dailyReport = found.get();
        DailyReportStatus current = dailyReport.getStatus();
        boolean allowed = false;
        if (current == DailyReportStatus.DRAFT) {
            allowed = (newStatus == DailyReportStatus.SUBMITTED || newStatus == DailyReportStatus.REJECTED);
        } else if (current == DailyReportStatus.SUBMITTED) {
            allowed = (newStatus == DailyReportStatus.APPROVED || newStatus == DailyReportStatus.REJECTED);
        }
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        dailyReport.setStatus(newStatus);
        dailyReportRepository.save(dailyReport);
        return "Daily report status updated successfully";
    }

    public String delete(UUID id) {
        if (!dailyReportRepository.existsById(id)) {
            return "The daily report with that id does not exist";
        }
        dailyReportRepository.deleteById(id);
        return "Daily report deleted successfully";
    }

    public List<DailyReport> getByStatus(DailyReportStatus status) {
        return dailyReportRepository.findByStatusOrderByReportDateAsc(status);
    }

    public List<DailyReport> getBetween(LocalDate start, LocalDate end) {
        return dailyReportRepository.findByReportDateBetweenOrderByReportDateAsc(start, end);
    }
}
