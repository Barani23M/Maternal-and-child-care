package com.maternal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service for sending emails
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to: {}", to, e);
            // Don't throw exception to prevent blocking main flow
        }
    }

    public void sendWelcomeEmail(String to, String fullName) {
        String subject = "Welcome to Maternal Care System";
        String text = String.format(
                "Dear %s,\n\n" +
                "Welcome to the Maternal and Child Health Care Monitoring System!\n\n" +
                "We're excited to have you join our community. Our platform is designed to help you " +
                "track and manage your pregnancy journey and child health care with ease.\n\n" +
                "If you have any questions, please don't hesitate to reach out to our support team.\n\n" +
                "Best regards,\n" +
                "Maternal Care Team",
                fullName
        );
        sendSimpleEmail(to, subject, text);
    }

    public void sendAppointmentReminder(String to, String appointmentDetails) {
        String subject = "Appointment Reminder";
        String text = String.format(
                "Dear User,\n\n" +
                "This is a reminder about your upcoming appointment:\n\n" +
                "%s\n\n" +
                "Please make sure to arrive 15 minutes early.\n\n" +
                "Best regards,\n" +
                "Maternal Care Team",
                appointmentDetails
        );
        sendSimpleEmail(to, subject, text);
    }

    public void sendVaccinationReminder(String to, String babyName, String vaccineName) {
        String subject = "Vaccination Reminder for " + babyName;
        String text = String.format(
                "Dear Parent,\n\n" +
                "This is a reminder that %s's %s vaccination is due soon.\n\n" +
                "Please schedule an appointment with your healthcare provider.\n\n" +
                "Best regards,\n" +
                "Maternal Care Team",
                babyName, vaccineName
        );
        sendSimpleEmail(to, subject, text);
    }
}
