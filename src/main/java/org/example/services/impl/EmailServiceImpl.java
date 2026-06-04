package org.example.services.impl;

import org.example.models.dtos.ContactMessageDto;
import org.example.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${RESEND_API_KEY}")
    private String resendApiKey;

    public void sendContactEmail(ContactMessageDto contactMessage) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            headers.setBearerAuth(resendApiKey);

            Map<String, Object> body = new HashMap<>();
            body.put("from", "onboarding@resend.dev");
            body.put("to", "emaildjwork@gmail.com");
            body.put("reply_to", contactMessage.getEmail());
            body.put("subject", "Ново съобщение от сайта Megapod - " + contactMessage.getName());

            String textContent = "Име: " + contactMessage.getName() + "\n" +
                    "Имейл: " + contactMessage.getEmail() + "\n" +
                    "Телефон: " + contactMessage.getPhone() + "\n\n" +
                    "Съобщение:\n" + contactMessage.getMessage();
            body.put("text", textContent);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            restTemplate.postForObject("https://api.resend.com/emails", request, String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}