package auth.server.services;

import auth.server.entities.Employee;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.*;

@Service
public class EmailEmployeeDistributionService {


    @Value("classpath:/mail-image.jpeg")
    Resource resourceFile;

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;
    private final EmployeeService employeeService;

    public EmailEmployeeDistributionService(JavaMailSender javaMailSender,SpringTemplateEngine thymeleafTemplateEngine,EmployeeService employeeService) {
        this.emailSender = javaMailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.employeeService = employeeService;
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.addInline("attachment.jpeg", resourceFile);
        emailSender.send(message);
    }

    private void sendMessageUsingThymeleafTemplate(
            String to, String subject, Map<String, Object> templateModel, Locale locale)
            throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        thymeleafContext.setLocale(locale);
        String htmlBody = thymeleafTemplateEngine.process("/email/email-template.html", thymeleafContext);
        sendHtmlMessage(to, subject, htmlBody);
    }

    @Scheduled(cron = "0 0 9 * * *")
    @Async
    public void scheduleEmailDistribution() throws MessagingException {
        Page<Employee> employeeList = this.employeeService.getEmployees(0,20);
        for(int page = 1; page <= employeeList.getTotalPages(); page++) {
            for(Employee employee : employeeList.getContent()) {
                String employeeEmail = employee.getEmployeeEmail();
                Locale employeeLocale = new Locale(employee.getLocale());
                String subject = "Greeting to " + employee.getEmployeeName();
                Map<String, Object> templateModel = new HashMap<>();
                templateModel.put("employeeEmail", employeeEmail);
                templateModel.put("name",employee.getEmployeeName());
                sendMessageUsingThymeleafTemplate(employeeEmail,subject,templateModel,employeeLocale);
            }
            employeeList = this.employeeService.getEmployees(page,20);
        }
    }
}
