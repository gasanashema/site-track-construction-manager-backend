package rw.auca.sitetrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.sitetrack.model.Project;
import rw.auca.sitetrack.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public String save(Project project) {
        projectRepository.save(project);
        return "Project saved successfully";
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public Optional<Project> getById(UUID id) {
        return projectRepository.findById(id);
    }

    public String update(UUID id, Project project) {
        if (!projectRepository.existsById(id)) {
            return "The project with that id does not exist";
        }
        project.setId(id);
        projectRepository.save(project);
        return "Project updated successfully";
    }

    public String delete(UUID id) {
        if (!projectRepository.existsById(id)) {
            return "The project with that id does not exist";
        }
        projectRepository.deleteById(id);
        return "Project deleted successfully";
    }
}
