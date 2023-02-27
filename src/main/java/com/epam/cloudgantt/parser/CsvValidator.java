package com.epam.cloudgantt.parser;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.Task;
import com.epam.cloudgantt.exceptions.AlertData;
import com.epam.cloudgantt.exceptions.ErrorData;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Setter
public class CsvValidator {

    private AlertData alertData;
    @Getter
    private ErrorData errorData;

    public CsvValidator(AlertData alertData, ErrorData errorData) {
        this.alertData = alertData;
        this.errorData = errorData;
    }

    public List<Task> validateAll(List<Task> tasks) {
        List<Task> first50tasks = getFirst50Tasks(tasks);
        for (Task task : first50tasks) {
            if (Objects.nonNull(task.getTaskNumber())) {
                isDependencyValid(task, tasks);
                isBeginDateBeforeEndDate(task);
            }
            cutTextToMaxLength(task);
        }
        checkSectionNames(tasks);
        checkUniqueID(tasks);
        List<String> errorMessages = InvalidTaskHelper.getAllMessages();
        for (String message : errorMessages) {
            errorData.addError(message);
        }

        if (!errorData.getErrorMessages().isEmpty()) {
            return new ArrayList<>();
        }

        tasks.sort(Comparator.comparing(Task::getTaskNumber));

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
        try {
            if (tasks.stream()
                    .allMatch(task -> task.getAssignee().isEmpty())) {
                emptyColumns.add("Assignee");
            }
        } catch (Exception e) {
            emptyColumns.add("Assignee");
        }
        if (!emptyColumns.isEmpty()) {
            String missingColumnsMessage = "Missing columns are : " + String.join(",", emptyColumns);
            alertData.getAlertMessages().add(missingColumnsMessage);
        }

        return tasks;
    }

    private List<Task> getFirst50Tasks(List<Task> tasks) {
        int size = tasks.size();
        if (size > 50) {
            alertData.getAlertMessages().add("The limit of 50 tasks was exceeded. Only 50 first tasks were uploaded");
            return tasks.subList(0, 49);
        }
        return tasks;
    }


    private void isDependencyValid(Task task, List<Task> tasks) {
        if (!task.getDependency().isBlank()) {
            String dependency = task.getDependency();
            String[] split = dependency.split(",");
            Set<Long> dependencies = new HashSet<>();
            for (String dep : split) {//1//6,#,%,1
                if (NumberUtils.isParsable(dep)) {
                    dependencies.add(Long.parseLong(dep));
                } else {
                    InvalidTaskHelper.addNotNumberDependency(task.getTaskNumber());
                    dependency = null;
                }
            }
            if (isCircularDependency(task.getTaskNumber(), dependencies)) {
                InvalidTaskHelper.addCircularDependency(task.getTaskNumber());
                dependency = null;
            }
            if (dependencyDoesNotExist(dependencies, tasks)) {
                InvalidTaskHelper.addNonExistentDependency(task.getTaskNumber());
                dependency = null;
            }
            task.setDependency(dependency);
        }
    }

    private boolean dependencyDoesNotExist(Set<Long> dependencies, List<Task> tasks) {
        List<Long> taskNumbers = tasks.stream()
                .map(Task::getTaskNumber)
                .toList();

        for (Long dependency : dependencies) {
            if (!taskNumbers.contains(dependency)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCircularDependency(Long taskNumber, Set<Long> dependencies) {
        return dependencies.stream()
                .anyMatch(dependency -> dependency.equals(taskNumber));
    }

    public void isBeginDateBeforeEndDate(Task task) {
        LocalDateTime begin = task.getBeginDate();
        LocalDateTime end = task.getEndDate();
        if (Objects.equals(begin, end)) {
            return;
        }
        if (Objects.nonNull(begin) && Objects.nonNull(end) && !begin.isBefore(end)) {
            InvalidTaskHelper.addBeginDateNotPrecedeEndDateTask(task.getTaskNumber());
        }
    }

    public void cutTextToMaxLength(Task task) {
        String s = "The text field was cut to 255 symbols as it exceeded its maximum length";
        String taskName = task.getTaskName();
        String assignee = "";
        try {
            assignee = task.getAssignee();
        } catch (Exception ignored) {
        }
        String description = task.getDescription();
        String sectionName = task.getSectionName();
        if (taskName.length() > 255) {
            task.setTaskName(taskName.substring(0, 255));
            checkIfExists(s);
        }
        try {
            if (assignee.length() > 255) {
                task.setAssignee(assignee.substring(0, 255));
                checkIfExists(s);
            }
        } catch (Exception ignored) {
        }
        if (description.length() > 255) {
            task.setDescription(description.substring(0, 255));
            checkIfExists(s);
        }
        if (sectionName.length() > 255) {
            task.setSectionName(taskName.substring(0, 255));
            checkIfExists(s);
        }
    }

    private void checkIfExists(String s) {
        if (!alertData.getAlertMessages().contains(s)) {
            alertData.getAlertMessages().add(s);
        }
    }

    public void checkUniqueID(List<Task> tasks) {
        Set<Long> idsOfTasks = tasks.stream().map(Task::getTaskNumber)
                .collect(Collectors.toSet());

        if (idsOfTasks.size() != tasks.size()) {
            errorData.addError(MessageByLang.getMessage("CSV_NOT_UNIQUE_ID"));
        }
    }

    public void checkSectionNames(List<Task> tasks) {
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
            errorData.addError(MessageByLang.getMessage("CSV_NOT_ALL_SECTIONS_FILLED"));
        }
    }
}
