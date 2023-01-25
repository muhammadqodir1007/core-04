package com.epam.cloudgantt.service;

import com.epam.cloudgantt.entity.CsvTemplate;
import com.epam.cloudgantt.repository.CsvTemplateRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
public class CsvTemplateServiceImpl implements CsvTemplateService {

    public CsvTemplateServiceImpl(CsvTemplateRepository csvTemplateRepository) {
        this.csvTemplateRepository = csvTemplateRepository;
    }

    CsvTemplateRepository csvTemplateRepository;

    @Override
    public void exportCSV(Writer writer) throws IOException {
        System.out.println(csvTemplateRepository.findAll());
        List<CsvTemplate> all = csvTemplateRepository.findAll();
        List<String> list = all.stream().map(CsvTemplate::getNameOfColumn).toList();

        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
        printer.printRecord(String.join(" | ", list));


    }
}
