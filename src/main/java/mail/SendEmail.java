package mail;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {
    public static void main(String[] args) {
        String to = "pniketan@deloitte.com";
        String from = "pniketan@deloitte.com";

        String user = "pniketan@deloitte.com";
        String password = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik1yNS1BVWliZkJpaTdOZDFqQmViYXhib1hXMCJ9.eyJhdWQiOiI0YjhiOTgzYS03MGYzLTRlNDEtYWYxYy01MGM2MWQ1YmY3NDUiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vZGIwNzQxZmItNDhjZC00ZDJkLTgyOTUtZmQ5ZWNjYWY0OTAxL3YyLjAiLCJpYXQiOjE2NDQ5MTExMTMsIm5iZiI6MTY0NDkxMTExMywiZXhwIjoxNjQ0OTE1MDEzLCJhaW8iOiJFMlpnWVBBb2NkZTVZWkJvWWJ2dDFQSzVzMjljQlFBPSIsImF6cCI6IjRiOGI5ODNhLTcwZjMtNGU0MS1hZjFjLTUwYzYxZDViZjc0NSIsImF6cGFjciI6IjEiLCJvaWQiOiJmYjMxZTQyMi1lNDFhLTRjNmYtYWY4OC1jNzAzYTZjZjEyYjEiLCJyaCI6IjAuQVZVQS0wRUgyODFJTFUyQ2xmMmV6SzlKQVRxWWkwdnpjRUZPcnh4UXhoMWI5MFdJQUFBLiIsInN1YiI6ImZiMzFlNDIyLWU0MWEtNGM2Zi1hZjg4LWM3MDNhNmNmMTJiMSIsInRpZCI6ImRiMDc0MWZiLTQ4Y2QtNGQyZC04Mjk1LWZkOWVjY2FmNDkwMSIsInV0aSI6IjFhNFlVOTRBTVVxWFQzLTNJd1ljQUEiLCJ2ZXIiOiIyLjAifQ.v1bupbwBw-cUS0cSnBXqIb_wwfKNYNqH9uViVJz2kt3IAUSclGW_8QtzfKTKY4ESQ7pxa725lWMChFjj-i2yhqW9WhJiCuC9d-bxrOmHe19s_T2W_rNHv5CPYtarQP2EaIgAb8UYG-vn8GdafTy2s5MePIit5XX1XK4rKAo38vrrmC-_bapY8ARAXT2S5d959FbpcF-dKeGlbwtirxV7DCjotRVcI_5KuYtiUdqlrdHsxP04NG0xAU0ptZwXOLYqk_qDET2TYE3cxWM9lvll_zp6c8JaJ94PRzueRxWxDCJzyIc_Pi8WIDgi9GVsZ40_jPn8hB7IjlngtRyd9DnZQg";

        Properties properties = System.getProperties();
//        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp-mail.outlook.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp-mail.outlook.com");

        //  properties.put("mail.smtp.ssl.trust", "*");
        // properties.put("mail.smtp.starttls.required", true);
//        properties.put("mail.smtp.sasl.enable", true);
//        properties.put("mail.smtp.sasl.mechanisms", "STARTTLS");


        //Starting a session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Welcome!!!");

            //Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Hey Bob,\n Welcome to the team");

            //Crete the Multipart part
            Multipart multipart = new MimeMultipart();

            //Add message body part
            multipart.addBodyPart(messageBodyPart);

            //Add attachments
            messageBodyPart = new MimeBodyPart();
            String fileName = "Mulesoft Architecture";
            String filePath = "C:\\Users\\pniketan\\Downloads\\DSFAW-672.jpg";
            DataSource source = new FileDataSource(filePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            //Send the complete message
            message.setContent(multipart);

            //Send Message
            Transport.send(message);
            System.out.println("Email sent successfully");


        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
