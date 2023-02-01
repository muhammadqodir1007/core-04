package com.epam.cloudgantt.parser;

import com.epam.cloudgantt.entity.Task;
import com.epam.cloudgantt.exceptions.ErrorData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epam.cloudgantt.util.CSVConstants.ASSIGNEE;
import static com.epam.cloudgantt.util.CSVConstants.BEGIN_DATE;
import static com.epam.cloudgantt.util.CSVConstants.DESCRIPTION;
import static com.epam.cloudgantt.util.CSVConstants.END_DATE;
import static com.epam.cloudgantt.util.CSVConstants.REQUIRED_HEADERS;
import static com.epam.cloudgantt.util.CSVConstants.SECTION_NAME;
import static com.epam.cloudgantt.util.CSVConstants.TASK_NAME;
import static com.epam.cloudgantt.util.CSVConstants.TASK_NUMBER;

public class CsvParser {

    private final ErrorData errorData;

    public CsvParser(ErrorData errorData) {
        this.errorData = errorData;
    }


    public List<Task> parseCsvFile(InputStream inputStream) throws IOException {
        List<Task> listOfTasks = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            {
                StringBuilder stringBuilder = new StringBuilder();
                String headerLine = fileReader.readLine();
                if (headerLine.isBlank()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No headers in csv");
                }
                String[] headers = headerLine.split("\\W");
                String line;
                while ((line = fileReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                List<String> inputHeaders = Arrays.asList(headers);
                boolean blank = stringBuilder.isEmpty();

                if (inputHeaders.containsAll(REQUIRED_HEADERS)) {
                    if (inputHeaders.size() > 7) {
                        errorData.getErrorMessages().add("Please be informed that non-default columns weren't uploaded");
                    }
                    if (blank) {
                        System.out.println("empty csv");
                        return new ArrayList<>();
                    }
                } else {
                    errorData.getErrorMessages().add(
                            "The project can't be successfully uploaded, you are missing important headers..");
                             return null;
                }
                BufferedReader bufferedReader = new BufferedReader(new StringReader(headerLine + "\n" + stringBuilder));
                try (CSVParser csvParser = new CSVParser(bufferedReader,
                        CSVFormat.Builder.create().setDelimiter(",")
                                .setSkipHeaderRecord(true)
                                .setTrim(true)
                                .setIgnoreEmptyLines(true)
                                .setQuote('"')
                                .setIgnoreHeaderCase(true)
                                .setHeader(
                                        TASK_NUMBER, SECTION_NAME, TASK_NAME, DESCRIPTION,
                                        BEGIN_DATE, END_DATE, ASSIGNEE).build())) {
                    Iterable<CSVRecord> csvRecords = csvParser.getRecords();
                    for (CSVRecord csvRecord : csvRecords) {
                        Task task = new Task();
                        task.setAssignee(csvRecord.get(ASSIGNEE).contains(",") ? "" : csvRecord.get(ASSIGNEE));
                        task.setDescription(csvRecord.get(DESCRIPTION).contains(",") ? "" : csvRecord.get(DESCRIPTION));
                        try {
                            task.setTaskNumber(Long.valueOf(csvRecord.get(TASK_NUMBER).contains(",") ? "" : csvRecord.get(TASK_NUMBER)));
                        } catch (Exception e) {
                            throw new RuntimeException("Please fill in task_number and/or task_name fields");
                        }
                        task.setTaskName(csvRecord.get(TASK_NAME).contains(",") ? "" : csvRecord.get(TASK_NAME));
                        task.setSectionName(csvRecord.get(SECTION_NAME).contains(",") ? "" : csvRecord.get(SECTION_NAME));
                        task.setBeginDate(csvRecord.get(BEGIN_DATE).contains(",") ? "" : csvRecord.get(BEGIN_DATE));
                        task.setEndDate(csvRecord.get(END_DATE).contains(",") ? "" : csvRecord.get(END_DATE));
                        listOfTasks.add(task);
                    }
                }
            }
        }

        return listOfTasks;
    }
}

