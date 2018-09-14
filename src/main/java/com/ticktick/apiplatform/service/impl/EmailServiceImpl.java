package com.ticktick.apiplatform.service.impl;

import com.ticktick.apiplatform.entity.ResultEntity;
import com.ticktick.apiplatform.service.EmailService;
import freemarker.template.Template;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @Title EmailServiceImpl
 * @Description
 * @Author ZQian
 * @date: 2018/9/12 14:57
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Template templateEngine;


    @Value("${mail.username}")
    private String fromUser;


    @Override
    public void sendReports(List<ResultEntity> results) {
//        Context context = new Context();
//
//        context.setVariable("id", "006");
//
//        String emailContent = templateEngine.process("report", context);
        sendHtmlMail("604922962@qq.com", "测试报告", "");
    }


    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(fromUser));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText("详情请查看附件", false);
            helper.addAttachment("report.html",
                    new ByteArrayResource(content.getBytes()));
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
