package com.epam.cloudgantt.parser;

import com.epam.cloudgantt.entity.Task;
import com.epam.cloudgantt.exceptions.ErrorData;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.epam.cloudgantt.util.CSVConstants.*;

public class CsvParser {

    private final ErrorData errorData;

    public CsvParser(ErrorData errorData) {
        this.errorData = errorData;
    }


    public List<Task> parseCsvFile(InputStream inputStream) throws IOException {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            {
                StringBuilder stringBuilder = new StringBuilder();
                String headerLine = fileReader.readLine();
                if (headerLine.replaceAll(",", "").isBlank()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No headers in csv");

                }
                String[] headers = headerLine.split(",");
                String line;
                while ((line = fileReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                List<String> inputHeaders = Arrays.asList(headers);
                boolean blank = stringBuilder.toString().replaceAll(",", "").isBlank();
                if (inputHeaders.containsAll(List.of(TASK_NUMBER, TASK_NAME))) {
                    if (inputHeaders.containsAll(REQUIRED_HEADERS)) {
                        if (inputHeaders.size() > 7) {
                            errorData.getErrorMessages().add("Please be informed that non-default columns weren't uploaded");
                        }
                        if (blank) {
                            System.out.println("empty csv");
                            return new ArrayList<>();
                        }
                    } else {

                        if (blank) {
                            throw new RuntimeException("Please fill in task_number and/or task_name fields");
                        } else {
                            List<String> missingHeaders = new ArrayList<>(REQUIRED_HEADERS);
                            missingHeaders.removeAll(REQUIRED_HEADERS);
                            errorData.getErrorMessages().add(
                                    "The project has been successfully uploaded, missing headers are: "
                                            + missingHeaders);
                        }
                    }
                }
                BufferedReader bufferedReader = new BufferedReader(new StringReader(headerLine + "\n" + stringBuilder));
                CSVParser csvParser = new CSVParser(bufferedReader,
                        CSVFormat.Builder.create().setDelimiter(",")
                                .setSkipHeaderRecord(true)
                                .setTrim(true)
                                .setIgnoreEmptyLines(true)
                                .setQuote('"')
                                .setIgnoreHeaderCase(true)
                                .setHeader(
                                        TASK_NUMBER, SECTION_NAME, TASK_NAME, DESCRIPTION,
                                        BEGIN_DATE, END_DATE, ASSIGNEE).build());
                List<Task> listOfTasks = new ArrayList<>();
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
                return listOfTasks;
            }
        }
    }
}

