package com.epam.cloudgantt.service;

import com.epam.cloudgantt.entity.Project;
import com.epam.cloudgantt.entity.Task1;
import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.exceptions.RestException;
import com.epam.cloudgantt.mapper.ProjectMapper;
import com.epam.cloudgantt.payload.*;
import com.epam.cloudgantt.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ApiResult<?> delete(UUID id, User user) {
        Project project = projectRepository.findById(id).orElseThrow(() -> RestException.restThrow("Project does not exist."));
        if (!project.getUser().equals(user)) throw RestException.restThrow("You are not allowed to rename.");
        projectRepository.deleteById(id);
        return ApiResult.successResponse("Project was successfully deleted.");
    }

    @Override
    public ApiResult<ProjectResponseDTO> createNewProject(CreateProjectDTO createProjectDTO, User user) {

        if (createProjectDTO == null) {
            throw RestException.restThrow("NAME_MUST_NOT_BE_NULL");
        }
        Project project = new Project();
        project.setName(createProjectDTO.getName());
        project.setUser(user);
        projectRepository.save(project);

        return ApiResult.successResponse("Project was successfully created");
    }

    @Override
    public ApiResult<ProjectResponseDTO> updateProjectName(UpdateProjectDTO updateProjectDTO, User user) {
        if (updateProjectDTO == null) throw RestException.restThrow("NAME_MUST_NOT_BE_NULL");
        Project project = projectRepository.findById(updateProjectDTO.getId()).orElseThrow(() -> RestException.restThrow("Project does not exist."));

        if (!project.getUser().equals(user)) throw RestException.restThrow("You are not allowed to rename.");

        project.setName(updateProjectDTO.getName());
        projectRepository.save(project);
        return ApiResult.successResponse("Project_Name was successfully edited.");
    }

    @Override
    public ApiResult<List<ProjectDTO>> myProjects(User user) {
        List<Project> projects = projectRepository.findAllByUser(user);
        return ApiResult.successResponse(projects.stream().map(this::mapProjectToProjectDTO).collect(Collectors.toList()));
    }

    @Override
    public ApiResult<ProjectDTO> myProjectById(UUID id, User user) throws ParseException {
        Project project = projectRepository.findById(id).orElseThrow(() -> RestException.restThrow("Project not found"));
        List<Task1> listOfTasks = project.getListOfTasks();
        ProjectDTO projectDTO = mapProjectToProjectDTO(project);
        List<Task1DTO> task1DTOList = new ArrayList<>();

        for (Task1 task : listOfTasks) {
            Task1DTO task1DTO = new Task1DTO();
            task1DTO.setProject_id(id);
            task1DTO.setAssignee(task.getAssignee());
            task1DTO.setDescription(task.getDescription());
            task1DTO.setTaskNumber(task.getTaskNumber());
            task1DTO.setBeginDate(task.getBeginDate());
            task1DTO.setEndDate(task.getEndDate());
            task1DTO.setId(task.getId());
            task1DTO.setSectionName(task.getSectionName());
            String endDate = task.getEndDate();
            int duration = 0;
            SimpleDateFormat obj = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            Date endDate1 = obj.parse(task.getEndDate());
            Date startDate1 = obj.parse(task.getBeginDate());
            if (task.getEndDate() != null && task.getBeginDate() != null) {
                long a = endDate1.getTime() - startDate1.getTime();
                duration = (int) ((a / (1000 * 60 * 60 * 24)) % 365);
            } else if (task.getEndDate() == null || task.getBeginDate() == null) {
                duration = 1;
            }
            task1DTO.setDuration(duration);
            task1DTOList.add(task1DTO);

        }


        //todo taskDTO
        projectDTO.setTasks(task1DTOList);
        return ApiResult.successResponse(projectDTO);
    }

    @Override
    public ApiResult<ProjectResponseDTO> uploadCSVFileToCreateProject(MultipartFile file, User user) {
        return null;
    }

    private ProjectDTO mapProjectToProjectDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getName());
    }
}
