package com.epam.cloudgantt.parser;

import com.epam.cloudgantt.entity.Task;
import lombok.SneakyThrows;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CsvValidator {
    public static boolean isRequiredFormatDate(String date) {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            System.out.println("date format is wrong");
            return false;
        }
        return true;
    }

    public static boolean isBeginDateBeforeEndDate(String begin, String end) {
        try {
            final DateFormat dtf = new SimpleDateFormat("yyyy-MMM-dd");
            final Date beginDate = dtf.parse(begin);
            final Date endDate = dtf.parse(end);
            return beginDate.before(endDate);
        } catch (ParseException p) {
            System.out.println("heta ynge");
            return false;
        }
    }

    public static void cutTextToMaxLength(Task task) {
        String taskName = task.getTaskName();
        String assignee = task.getAssignee();
        String description = task.getDescription();
        String sectionName = task.getSectionName();
        if (taskName.length() > 255) {
            task.setTaskName(taskName.substring(0, 255));
        }
        if (assignee.length() > 255) {
            task.setTaskName(taskName.substring(0, 255));
        }
        if (description.length() > 255) {
            task.setTaskName(taskName.substring(0, 255));
        }
        if (sectionName.length() > 255) {
            task.setTaskName(taskName.substring(0, 255));
        }
    }

    @SneakyThrows
    public static List<Task> sortAndGet50(List<Task> tasks) {
        HashSet<Long> idsOfTasks = new HashSet<>();
        tasks.forEach(task -> idsOfTasks.add(task.getTaskNumber()));
        if (idsOfTasks.size() != tasks.size()){
            System.out.println("idn nuynna");
            throw new Exception();
        }
        Collections.sort(tasks, Comparator.comparing(Task::getTaskNumber));
        return tasks.stream().limit(Math.min(tasks.size(), 50)).collect(Collectors.toList());
    }

    @SneakyThrows
    public static void checkSectionNames(List<Task> tasks) {
        var blankTextCount = 0;
        var textCount = 0;
        for (Task task : tasks) {
            if (task.getSectionName().isBlank()) {
                blankTextCount++;
            } else {
                textCount++;
            }
        }
        if(blankTextCount > 0 && textCount > 0){
            System.out.println("section name error");
            throw new Exception();
        }
    }
}
