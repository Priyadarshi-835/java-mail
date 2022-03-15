package mail;

import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.http.IHttpRequest;
import com.microsoft.graph.models.extensions.*;
import com.microsoft.graph.models.generated.BodyType;
import com.microsoft.graph.requests.extensions.AttachmentCollectionPage;
import com.microsoft.graph.requests.extensions.AttachmentCollectionResponse;
import com.microsoft.graph.requests.extensions.GraphServiceClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SendMailOutlook {
    public static void main(String[] args) throws IOException {
        String accessToken = "EwB4A8l6BAAUwihrrCrmQ4wuIJX5mbj7rQla6TUAAVfg6q+o38+BLvZ/6pGHGddkBzvTgqRBeWIBBL3eAeZgNghC6u6ySgGcD8cWTZE3NNIjkIrvTv13jJWua+ZOrNOx3eGfn5Zfe1wWgKg6OkBNmhVuCuXV4gCVNSvgNYrMp0+vSoaacM1/8HVZgyeae6SRTQ9yNif5uIM006e2PjqC2bASS2N29hOprZBJc0gmeWKrXoB5+2xF2LnmgV1Fi0KYVOLcwhrec9QF9L+PKoXsq/8PaAOiuySFpFDP3NN/1Wfpu7D0h7OHgx/T1y6ZRucjDjs16FuhmHO7vzvLPF6ttbkgiuWH2LFMW33m8e8xyyOqBmeLAWSnu6ePrG1pKkADZgAACMcsKUBR3pMXSAIlhbBVUcZUQ8kXpckfSRZjHEftv9SxWm4HraiYTbpn7yO9R1HSvDbVUkvfHYHQVbEWcfWv98sZA5pO4dJ4Mh2BaURaE8WfkIOhYTjI7Ee+lj1blrBde43Y7d3HunAlOK+Cq+rHd4oI4vMlAlc5NB8zWDkSNxAKlTpb3tM1pBfYsBB3wS5fy0OWI/sS29Xi9DnzH6OlQQ/pvGGHfh/bfP3JeDOJHsZV+eRn8bfidMkIsRIZw7ZCKtxoB25o4/F8wYsQkbNLQE0e1fR8Ljleua5LYnrlvw0jEnhrUMtkT1lZhszwqDuz9Kp/yXQHMq2nnkc2cgRbZd0XjLmlQOx6IZl1cRZkDXAcuonwYrYNzKxX/GwNQVVATpYWofIa6W/ZtjD4KMwp+fQo98CEq4pttJvcC2v7+2PN451I+3AZM5sqcU46UAdqWSSK5GWZslX3V7D1b6XB5xhzOzuyLNj73kXnZEkmgvMtKxi5/VHAc/MTpeiETDLvg/a3FgllaqMCgagd12byDDuEfm1RTrAlq9qwTF+/HAgm06OOUbuuVT5xEk7rglcFhkF5O9nNQYLO2eL9oJgINQn+N5T5FoLqO0BKDP+ohs6xjh9BScOzPxEs7jX7TMMmuJINJobSSAF5iiki5TvQ3gZbRqLvs33ViAi4SqyOMT0IP4W+pijXqp9qvCH2ygJAYBo0SH4DX+frUAJyIDv+4xInNH0cVScM+S/c9Cv38azXGk1ynyMRvXL7sQMakOgRFYuUxEhctG9Q5/uK03RYviMitogC";
        sendEmail(accessToken);
        //sendEmailWithAttachment(accessToken);
    }

    private static void sendEmailWithAttachment(String accessToken) throws IOException {
        GraphServiceClient graphClient = (GraphServiceClient) GraphServiceClient.builder().authenticationProvider(new SimpleAuthProvider(accessToken)).buildClient();

        Message message = new Message();
        message.subject = "Test email with attachment(s)";
        ItemBody body = new ItemBody();
        body.contentType = BodyType.TEXT;
        body.content = "Hi Niketan,\n\nSending a test email.";
        message.body = body;
        List<Recipient> toRecipientsList = new ArrayList<>();
        Recipient toRecipients = new Recipient();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.address = "priyaniketan@gmail.com";
        toRecipients.emailAddress = emailAddress;
        toRecipientsList.add(toRecipients);
        message.toRecipients = toRecipientsList;
        List<Attachment> attachmentsList = new ArrayList<>();

        FileAttachment attachments = new FileAttachment();
        attachments.name = "attachment.txt";
        attachments.contentType = "text/plain";
        attachments.contentBytes = Files.readAllBytes(Paths.get("C:/Users/pniketan/Documents/SFF/Snow-Create-mapping.txt"));
        attachments.oDataType = "#microsoft.graph.fileAttachment";
        attachmentsList.add(attachments);

        AttachmentCollectionResponse attachmentCollectionResponse = new AttachmentCollectionResponse();
        attachmentCollectionResponse.value = attachmentsList;
        message.attachments = new AttachmentCollectionPage(attachmentCollectionResponse, null);

        graphClient.me()
                .sendMail(message, true)
                .buildRequest()
                .post();
    }

    private static void sendEmail(String accessToken) {
        GraphServiceClient graphClient = (GraphServiceClient) GraphServiceClient.builder().authenticationProvider(new SimpleAuthProvider(accessToken)).buildClient();

        Message message = new Message();
        message.subject = "Meet for lunch?";
        ItemBody body = new ItemBody();
        body.contentType = BodyType.TEXT;
        body.content = "The new cafeteria is open.";
        message.body = body;

        List<Recipient> toRecipientsList = new ArrayList<>();
        Recipient toRecipients = new Recipient();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.address = "pniketan@deloitte.com";
        toRecipients.emailAddress = emailAddress;
        toRecipientsList.add(toRecipients);
        message.toRecipients = toRecipientsList;

        List<Recipient> ccRecipientsList = new ArrayList<>();
        Recipient ccRecipients = new Recipient();
        EmailAddress emailAddress1 = new EmailAddress();
        emailAddress1.address = "priyaniketan@gmail.com";
        ccRecipients.emailAddress = emailAddress1;
        ccRecipientsList.add(ccRecipients);
        message.ccRecipients = ccRecipientsList;

        graphClient.me()
                .sendMail(message, false)
                .buildRequest()
                .post();
    }
}

class SimpleAuthProvider implements IAuthenticationProvider {

    private String accessToken;

    public SimpleAuthProvider(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public void authenticateRequest(IHttpRequest request) {
        // Add the access token in the Authorization header
        request.addHeader("Authorization", "Bearer " + accessToken);
    }
}

