package com.maice.controller.components;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.maice.model.MaiceConstants;

@Component
@RequestScope
public class MailController {

	public String sendRegistrationEmail(String emailTo){
		String hash = UUID.randomUUID().toString();
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", MaiceConstants.MAIL_HOST);
		props.put("mail.smtp.port", MaiceConstants.MAIL_PORT);
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(MaiceConstants.MAIL_USER, MaiceConstants.MAIL_PASSWORD);
			}
		  });
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(MaiceConstants.MAIL_USER));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(emailTo));
			message.setSubject("Please do not reply");
			message.setText("Guten Tag,\n"
					+ "vielen Dank für ihre Registrierung bei MAICE.\n"
					+ "Bitte bestätigen Sie ihre Email-Adresse durch Klick auf den folgenden Link:\n"
					+ "http://localhost:4949/register/" + hash);
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return hash;
	}
}
