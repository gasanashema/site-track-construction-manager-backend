package rw.auca.sitetrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.sitetrack.model.DailyReport;
import rw.auca.sitetrack.model.DailyReportStatus;
import rw.auca.sitetrack.model.MaterialUsage;
import rw.auca.sitetrack.repository.DailyReportRepository;
import rw.auca.sitetrack.repository.MaterialUsageRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MaterialUsageService {

    @Autowired
    private MaterialUsageRepository materialUsageRepository;

    @Autowired
    private DailyReportRepository dailyReportRepository;

    public String save(UUID dailyReportId, MaterialUsage materialUsage) {
        Optional<DailyReport> optional = dailyReportRepository.findById(dailyReportId);
        if (optional.isEmpty()) {
            return "The daily report with that id does not exist";
        }
        DailyReport dailyReport = optional.get();
        if (dailyReport.getStatus() != DailyReportStatus.APPROVED) {
            return "Material usages are only for APPROVED daily reports";
        }
        materialUsage.setRecordedDate(LocalDate.now());
        materialUsage.setDailyReport(dailyReport);
        materialUsageRepository.save(materialUsage);
        return "Material usage saved successfully";
    }

    public List<MaterialUsage> getAll() {
        return materialUsageRepository.findAll();
    }

    public Optional<MaterialUsage> getById(UUID id) {
        return materialUsageRepository.findById(id);
    }

    public String update(UUID id, MaterialUsage materialUsage) {
        if (!materialUsageRepository.existsById(id)) {
            return "The material usage with that id does not exist";
        }
        MaterialUsage existing = materialUsageRepository.findById(id).get();
        materialUsage.setId(id);
        materialUsage.setRecordedDate(existing.getRecordedDate());
        materialUsage.setDailyReport(existing.getDailyReport());
        materialUsageRepository.save(materialUsage);
        return "Material usage updated successfully";
    }

    public String delete(UUID id) {
        if (!materialUsageRepository.existsById(id)) {
            return "The material usage with that id does not exist";
        }
        materialUsageRepository.deleteById(id);
        return "Material usage deleted successfully";
    }

    public List<MaterialUsage> getByProjectCode(String projectCode) {
        return materialUsageRepository.findByProjectCode(projectCode);
    }
}
