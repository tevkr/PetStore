package ru.mirea.petstore.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.mirea.petstore.Models.Purchase;
import ru.mirea.petstore.Models.User;
import ru.mirea.petstore.Repositories.IProductRepository;
import ru.mirea.petstore.Repositories.IPurchaseRepository;
import ru.mirea.petstore.Repositories.IUserRepository;

import javax.mail.*;
import javax.mail.internet.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Класс-сервис для отправки сообщений на электронную почту
 * @author Яновский Владислав
 */
@Service
public class EmailService {

    /**
     * Асинхронный метод отправки сообщения на почту
     * @param mail Адрес эектронной почты
     * @param message Сообщение
     * @param isManager Переменная показывающая посылается ли письмо менеджеру или обычному пользователю
     * @throws AddressException Исключение, возникающее при обнаружении неправильно отформатированного адреса
     * @throws MessagingException Базовый класс для всех исключений, создаваемых классами обмена сообщениями
     * @throws IOException Сигнализирует о том, что произошло какое-либо исключение ввода-вывода. Этот класс является общим классом исключений, создаваемых неудачными или прерванными операциями ввода-вывода.
     */
    @Async
    public void sendmail(String mail, String message, boolean isManager) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("nomxdqm@gmail.com", "xxxxxxxxx");
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("nomxdqm@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
        if (isManager)
            msg.setSubject("Petshop. Новый заказ.");
        else
            msg.setSubject("Petshop. Ваш заказ.");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html; charset=UTF-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
}
