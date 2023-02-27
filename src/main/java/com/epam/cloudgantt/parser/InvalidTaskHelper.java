package com.epam.cloudgantt.parser;

import com.epam.cloudgantt.config.MessageByLang;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class InvalidTaskHelper {
    private InvalidTaskHelper() {
    }

    protected static final Set<Long> circularDependencyTasks = new HashSet<>();
    protected static final Set<Long> nonExistentDependencyTasks = new HashSet<>();
    protected static final Set<Long> notNumberDependencyTasks = new HashSet<>();
    protected static final Set<Long> beginDateDoesNotPrecedeEndDateTasks = new HashSet<>();

    public static List<String> getAllMessages() {
        List<String> messages = new ArrayList<>();
        if (!circularDependencyTasks.isEmpty()) {
            messages.add(getCircularDependencyMessage());
        }
        if (!nonExistentDependencyTasks.isEmpty()) {
            messages.add(getNonExistentDependencyMessage());
        }
        if (!notNumberDependencyTasks.isEmpty()) {
            messages.add(getNotNumberDependencyMessage());
        }
        if (!beginDateDoesNotPrecedeEndDateTasks.isEmpty()) {
            messages.add(getBeginDateDoesNotPrecedeEndDateMessage());
        }
        clearErrorLists();
        return messages;
    }

    private static String getBeginDateDoesNotPrecedeEndDateMessage() {
        List<Long> tasks = beginDateDoesNotPrecedeEndDateTasks.stream().toList();

        if (beginDateDoesNotPrecedeEndDateTasks.size() == 1) {
            return String.format(
                    MessageByLang.getMessage("CSV_BEGIN_DATE_DOES_NOT_PRECEDE_END_DATE_SINGLE"),
                    tasks.get(0));
        } else {
            return String.format(
                    MessageByLang.getMessage("CSV_BEGIN_DATE_DOES_NOT_PRECEDE_END_DATE_MULTIPLE"),
                    StringUtils.collectionToDelimitedString(beginDateDoesNotPrecedeEndDateTasks, ",")
            );
        }
    }

    private static String getNotNumberDependencyMessage() {
        List<Long> tasks = notNumberDependencyTasks.stream().toList();

        if (notNumberDependencyTasks.size() == 1) {
            return String.format(
                    MessageByLang.getMessage("CSV_DEPENDS_ON_NOT_NUMBER_SINGLE"),
                    tasks.get(0));
        } else {
            return String.format(
                    MessageByLang.getMessage("CSV_DEPENDS_ON_NOT_NUMBER_MULTIPLE"),
                    StringUtils.collectionToDelimitedString(notNumberDependencyTasks, ",")
            );
        }
    }

    private static String getNonExistentDependencyMessage() {
        List<Long> tasks = nonExistentDependencyTasks.stream().toList();

        if (nonExistentDependencyTasks.size() == 1) {
            return String.format(
                    MessageByLang.getMessage("CSV_DEPENDENCY_NOT_EXIST_SINGLE"),
                    tasks.get(0));
        } else {
            return String.format(
                    MessageByLang.getMessage("CSV_DEPENDENCY_NOT_EXIST_MULTIPLE"),
                    StringUtils.collectionToDelimitedString(nonExistentDependencyTasks, ",")
            );
        }
    }

    private static String getCircularDependencyMessage() {
        List<Long> tasks = circularDependencyTasks.stream().toList();

        if (circularDependencyTasks.size() == 1) {
            return String.format(MessageByLang.getMessage("CSV_CIRCULAR_DEPENDENCY_SINGLE"),
                    tasks.get(0));
        } else {
            return String.format(MessageByLang.getMessage("CSV_CIRCULAR_DEPENDENCY_MULTIPLE"),
                    StringUtils.collectionToDelimitedString(circularDependencyTasks, ","));
        }
    }


    public static void addCircularDependency(Long taskNumber) {
        circularDependencyTasks.add(taskNumber);
    }

    public static void addNonExistentDependency(Long taskNumber) {
        nonExistentDependencyTasks.add(taskNumber);
    }

    public static void addNotNumberDependency(Long taskNumber) {
        notNumberDependencyTasks.add(taskNumber);
    }

    public static void addBeginDateNotPrecedeEndDateTask(Long taskNumber) {
        beginDateDoesNotPrecedeEndDateTasks.add(taskNumber);
    }

    public static void clearErrorLists() {
        circularDependencyTasks.clear();
        nonExistentDependencyTasks.clear();
        notNumberDependencyTasks.clear();
        beginDateDoesNotPrecedeEndDateTasks.clear();
    }

}
