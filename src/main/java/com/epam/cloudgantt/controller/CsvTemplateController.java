package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.util.AppConstants;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RequestMapping(AppConstants.BASE_PATH)
public interface CsvTemplateController {

    @GetMapping("/export-template")
    void exportCSV(HttpServletResponse response) throws IOException;

}
