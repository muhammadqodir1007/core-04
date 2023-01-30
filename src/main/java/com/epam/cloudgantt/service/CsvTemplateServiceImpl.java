package com.epam.cloudgantt.service;

import com.epam.cloudgantt.entity.CsvTemplate;
import com.epam.cloudgantt.repository.CsvTemplateRepository;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.Writer;
import java.util.List;

@Service
public class CsvTemplateServiceImpl implements CsvTemplateService {

    public CsvTemplateServiceImpl(CsvTemplateRepository csvTemplateRepository) {
        this.csvTemplateRepository = csvTemplateRepository;
    }

    CsvTemplateRepository csvTemplateRepository;

    @Override
    public void exportCSV(Writer writer) {
        System.out.println(csvTemplateRepository.findAll());
        List<CsvTemplate> all = csvTemplateRepository.findAll();
        List<String> list = all.stream().map(CsvTemplate::getNameOfColumn).toList();
        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.writeNext(list.toArray(new String[0]));
    }


}
