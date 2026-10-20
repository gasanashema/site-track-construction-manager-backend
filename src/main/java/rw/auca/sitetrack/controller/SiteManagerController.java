package rw.auca.sitetrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rw.auca.sitetrack.model.SiteManager;
import rw.auca.sitetrack.service.SiteManagerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/site-managers")
public class SiteManagerController {

    @Autowired
    private SiteManagerService siteManagerService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody SiteManager siteManager) {
        String message = siteManagerService.save(siteManager);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SiteManager>> getAll() {
        return new ResponseEntity<>(siteManagerService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/by-trade")
    public ResponseEntity<List<SiteManager>> getByTrade(@RequestParam String name) {
        return new ResponseEntity<>(siteManagerService.getByTrade(name), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<SiteManager> optional = siteManagerService.getById(id);
        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The site manager with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody SiteManager siteManager) {
        String message = siteManagerService.update(id, siteManager);
        if (message.equals("The site manager with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = siteManagerService.delete(id);
        if (message.equals("The site manager with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @PutMapping("/{siteManagerId}/trades/{tradeId}")
    public ResponseEntity<String> addTrade(@PathVariable UUID siteManagerId, @PathVariable UUID tradeId) {
        String message = siteManagerService.addTrade(siteManagerId, tradeId);
        if (message.equals("The site manager with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.equals("That trade does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.equals("The site manager already has that trade")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
