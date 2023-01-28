package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import com.epam.cloudgantt.service.CsvTemplateService;
import com.epam.cloudgantt.service.ProjectService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class CsvTemplateControllerImpl implements CsvTemplateController {
    private final CsvTemplateService csvTemplateService;

    private final ProjectService projectService;

    public CsvTemplateControllerImpl(CsvTemplateService csvTemplateService, ProjectService projectService) {
        this.csvTemplateService = csvTemplateService;
        this.projectService = projectService;
    }

    @Override
    public void exportCSV(HttpServletResponse response) throws IOException {
        String filename = "GanttTemplate.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        csvTemplateService.exportCSV(response.getWriter());
    }

    @Override
    public ApiResult<ProjectResponseDTO> uploadCSV(MultipartFile file, User user) throws Exception {
        InputStream inputStream = file.getInputStream();
        return projectService.uploadCSV(inputStream, user);
    }
}

