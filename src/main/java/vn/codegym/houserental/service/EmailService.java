package vn.codegym.houserental.service;

import vn.codegym.houserental.model.EmailDetails;
import org.thymeleaf.context.Context;


// Interface
public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
    String sendMailWithTemplate(EmailDetails details, String template, Context context);
}