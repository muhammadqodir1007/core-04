package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.service.CsvTemplateService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CsvTemplateControllerImpl implements CsvTemplateController {
    CsvTemplateService csvTemplateService;

    public CsvTemplateControllerImpl(CsvTemplateService csvTemplateService) {
        this.csvTemplateService = csvTemplateService;
    }

    @Override
    public void exportCSV(HttpServletResponse response) throws IOException {
        String filename = "GanttTemplate.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        csvTemplateService.exportCSV(response.getWriter());
    }
}

