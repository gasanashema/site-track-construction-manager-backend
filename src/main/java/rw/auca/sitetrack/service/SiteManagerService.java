package rw.auca.sitetrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.sitetrack.model.SiteManager;
import rw.auca.sitetrack.repository.SiteManagerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SiteManagerService {

    @Autowired
    private SiteManagerRepository siteManagerRepository;

    public String save(SiteManager siteManager) {
        siteManagerRepository.save(siteManager);
        return "Site manager saved successfully";
    }

    public List<SiteManager> getAll() {
        return siteManagerRepository.findAll();
    }

    public Optional<SiteManager> getById(UUID id) {
        return siteManagerRepository.findById(id);
    }

    public String update(UUID id, SiteManager siteManager) {
        if (!siteManagerRepository.existsById(id)) {
            return "The site manager with that id does not exist";
        }
        siteManager.setId(id);
        siteManagerRepository.save(siteManager);
        return "Site manager updated successfully";
    }

    public String delete(UUID id) {
        if (!siteManagerRepository.existsById(id)) {
            return "The site manager with that id does not exist";
        }
        siteManagerRepository.deleteById(id);
        return "Site manager deleted successfully";
    }

    public List<SiteManager> getByTrade(String name) {
        return siteManagerRepository.findByTradeName(name);
    }
}
