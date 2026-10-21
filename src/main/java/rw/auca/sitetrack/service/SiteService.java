package rw.auca.sitetrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.sitetrack.model.Site;
import rw.auca.sitetrack.repository.SiteRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;

    public String save(Site site) {
        siteRepository.save(site);
        return "Site saved successfully";
    }

    public List<Site> getAll() {
        return siteRepository.findAll();
    }

    public Optional<Site> getById(UUID id) {
        return siteRepository.findById(id);
    }

    public String update(UUID id, Site site) {
        if (!siteRepository.existsById(id)) {
            return "The site with that id does not exist";
        }
        site.setId(id);
        siteRepository.save(site);
        return "Site updated successfully";
    }

    public String delete(UUID id) {
        if (!siteRepository.existsById(id)) {
            return "The site with that id does not exist";
        }
        siteRepository.deleteById(id);
        return "Site deleted successfully";
    }

    public List<Site> getFreeSites() {
        return siteRepository.findFreeSites();
    }
}
