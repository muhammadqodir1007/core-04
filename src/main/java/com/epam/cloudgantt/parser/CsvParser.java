package com.epam.cloudgantt.parser;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.Task;
import com.epam.cloudgantt.exceptions.ErrorData;
import com.epam.cloudgantt.exceptions.RestException;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.epam.cloudgantt.util.CSVConstants.ASSIGNEE;
import static com.epam.cloudgantt.util.CSVConstants.BEGIN_DATE;
import static com.epam.cloudgantt.util.CSVConstants.DESCRIPTION;
import static com.epam.cloudgantt.util.CSVConstants.END_DATE;
import static com.epam.cloudgantt.util.CSVConstants.REQUIRED_HEADERS;
import static com.epam.cloudgantt.util.CSVConstants.SECTION_NAME;
import static com.epam.cloudgantt.util.CSVConstants.TASK_NAME;
import static com.epam.cloudgantt.util.CSVConstants.TASK_NUMBER;

@Setter
@Component
public class CsvParser
{

    private ErrorData errorData;

    public CsvParser(ErrorData errorData)
    {
        this.errorData = errorData;
    }

    public List<Task> parseCsvFile(InputStream inputStream)
        throws IOException
    {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)))
        {
            {
                StringBuilder stringBuilder = new StringBuilder();
                String headerLine = fileReader.readLine();

                String[] headers = headerLine.split(",");

                String line;
                while ((line = fileReader.readLine()) != null)
                {
                    stringBuilder.append(line).append("\n");
                }
                Set<String> inputHeaders = Set.of(headers);
                if (!inputHeaders.containsAll(REQUIRED_HEADERS))
                {
                    throw RestException.restThrow(MessageByLang.getMessage("CSV_REQUIRED_HEADERS_MISSING"));
                }
                if (inputHeaders.size() > 7)
                {
                    errorData.getAlertMessages().add(MessageByLang.getMessage("CSV_ADDITIONAL_COLUMNS_IGNORED"));
                }

                boolean blank = stringBuilder.toString().replaceAll(",", "").isBlank();//check

                if (blank)
                {
                    return new ArrayList<>();
                }

                BufferedReader bufferedReader = new BufferedReader(new StringReader(headerLine + "\n" + stringBuilder));

                List<Task> listOfTasks = new ArrayList<>();

                try (CSVParser csvParser = new CSVParser(
                    bufferedReader,
                    CSVFormat.Builder.create().setDelimiter(",")
                        .setSkipHeaderRecord(true)
                        .setTrim(true)
                        .setIgnoreEmptyLines(true)
                        .setQuote('"')
                        .setIgnoreHeaderCase(true)
                        .setHeader(
                            TASK_NUMBER, SECTION_NAME, TASK_NAME, DESCRIPTION,
                            BEGIN_DATE, END_DATE, ASSIGNEE
                        ).build()
                )
                )
                {
                    Iterable<CSVRecord> csvRecords = csvParser.getRecords();
                    for (CSVRecord csvRecord : csvRecords)
                    {
                        Task task = new Task();
                        task.setAssignee(csvRecord.get(ASSIGNEE).contains(",") ? "" : csvRecord.get(ASSIGNEE));
                        task.setDescription(csvRecord.get(DESCRIPTION).contains(",") ? "" : csvRecord.get(DESCRIPTION));
                        try
                        {
                            task.setTaskNumber(Long.valueOf(csvRecord.get(TASK_NUMBER).contains(",")
                                ? ""
                                : csvRecord.get(TASK_NUMBER)));
                        }
                        catch (Exception e)
                        {
                            throw RestException.restThrow(MessageByLang.getMessage(
                                "CSV_TASK_NUMBER_OR_TASK_NAME_MISSING"));
                        }

                        String taskName = csvRecord.get(TASK_NAME);

                        if (taskName.isBlank())
                        {
                            throw RestException.restThrow(MessageByLang.getMessage(
                                "CSV_TASK_NUMBER_OR_TASK_NAME_MISSING"));
                        }

                        task.setTaskName(taskName.contains(",") ? "" : taskName);

                        task.setSectionName(csvRecord.get(SECTION_NAME).contains(",")
                            ? ""
                            : csvRecord.get(SECTION_NAME));

                        task.setBeginDate(parseDate(csvRecord.get(BEGIN_DATE)));
                        task.setEndDate(parseDate(csvRecord.get(END_DATE)));

                        listOfTasks.add(task);
                    }
                }
                return listOfTasks;
            }
        }
    }

    private LocalDateTime parseDate(String date)
    {
        if (date.isEmpty())
        {
            return null;
        } else
        {
            try
            {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate localDate = LocalDate.parse(date, formatter);
                return localDate.atStartOfDay();
            }
            catch (Exception e1)
            {
                try
                {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
                    LocalDate localDate = LocalDate.parse(date, formatter);
                    return localDate.atStartOfDay();
                }
                catch (Exception e2)
                {
                    try
                    {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                        LocalDate localDate = LocalDate.parse(date, formatter);
                        return localDate.atStartOfDay();
                    }
                    catch (Exception e3)
                    {
                        try
                        {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
                            LocalDate localDate = LocalDate.parse(date, formatter);
                            return localDate.atStartOfDay();
                        }
                        catch (Exception e4)
                        {
                            throw RestException.restThrow(MessageByLang.getMessage("CSV_WRONG_DATE_FORMAT"));
                        }
                    }
                }
            }
        }
    }
}