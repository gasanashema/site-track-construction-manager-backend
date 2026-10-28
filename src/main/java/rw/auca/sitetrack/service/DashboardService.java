package rw.auca.sitetrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.sitetrack.model.DailyReportStatus;
import rw.auca.sitetrack.repository.DailyReportRepository;
import rw.auca.sitetrack.repository.MaterialUsageRepository;
import rw.auca.sitetrack.repository.ProjectRepository;
import rw.auca.sitetrack.repository.SiteManagerRepository;
import rw.auca.sitetrack.repository.SiteRepository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SiteManagerRepository siteManagerRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private MaterialUsageRepository materialUsageRepository;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("projects", projectRepository.count());
        summary.put("siteManagers", siteManagerRepository.count());
        summary.put("freeSites", (long) siteRepository.findFreeSites().size());
        summary.put("reportsToday", dailyReportRepository.countByReportDate(LocalDate.now()));
        summary.put("draft", dailyReportRepository.countByStatus(DailyReportStatus.DRAFT));
        summary.put("materialUsages", materialUsageRepository.count());
        return summary;
    }
}
