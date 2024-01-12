package vn.codegym.houserental.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import vn.codegym.houserental.model.EmailDetails;
import vn.codegym.houserental.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.Arrays;

import static org.antlr.v4.runtime.misc.Utils.readFile;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}") private String sender;

    public String sendSimpleMail(EmailDetails details)
    {
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
//            mailMessage.setText(details.getMsgBody());
//            mailMessage.setSubject(details.getSubject());
            String htmlTemplate = Arrays.toString(readFile("template.html"));
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    public String
    sendMailWithAttachment(EmailDetails details)
    {
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());

            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
    }

    @Override
    public String sendMailWithTemplate(EmailDetails details, String template, Context context) {

        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage, true);;
            mimeMessageHelper.setTo(details.getRecipient());
            String htmlContent = templateEngine.process(template,context);
            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        } catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
    }


}
