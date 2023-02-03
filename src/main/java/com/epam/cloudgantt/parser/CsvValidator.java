package com.epam.cloudgantt.parser;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.Task;
import com.epam.cloudgantt.exceptions.ErrorData;
import com.epam.cloudgantt.exceptions.RestException;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Component
@Setter
public class CsvValidator {
    private ErrorData errorData;

    public CsvValidator(ErrorData errorData) {
        this.errorData = errorData;
    }

    public List<Task> validateAll(List<Task> tasks) {
        tasks.forEach(task -> {
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
            errorData.getAlertMessages().add(missingColumnsMessage);
        }
        return tasks;
    }


    public void isBeginDateBeforeEndDate(Task task, LocalDateTime begin, LocalDateTime end) {
        if (Objects.equals(begin, end))
            return;
        if (Objects.nonNull(begin) && Objects.nonNull(end) && !begin.isBefore(end)) {
            throw RestException.restThrow(String.format(MessageByLang.getMessage("CSV_BEGIN_DATE_DOES_NOT_PRECEDE_END_DATE"), task.getTaskNumber()));
        }
    }

    public void cutTextToMaxLength(Task task) {
        String s = "The text field was cut to 255 symbols as it exceeded its maximum length";
        String taskName = task.getTaskName();
        String assignee = task.getAssignee();
        String description = task.getDescription();
        String sectionName = task.getSectionName();
        if (taskName.length() > 255) {
            task.setTaskName(taskName.substring(0, 255));
            checkIfExists(s);
        }
        if (assignee.length() > 255) {
            task.setTaskName(taskName.substring(0, 255));
            checkIfExists(s);
        }
        if (description.length() > 255) {
            task.setTaskName(taskName.substring(0, 255));
            checkIfExists(s);
        }
        if (sectionName.length() > 255) {
            task.setTaskName(taskName.substring(0, 255));
            checkIfExists(s);
        }

    }

    private void checkIfExists(String s) {
        if (!errorData.getAlertMessages().contains(s)) {
            errorData.getAlertMessages().add(s);
        }
    }

    public List<Task> sortAndGet50(List<Task> tasks) {
        HashSet<Long> idsOfTasks = new HashSet<>();
        tasks.forEach(task -> idsOfTasks.add(task.getTaskNumber()));
        if (idsOfTasks.size() != tasks.size()) {
            throw RestException.restThrow(MessageByLang.getMessage("CSV_NOT_UNIQUE_ID"));
        }
        if (tasks.size() > 50)
            errorData.getAlertMessages().add("The limit of 50 tasks was exceeded. Only 50 first tasks were uploaded");
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
