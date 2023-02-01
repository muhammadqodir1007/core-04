package com.epam.cloudgantt.service;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

public interface CsvTemplateService {
    void exportCSV(Writer writer) throws IOException;
}
