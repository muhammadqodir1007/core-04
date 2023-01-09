package com.epam.cloudgantt.service;


public interface MailService {

    void sendEmailForSignUpConfirmation(String to, String verificationCode);

    void sendEmailForForForgotPassword(String to, String verificationCode);

    String textForConfirmationEmail(String confirmLink);

    String textForForgotPassword(String confirmLink);


}
