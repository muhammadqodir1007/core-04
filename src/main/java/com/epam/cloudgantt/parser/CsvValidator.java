package com.epam.cloudgantt.parser;

import com.epam.cloudgantt.config.MessageByLang;
import com.epam.cloudgantt.entity.Task;
import com.epam.cloudgantt.exceptions.ErrorData;
import com.epam.cloudgantt.exceptions.RestException;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Setter
public class CsvValidator
{

    private ErrorData errorData;

    public CsvValidator(ErrorData errorData)
    {
        this.errorData = errorData;
    }

    public List<Task> validateAll(List<Task> tasks)
    {
        tasks.forEach(task -> {
            isBeginDateBeforeEndDate(task, task.getBeginDate(), task.getEndDate());
            cutTextToMaxLength(task);
            isDependencyValid(task);
        });
        checkSectionNames(tasks);
        isDependencyExists(sortAndGet50(tasks));

        List<String> emptyColumns = new ArrayList<>();

        if (tasks.stream()
            .allMatch(task -> Objects.isNull(task.getBeginDate())))
        {
            emptyColumns.add("Begin Date");
        }
        if (tasks.stream()
            .allMatch(task -> Objects.isNull(task.getEndDate())))
        {
            emptyColumns.add("End Date");
        }
        if (tasks.stream()
            .allMatch(task -> task.getSectionName().isEmpty()))
        {
            emptyColumns.add("Section Name");
        }
        if (tasks.stream()
            .allMatch(task -> task.getDescription().isEmpty()))
        {
            emptyColumns.add("Description");
        }
        if (tasks.stream()
            .allMatch(task -> task.getAssignee().isEmpty()))
        {
            emptyColumns.add("Assignee");
        }
        if (!emptyColumns.isEmpty())
        {
            String missingColumnsMessage = "Missing columns are : " + String.join(",", emptyColumns);
            errorData.getAlertMessages().add(missingColumnsMessage);
        }
        return tasks;
    }

    private void isDependencyExists(List<Task> tasks)
    {
        Set<Integer> dependencies = new HashSet<>();
        List<Integer> incorrectDeps = new ArrayList<>();
        Set<Long> taskNumbers = tasks.stream().map(Task::getTaskNumber).collect(Collectors.toSet());
        for (Task task : tasks) {
            if (!task.getDependency().isBlank()) {
                dependencies = Arrays.stream(task.getDependency().split(","))
                        .mapToInt(Integer::parseInt)
                        .boxed()
                        .collect(Collectors.toSet());
            }

            for (Integer dependency : dependencies) {
                if (!taskNumbers.contains(Long.valueOf(dependency))) {
                    incorrectDeps.add(dependency);
                }
            }
            if (!incorrectDeps.isEmpty()) {
                throw RestException.restThrow(String.format(
                        MessageByLang.getMessage("CSV_DEPENDENCY_NOT_EXIST"),
                        StringUtils.collectionToDelimitedString(incorrectDeps, ",")
                ));
            }
        }
    }

    private void isDependencyValid(Task task)
    {
        Set<Integer> dependencies;
        if (!task.getDependency().isBlank())
        {
            try
            {
                dependencies = Arrays.stream(task.getDependency().split(","))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toSet());
            }
            catch (Exception e)
            {
                throw RestException.restThrow(String.format(MessageByLang.getMessage("CSV_DEPENDS_ON_NOT_NUMBER"),
                    task.getTaskNumber()));
            }
            task.setDependency(StringUtils.collectionToDelimitedString(dependencies, ","));
            for (Integer dependency : dependencies)
            {
                if (dependency == task.getTaskNumber().intValue())
                {
                    throw RestException.restThrow("Task " + dependency + " has dependency on itself");
                }
            }
        }
    }

    public void isBeginDateBeforeEndDate(Task task, LocalDateTime begin, LocalDateTime end)
    {
        if (Objects.equals(begin, end))
        {
            return;
        }
        if (Objects.nonNull(begin) && Objects.nonNull(end) && !begin.isBefore(end))
        {
            throw RestException.restThrow(String.format(MessageByLang.getMessage(
                "CSV_BEGIN_DATE_DOES_NOT_PRECEDE_END_DATE"), task.getTaskNumber()));
        }
    }

    public void cutTextToMaxLength(Task task)
    {
        String s = "The text field was cut to 255 symbols as it exceeded its maximum length";
        String taskName = task.getTaskName();
        String assignee = task.getAssignee();
        String description = task.getDescription();
        String sectionName = task.getSectionName();
        if (taskName.length() > 255)
        {
            task.setTaskName(taskName.substring(0, 255));
            checkIfExists(s);
        }
        if (assignee.length() > 255)
        {
            task.setTaskName(taskName.substring(0, 255));
            checkIfExists(s);
        }
        if (description.length() > 255)
        {
            task.setTaskName(taskName.substring(0, 255));
            checkIfExists(s);
        }
        if (sectionName.length() > 255)
        {
            task.setTaskName(taskName.substring(0, 255));
            checkIfExists(s);
        }
    }

    private void checkIfExists(String s)
    {
        if (!errorData.getAlertMessages().contains(s))
        {
            errorData.getAlertMessages().add(s);
        }
    }

    public List<Task> sortAndGet50(List<Task> tasks)
    {
        HashSet<Long> idsOfTasks = new HashSet<>();
        tasks.forEach(task -> idsOfTasks.add(task.getTaskNumber()));
        if (idsOfTasks.size() != tasks.size())
        {
            throw RestException.restThrow(MessageByLang.getMessage("CSV_NOT_UNIQUE_ID"));
        }
        if (tasks.size() > 50)
        {
            errorData.getAlertMessages().add("The limit of 50 tasks was exceeded. Only 50 first tasks were uploaded");
        }
        tasks.sort(Comparator.comparing(Task::getTaskNumber));
        return tasks.stream().limit(Math.min(tasks.size(), 50)).toList();
    }

    @SneakyThrows
    public static void checkSectionNames(List<Task> tasks)
    {
        var blankTextCount = 0;
        var textCount = 0;
        for (Task task : tasks)
        {
            if (task.getSectionName().isEmpty())
            {
                blankTextCount++;
            } else
            {
                textCount++;
            }
        }
        if (blankTextCount > 0 && textCount > 0)
        {
            throw RestException.restThrow(MessageByLang.getMessage("CSV_NOT_ALL_SECTIONS_FILLED"));
        }
    }
}
