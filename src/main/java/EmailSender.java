import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EmailSender {

	public static void main(String[] args) {
		// Sender's email address and password
		String senderEmail = "muhammedsaidsyed215@gmail.com";
		String password = "your_password"; // Update with your password
		String path_to_pdf_file = "path_to_pdf_file.pdf";
		String email_list_csv = "email_list.csv";

		try {
			// Read email addresses from CSV file
			BufferedReader csvReader = new BufferedReader(new FileReader(email_list_csv));
			String email;
			while ((email = csvReader.readLine()) != null) {
				// Read message from text file
				BufferedReader messageReader = new BufferedReader(new FileReader("message.txt"));
				StringBuilder messageContent = new StringBuilder();
				String line;
				while ((line = messageReader.readLine()) != null) {
					messageContent.append(line);
					messageContent.append("\n");
				}
				messageReader.close();

				// Create an email
				MultiPartEmail multiPartEmail = new MultiPartEmail();
				multiPartEmail.setHostName("smtp.gmail.com");
				multiPartEmail.setSmtpPort(465);
				multiPartEmail.setAuthenticator(new javax.mail.Authenticator() {
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication(senderEmail, password);
					}
				});
				multiPartEmail.setSSLOnConnect(true);
				multiPartEmail.setFrom(senderEmail);
				multiPartEmail.addTo(email);
				multiPartEmail.setSubject("Apply for Software Test Engineer");
				multiPartEmail.setMsg(messageContent.toString());

				// Attach PDF file
				EmailAttachment attachment = new EmailAttachment();
				attachment.setPath(path_to_pdf_file); // Update with your PDF file path
				attachment.setDisposition(EmailAttachment.ATTACHMENT);
				attachment.setDescription("PDF File");
				attachment.setName("mohamed_said_software_resume.pdf");
				multiPartEmail.attach(attachment);

				// Send email
				multiPartEmail.send();
			}
			csvReader.close();
			System.out.println("Emails sent successfully.");
		} catch (IOException | EmailException e) {
			e.printStackTrace();
		}
	}
}
