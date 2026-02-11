package com.example.Event_Manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Injects the email from application.properties to avoid hardcoding
    @Value("${spring.mail.username}")
    private String senderEmail;

    /**
     * 1. Send email to Student when they successfully register for an event.
     */
    public void sendRegistrationEmail(String toEmail, String userName, String eventTitle, String eventDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(toEmail);
        message.setSubject("Registration Confirmed: " + eventTitle);

        String body = "Hello " + userName + ",\n\n" +
                "You have successfully registered for the event: " + eventTitle + ".\n" +
                "Date: " + eventDate + "\n\n" +
                "Please carry your ID card to the venue.\n\n" +
                "See you there!\n" +
                "College Event Team";

        message.setText(body);
        sendEmailSafe(message);
    }

    /**
     * 2. Send email to Host when they successfully create a new event.
     */
    public void sendEventCreationEmail(String toEmail, String hostName, String eventTitle, String eventDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(toEmail);
        message.setSubject("Event Created Successfully: " + eventTitle);

        String body = "Hello " + hostName + ",\n\n" +
                "Your event '" + eventTitle + "' is now live in the system.\n" +
                "Scheduled Date: " + eventDate + "\n\n" +
                "Students can now start registering. You can manage this event from your dashboard.\n\n" +
                "Best,\n" +
                "College Event Team";

        message.setText(body);
        sendEmailSafe(message);
    }

    /**
     * 3. Send email to User when Super Admin approves their request to become a Host.
     */
    public void sendHostUpgradeEmail(String toEmail, String userName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(toEmail);
        message.setSubject("Role Upgrade Approved: You are now a Host!");

        String body = "Congratulations " + userName + ",\n\n" +
                "Your request to become an Event Host has been APPROVED by the Super Admin.\n\n" +
                "You now have access to:\n" +
                "- Create new events\n" +
                "- Update your events\n" +
                "- View participant lists\n\n" +
                "Please log out and log back in to see your new dashboard.\n\n" +
                "Welcome to the team!\n" +
                "College Event Team";

        message.setText(body);
        sendEmailSafe(message);
    }

    /**
     * Helper method to handle errors so the main app doesn't crash if internet is down.
     */
    private void sendEmailSafe(SimpleMailMessage message) {
        try {
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + message.getTo()[0]);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + message.getTo()[0] + ": " + e.getMessage());
            // We log the error but DO NOT throw it.
            // This ensures the user still gets registered even if the email fails.
        }
    }
}