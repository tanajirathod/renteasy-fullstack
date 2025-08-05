/* package com.renteasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendInterestNotification(String sellerEmail, String sellerName, 
                                         String propertyTitle, String buyerName, 
                                         String buyerEmail, String buyerPhone) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sellerEmail);
        message.setSubject("New Interest in Your Property: " + propertyTitle);
        
        String text = String.format(
            "Dear %s,\n\n" +
            "A buyer has shown interest in your property!\n\n" +
            "Property: %s\n" +
            "Buyer: %s\n" +
            "Email: %s\n" +
            "Phone: %s\n\n" +
            "Contact them directly to discuss details.\n\n" +
            "Best regards,\nRentEasy Team",
            sellerName, propertyTitle, buyerName, buyerEmail, buyerPhone
        );
        
        message.setText(text);
        mailSender.send(message);
    }

    public void sendWelcomeEmail(String userEmail, String userName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user极狐
        message.setSubject("Welcome to RentEasy!");
        
        String text = String.format(
            "Dear %s,\n\n" +
            "Welcome to RentEasy! Your account has been successfully created.\n\n" +
            "You can now:\n- Browse properties\n- Express interest\n- List properties\n\n" +
            "Happy renting!\n\n" +
            "Best regards,\nRentEasy Team",
            userName
        );S
        message.setText(text);
        mailSender.send(message);
    }
}
*/