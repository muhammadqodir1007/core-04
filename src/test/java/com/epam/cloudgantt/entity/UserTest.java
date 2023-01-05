package com.epam.cloudgantt.entity;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    static final String USERNAME = "test@gmail.com";
    private User test = new User();

    @Before
    public void setUp() {
        test.setEmail(USERNAME);
    }

    @Test
    void getUsername() {
        String username = test.getUsername();
        assertEquals(USERNAME, username);
    }
}