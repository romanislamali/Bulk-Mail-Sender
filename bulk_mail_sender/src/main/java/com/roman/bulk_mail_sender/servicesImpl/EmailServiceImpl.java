package com.roman.bulk_mail_sender.servicesImpl;

import com.roman.bulk_mail_sender.dto.EmailDto;
import com.roman.bulk_mail_sender.services.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration config;
    public void sendBulkEmail(List<EmailDto> emailList) throws MessagingException {
        try {
            MimeMessage[] messages = new MimeMessage[emailList.size()];
            for (int i = 0; i < emailList.size(); i++) {
                MimeMessage  message = mailSender.createMimeMessage();
                // set MediaType
                MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name());
                Template t = config.getTemplate("email-template.ftl");
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, emailList.get(i));
                helper.setText(html, true);
                helper.setTo(emailList.get(i).getTo());
                helper.setSubject(emailList.get(i).getSubject());

                messages[i] = message;
            }
            mailSender.send(messages);
        } catch (Exception e) {
            log.error("Error occurs "+e);
        }
    }
}
