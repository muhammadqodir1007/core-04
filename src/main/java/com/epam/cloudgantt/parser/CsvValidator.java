package com.epam.cloudgantt.parser;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.Task;
import com.epam.cloudgantt.exceptions.ErrorData;
import com.epam.cloudgantt.exceptions.RestException;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class CsvValidator {
    private final ErrorData errorData;

    public CsvValidator(ErrorData errorData) {
        this.errorData = errorData;
    }

    public List<Task> validateAll(List<Task> tasks) {
        //todo check
        tasks.forEach(task -> {
           /* isRequiredFormatDate((task.getBeginDate()));
            isRequiredFormatDate((task.getEndDate()));*/
            isBeginDateBeforeEndDate(task, task.getBeginDate(), task.getEndDate());
            cutTextToMaxLength(task);
        });
        checkSectionNames(tasks);
        tasks = sortAndGet50(tasks);

        List<String> emptyColumns = new ArrayList<>();

        if (tasks.stream()
                .allMatch(task -> Objects.isNull(task.getBeginDate()))) {
            emptyColumns.add("Begin Date");
        }

        if (tasks.stream()
                .allMatch(task -> Objects.isNull(task.getEndDate()))) {
            emptyColumns.add("End Date");
        }

        if (tasks.stream()
                .allMatch(task -> task.getSectionName().isEmpty())) {
            emptyColumns.add("Section Name");
        }

        if (tasks.stream()
                .allMatch(task -> task.getDescription().isEmpty())) {
            emptyColumns.add("Description");
        }
        if (tasks.stream()
                .allMatch(task -> task.getAssignee().isEmpty())) {
            emptyColumns.add("Assignee");
        }
        if (!emptyColumns.isEmpty()) {
            String missingColumnsMessage = "Missing columns are : " + String.join(",", emptyColumns);
            errorData.getErrorMessages().add(missingColumnsMessage);
        }
        return tasks;
    }


   /* public static void isRequiredFormatDate(String date) {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        if (!date.replaceAll(",", "").isBlank()) {
            try {
                sdf.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException("Incorrect date format");
            }
        }
    }*/

    public void isBeginDateBeforeEndDate(Task task, LocalDateTime begin, LocalDateTime end) {
        if(Objects.equals(begin,end))
            return;
        if (Objects.nonNull(begin) && Objects.nonNull(end) && !begin.isBefore(end)) {
            throw RestException.restThrow(String.format(MessageByLang.getMessage("CSV_BEGIN_DATE_DOES_NOT_PRECEDE_END_DATE"), task.getTaskNumber()));
        }
      /*  if (!begin.isBlank() && !end.isBlank())
            try {
                final DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
                final Date beginDate = dtf.parse(begin);
                final Date endDate = dtf.parse(end);
                if (!beginDate.before(endDate)) {
                    throw new RuntimeException("End date > begin date");
                }
            } catch (ParseException p) {
                throw new RuntimeException("failed to parse date");
            }*/
    }

    public void cutTextToMaxLength(Task task) {
        String taskName = task.getTaskName();
        String assignee = task.getAssignee();
        String description = task.getDescription();
        String sectionName = task.getSectionName();
        if (taskName.length() > 255) {
            errorData.getErrorMessages().add("The task name field cut to 255 symbols as it exceeded its maximum length");
            task.setTaskName(taskName.substring(0, 255));
        }
        if (assignee.length() > 255) {
            errorData.getErrorMessages().add("The assignee field was cut to 255 symbols as it exceeded its maximum length");
            task.setTaskName(taskName.substring(0, 255));
        }
        if (description.length() > 255) {
            errorData.getErrorMessages().add("The description field was cut to 255 symbols as it exceeded its maximum length");
            task.setTaskName(taskName.substring(0, 255));
        }
        if (sectionName.length() > 255) {
            errorData.getErrorMessages().add("The section field was cut to 255 symbols as it exceeded its maximum length");
            task.setTaskName(taskName.substring(0, 255));
        }
    }

    public List<Task> sortAndGet50(List<Task> tasks) {
        HashSet<Long> idsOfTasks = new HashSet<>();
        tasks.forEach(task -> idsOfTasks.add(task.getTaskNumber()));
        if (idsOfTasks.size() != tasks.size()) {
            throw RestException.restThrow(MessageByLang.getMessage("CSV_NOT_UNIQUE_ID"));
        }
        if (tasks.size() > 50)
            errorData.getErrorMessages().add("The limit of 50 tasks was exceeded. Only 50 first tasks were uploaded");
        tasks.sort(Comparator.comparing(Task::getTaskNumber));
        return tasks.stream().limit(Math.min(tasks.size(), 50)).toList();
    }

    @SneakyThrows
    public static void checkSectionNames(List<Task> tasks) {
        var blankTextCount = 0;
        var textCount = 0;
        for (Task task : tasks) {
            if (task.getSectionName().isEmpty()) {
                blankTextCount++;
            } else {
                textCount++;
            }
        }
        if (blankTextCount > 0 && textCount > 0) {
            throw RestException.restThrow(MessageByLang.getMessage("CSV_NOT_ALL_SECTIONS_FILLED"));
        }
    }
}
