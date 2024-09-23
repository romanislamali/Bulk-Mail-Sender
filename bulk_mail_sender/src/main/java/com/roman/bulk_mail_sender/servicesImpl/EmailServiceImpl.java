package com.roman.bulk_mail_sender.servicesImpl;

import com.roman.bulk_mail_sender.dto.ApiResponse;
import com.roman.bulk_mail_sender.dto.EmailDto;
import com.roman.bulk_mail_sender.services.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration config;

    @Override
    public ApiResponse sendBulkEmail(List<EmailDto> emailList) {
        ApiResponse response = new ApiResponse();

        try {
            MimeMessage[] messages = new MimeMessage[emailList.size()];

            for (int i = 0; i < emailList.size(); i++) {
                MimeMessage message = mailSender.createMimeMessage();

                MimeMessageHelper helper = new MimeMessageHelper(
                        message,
                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name()
                );

                Map<String, Object> templateData = new HashMap<>();
                String email = emailList.get(i).getTo();
                String username = email.substring(0, email.indexOf('@'));
                templateData.put("username", username);
                Template t = config.getTemplate("email-template.ftl");
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, templateData);

                helper.setText(html, true);
                helper.setTo(emailList.get(i).getTo());
                helper.setSubject(emailList.get(i).getSubject());

                messages[i] = message;
            }

            mailSender.send(messages);

            response.setSuccess(true);
            response.setMessage("Bulk email sent successfully!");
            log.info("Bulk email sent successfully!");

        } catch (Exception e) {
            log.error("Failed to send bulk email: " + e.getMessage(), e);
            response.setSuccess(false);
            response.setMessage("Failed to send bulk email: " + e.getMessage());
        }

        return response;
    }
}
