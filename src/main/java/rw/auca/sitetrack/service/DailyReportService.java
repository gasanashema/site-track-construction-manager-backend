package rw.auca.sitetrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.sitetrack.model.DailyReport;
import rw.auca.sitetrack.repository.DailyReportRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DailyReportService {

    @Autowired
    private DailyReportRepository dailyReportRepository;

    public String save(DailyReport dailyReport) {
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
        dailyReport.setId(id);
        dailyReportRepository.save(dailyReport);
        return "Daily report updated successfully";
    }

    public String delete(UUID id) {
        if (!dailyReportRepository.existsById(id)) {
            return "The daily report with that id does not exist";
        }
        dailyReportRepository.deleteById(id);
        return "Daily report deleted successfully";
    }
}
