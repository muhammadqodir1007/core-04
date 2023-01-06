package com.epam.cloudgantt.service;

import com.epam.cloudgantt.config.MessageByLang;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private final static Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);
    @Value("${spring.mail.username}")
    String sender;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.iPAndPort}")
    String confirmLinkIPAndPort;

    @Value("${spring.mail.conformEmailForSignUpURL}")
    String conformEmailForSignUpURL;


    @Async
    @Override
    public void sendEmailForSignUpConfirmation(String to, String subject, String link) {


        try {
            LOGGER.info(link);
            LOGGER.info(subject);
            LOGGER.info(to);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, " charset=utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(sender);
            String s = confirmLinkIPAndPort + conformEmailForSignUpURL + link;
            String textForEmail = textForConfirmationEmail(s);
            mimeMessage.setContent(textForEmail, "text/html; charset=utf-8");

            Thread thread = new Thread(() -> mailSender.send(mimeMessage));
            thread.start();
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    @Override
    public void sendEmailForForForgotPassword(String to, String subject, String link) {


        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, " charset=utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(sender);
            String forgotPassword = textForForgotPassword(link);
            mimeMessage.setContent(forgotPassword, "text/html; charset=utf-8");
            Thread thread = new Thread(() -> mailSender.send(mimeMessage));
            thread.start();
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }


    }


    @Override
    public String textForConfirmationEmail(String confirmLink) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +

                "<style>\n" +
                "    .hero {\n" +
                "        background-color: #FAF8F1;\n" +
                "        color:#000;\n" +
                "        width:100%;\n" +
                "        padding:15px 20px;\n" +
                "        border-radius: 25px;\n" +
                "\n" +
                "    }\n" +
                "    .btn-secondary {\n" +
                "        background-color: #DD5353 !important;\n" +
                "        border:0;\n" +
                "        outline: none;\n" +
                "        border-radius: 25px;\n" +
                "        font-size:18px;\n" +
                "        font-weight: 400;\n" +
                "        padding:10px 20px !important;\n" +
                "        font-family: -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, Helvetica, Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n" +
                "\n" +
                "\n" +
                "    }\n" +
                "    .btn-secondary:hover, .btn-secondary:focus {\n" +
                "        outline: none !important;\n" +
                "        box-shadow: none !important;\n" +
                "    }\n" +
                "</style>\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"container p-5 mt-5\">\n" +
                "    <div class=\"hero\">\n" +
                "        <div class=\"px-4 py-5 my-5 text-center\">\n" +
                "            <div class=\"col-lg-6 mx-auto\">\n" +
                "                <p class=\"lead mb-4\"> " + MessageByLang.getMessage("WELCOME_TO_CLOUD_GANTT!") + "" +
                "<br> <br>" +
                "\n" +
                "" + MessageByLang.getMessage("PLEASE_CLICK_HERE_TO_VERIFY_YOUR_EMAIL") + " " +
//                " <br> <br> <br> \n" +
                "<div class=\"d-grid gap-2 d-sm-flex justify-content-sm-center\">\n" +
                "                    <button type=\"button\" class=\"btn btn-secondary btn-lg px-4 gap-3\"><a href=\"" + confirmLink + "\">Confirm</a></button>\n" +
                "                </div> <br>\n" +

                "Thank you for doing that,<br>\n" +

                "CloudGantt <br> <br> <br>\n" +

                " " + MessageByLang.getMessage("THIS_IS_AN_AUTOGENERATED_MAIL_DO_NOT_REPLY_TO_THIS") + "  " +
                " </p>\n" +

                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }

    @Override
    public String textForForgotPassword(String link) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "<style>\n" +
                "    .hero {\n" +
                "        background-color: #FAF8F1;\n" +
                "        color:#000;\n" +
                "        width:100%;\n" +
                "        padding:15px 20px;\n" +
                "        border-radius: 25px;\n" +
                "\n" +
                "    }\n" +
                "    .btn-secondary {\n" +
                "        background-color: #DD5353 !important;\n" +
                "        border:0;\n" +
                "        outline: none;\n" +
                "        border-radius: 25px;\n" +
                "        font-size:18px;\n" +
                "        font-weight: 400;\n" +
                "        padding:10px 20px !important;\n" +
                "        font-family: -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, Helvetica, Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n" +
                "\n" +
                "\n" +
                "    }\n" +
                "    .btn-secondary:hover, .btn-secondary:focus {\n" +
                "        outline: none !important;\n" +
                "        box-shadow: none !important;\n" +
                "    }\n" +
                "</style>\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"container p-5 mt-5\">\n" +
                "    <div class=\"hero\">\n" +
                "        <div class=\"px-4 py-5 my-5 text-center\">\n" +
                "            <h1 class=\"display-5 fw-bold\">Confirmation Request !</h1>\n" +
                "            <div class=\"col-lg-6 mx-auto\">\n" +
                "                <p class=\"lead mb-4\"> " + MessageByLang.getMessage("WELCOME_TO_CLOUD_GANTT!") + "" +
                "\n" +
                "" + MessageByLang.getMessage("CLICK_BUTTON_BELOW_TO_CHANGE_YOUR_PASSWORD") + " " +
                " <br> <br> <br> \n" +
                "<div class=\"d-grid gap-2 d-sm-flex justify-content-sm-center\">\n" +
                "                    <button type=\"button\" class=\"btn btn-secondary btn-lg px-4 gap-3\"><a href=\"" + link + "\">Confirm</a></button>\n" +
                "                </div> <br>\n" +

                "Thank you for doing that,<br>\n" +

                "CloudGantt <br> <br> <br>\n" +

                " " + MessageByLang.getMessage("THIS_IS_AN_AUTOGENERATED_MAIL_DO_NOT_REPLY_TO_THIS") + "  " +
                " </p>\n" +

                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }
}


