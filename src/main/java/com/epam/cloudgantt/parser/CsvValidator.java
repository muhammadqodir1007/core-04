package com.epam.cloudgantt.parser;

import com.epam.cloudgantt.entity.Task;
import com.epam.cloudgantt.exceptions.ErrorData;
import lombok.SneakyThrows;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CsvValidator {
    private final ErrorData errorData;

    public CsvValidator(ErrorData errorData) {
        this.errorData = errorData;
    }

    public List<Task> validateAll(List<Task> tasks) {
        //todo check
//        tasks.forEach(task -> {
//            isRequiredFormatDate((task.getBeginDate()));
//            isRequiredFormatDate((task.getEndDate()));
//            isBeginDateBeforeEndDate(task.getBeginDate(), task.getEndDate());
//            cutTextToMaxLength(task);
//
//        });
        checkSectionNames(tasks);
        tasks = sortAndGet50(tasks);
        return tasks;
    }

    public static void isRequiredFormatDate(String date) {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        if (!date.replaceAll(",", "").isBlank()) {
            try {
                sdf.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException("Incorrect date format");
            }
        }
    }

    public void isBeginDateBeforeEndDate(String begin, String end) {
        if (!begin.isBlank() && !end.isBlank())
            try {
                final DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
                final Date beginDate = dtf.parse(begin);
                final Date endDate = dtf.parse(end);
                if (!beginDate.before(endDate)) {
                    throw new RuntimeException("End date > begin date");
                }
            } catch (ParseException p) {
                throw new RuntimeException("failed to parse date");
            }
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
            System.out.println("idn nuynna");
            throw new RuntimeException("id is not unique");
        }
        if (tasks.size() > 50)
            errorData.getErrorMessages().add("The limit of 50 tasks was exceeded. Only 50 first tasks were uploaded");
        tasks.sort(Comparator.comparing(Task::getTaskNumber));
        return tasks.stream().limit(Math.min(tasks.size(), 50)).collect(Collectors.toList());
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
            throw new RuntimeException("Please fill in section_name field for each task");
        }
    }
}
