/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author Harshvardhan Kale
 */
public class EmailUtil {
    
    public static void sendEmail(String to, String plan, int duration, String designation) {
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String from = "gym.system2@gmail.com";
        String password = "ioHANDling7u";
        
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        
        String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd LLLL, yyyy"));
        String endDate = LocalDate.now().plusMonths(duration).format(DateTimeFormatter.ofPattern("dd LLLL, yyyy"));
        Message fullMessage = null;
        
        if(designation.equals("Dietician")) {
            String message = "Hi there!\n"
                    + "Here's your curated diet plan. Please follow this plan from " + startDate + " to " + endDate + ".";
            message = message + "\n\n" + plan;
            fullMessage = getMessage(session, from, to, message, "Dietician");
        }
        
        else if(designation.equals("Trainer")) {
            String message = "Hi there!\n"
                    + "Here's your curated workout plan. Please follow this plan from " + startDate + " to " + endDate + ".";
            message = message + "\n\n" + plan;
            fullMessage = getMessage(session, from, to, message, "Trainer");
        }
        

        try {
            Transport.send(fullMessage);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static Message getMessage(Session session, String from, String to, String text, String designation) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            
            if(designation.equals("Dietician"))
                message.setSubject("Your Diet Plan");
            else if(designation.equals("Trainer"))
                message.setSubject("Your Workout Plan");
            
            message.setText(text);
            
            return message;
        } catch (MessagingException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        return null;
    }
}
