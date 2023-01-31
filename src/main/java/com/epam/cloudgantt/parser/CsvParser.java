package com.epam.cloudgantt.parser;

import com.epam.cloudgantt.entity.Task;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.epam.cloudgantt.util.CSVConstants.*;

public class CsvParser {
    public static List<Task> parseCsvFile(InputStream inputStream) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.Builder.create().setDelimiter(",")
                             .setSkipHeaderRecord(true)
                             .setTrim(true)
                             .setIgnoreEmptyLines(true)
                             .setQuote('"')
                             .setIgnoreHeaderCase(true)
                             .setHeader(
                                     TASK_NUMBER, SECTION_NAME, TASK_NAME, DESCRIPTION,
                                     BEGIN_DATE, END_DATE, ASSIGNEE).build())) {
            List<Task> listOfTasks = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Task task = new Task();
                task.setAssignee(csvRecord.get(ASSIGNEE));
                task.setDescription(csvRecord.get(DESCRIPTION));
                task.setTaskNumber(Long.valueOf(csvRecord.get(TASK_NUMBER)));
                task.setTaskName(csvRecord.get(TASK_NAME));
                task.setSectionName(csvRecord.get(SECTION_NAME));
                task.setBeginDate(csvRecord.get(BEGIN_DATE));
                task.setEndDate(csvRecord.get(END_DATE));
                listOfTasks.add(task);
            }
            return listOfTasks;
        }
    }
}

