package com.epam.cloudgantt.service;

import java.io.IOException;
import java.io.Writer;

public interface CsvTemplateService {
    void exportCSV(Writer writer) throws IOException;
}
