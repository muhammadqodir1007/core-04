package com.epam.cloudgantt.service;


public interface MailService {

    void send(String to, String subject, String message);
    public String textForEmail(String confirmLink);

}
