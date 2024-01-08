package org.example.projectmodule6renthousebe.controller;


import org.example.projectmodule6renthousebe.model.EmailDetails;
import org.example.projectmodule6renthousebe.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

// Annotation
@RestController
// Class
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {

        return emailService.sendSimpleMail(details);
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
            @RequestBody EmailDetails details)
    {
        return emailService.sendMailWithAttachment(details);
    }

    @PostMapping("/sendMailWithTemplate")
    public String sendHtmlEmail(@RequestBody EmailDetails details) {
        Context context = new Context();
        context.setVariable("message", details.getMsgBody());

        emailService.sendMailWithTemplate(details, "index", context);
        return "HTML email sent successfully!";
    }
}