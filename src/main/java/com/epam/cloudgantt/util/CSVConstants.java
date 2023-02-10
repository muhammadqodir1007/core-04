package com.epam.cloudgantt.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface CSVConstants
{

    String TASK_NUMBER = "task_number";

    String SECTION_NAME = "section_name";

    String TASK_NAME = "task_name";

    String DESCRIPTION = "description";

    String BEGIN_DATE = "begin_date";

    String END_DATE = "end_date";

    String ASSIGNEE = "assignee";

    String DEPENDENCY = "dependency";

    Set<String> REQUIRED_HEADERS = new HashSet<>(List.of(TASK_NUMBER, TASK_NAME,
        SECTION_NAME, DESCRIPTION,
        BEGIN_DATE, END_DATE, ASSIGNEE, DEPENDENCY
    ));

    long MAX_FILE_SIZE = 5 * 1024 * 1024;
}
