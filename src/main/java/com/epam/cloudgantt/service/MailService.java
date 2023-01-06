package com.epam.cloudgantt.service;


public interface MailService {

    void sendEmailForSignUpConfirmation(String to, String subject, String link);

    void sendEmailForForForgotPassword(String to, String subject, String link);

    String textForConfirmationEmail(String confirmLink);

    String textForForgotPassword(String confirmLink);


}
