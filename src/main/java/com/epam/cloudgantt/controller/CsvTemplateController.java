package com.epam.cloudgantt.controller;

import com.epam.cloudgantt.entity.User;
import com.epam.cloudgantt.payload.ApiResult;
import com.epam.cloudgantt.payload.ProjectResponseDTO;
import com.epam.cloudgantt.security.CurrentUser;
import com.epam.cloudgantt.util.AppConstants;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping(AppConstants.BASE_PATH)
public interface CsvTemplateController {


    String UPLOAD_PATH ="upload";

    String EXPORT_TEMPLATE_PATH ="export-template";

    @GetMapping(value = EXPORT_TEMPLATE_PATH)
    void exportCSV(HttpServletResponse response) throws IOException;

    @PostMapping(value = UPLOAD_PATH)
    ApiResult<ProjectResponseDTO> uploadCSV(@RequestParam("file") MultipartFile file, @CurrentUser User user) throws Exception;
}
