package com.epam.cloudgantt.service;


public interface MailService {

    void send(String to, String subject, String message);

}
