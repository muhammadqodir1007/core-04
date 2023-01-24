package com.epam.cloudgantt.entity;


import com.epam.cloudgantt.exceptions.RestException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    static final String USERNAME = "test@gmail.com";
    private User test = new User();


    @Test
    void getUsername() {
        test.setEmail(USERNAME);
        String username = test.getUsername();
        assertEquals(USERNAME, username);

        assertTrue(test.getUsername().equals(USERNAME));
//        assertThrows(RestException.class, () -> {
//            test.setEmail("test3@epam.com");});
    }
}