package uz.pdp.restaurantcustomerservice.service;

import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import uz.pdp.restaurantcustomerservice.entity.Customer;
import uz.pdp.restaurantcustomerservice.security.jwt.JwtTokenProvider;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final Configuration configuration;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${frontend.url}")
    private String verificationUrl;
    @SneakyThrows
    @Async
    public void sendEmailVerificationMessage(String username,String email) {
        TimeUnit.SECONDS.sleep(10);
        var helper = new MimeMessageHelper(javaMailSender.createMimeMessage());
        helper.setFrom("escobar.vonn2002@gmail.com");
        helper.setTo(email);
        var template = configuration.getTemplate("mail/email-verification.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(
                template,
                Map.of("link", verificationUrl + jwtTokenProvider.generateForEmail(
                        Customer.builder()
                                .username(username)
                                .email(email)
                                .build()
                ))
        );
        helper.setText(html,true);
        javaMailSender.send(helper.getMimeMessage());
    }

}
