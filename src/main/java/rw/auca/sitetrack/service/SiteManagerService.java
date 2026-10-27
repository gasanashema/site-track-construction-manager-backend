package rw.auca.sitetrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.sitetrack.model.Site;
import rw.auca.sitetrack.model.SiteManager;
import rw.auca.sitetrack.model.Trade;
import rw.auca.sitetrack.repository.SiteManagerRepository;
import rw.auca.sitetrack.repository.SiteRepository;
import rw.auca.sitetrack.repository.TradeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SiteManagerService {

    @Autowired
    private SiteManagerRepository siteManagerRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private SiteRepository siteRepository;

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

    public String addTrade(UUID siteManagerId, UUID tradeId) {
        Optional<SiteManager> sm = siteManagerRepository.findById(siteManagerId);
        if (sm.isEmpty()) {
            return "The site manager with that id does not exist";
        }
        Optional<Trade> trade = tradeRepository.findById(tradeId);
        if (trade.isEmpty()) {
            return "That trade does not exist";
        }
        SiteManager siteManager = sm.get();
        if (siteManager.getTrades() == null) {
            siteManager.setTrades(new ArrayList<>());
        }
        for (Trade t : siteManager.getTrades()) {
            if (t.getId().equals(tradeId)) {
                return "The site manager already has that trade";
            }
        }
        siteManager.getTrades().add(trade.get());
        siteManagerRepository.save(siteManager);
        return "Trade added to the site manager";
    }

    public String changeSite(UUID siteManagerId, int siteNumber) {
        Optional<SiteManager> sm = siteManagerRepository.findById(siteManagerId);
        if (sm.isEmpty()) {
            return "The site manager with that id does not exist";
        }
        Optional<Site> site = siteRepository.findFirstBySiteNumber(siteNumber);
        if (site.isEmpty()) {
            return "The site with that siteNumber does not exist";
        }
        SiteManager siteManager = sm.get();
        Site targetSite = site.get();
        if (siteManagerRepository.existsBySite(targetSite) && (siteManager.getSite() == null || !siteManager.getSite().getId().equals(targetSite.getId()))) {
            return "That site is taken by another site manager";
        }
        siteManager.setSite(targetSite);
        siteManagerRepository.save(siteManager);
        return "Site changed successfully";
    }
}
