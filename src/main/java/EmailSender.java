import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EmailSender {

	public static void main(String[] args) throws InterruptedException {
		// Sender's email address and password
		String senderEmail = "muhammedsaidsyed215@gmail.com";
		String password = "rogc gojz mwfj exjc"; // Update with your password
		String path_to_pdf_file = "C:\\Users\\moham\\eclipse-workspace\\Send_many_emails\\mohamed_said_software_testing.pdf";
		String email_list_csv = "C:\\Users\\moham\\eclipse-workspace\\Send_many_emails\\Company_Emails_UAE.csv";
		String message_text = "C:\\Users\\moham\\eclipse-workspace\\Send_many_emails\\message.txt";
		String to_email = "";
		String line = "";

		try {
			// Read email addresses from CSV file
			BufferedReader csvReader = new BufferedReader(new FileReader(email_list_csv));
			System.out.println("--- BufferedReader csvReader: " + csvReader.toString());
			// Read message from text file
			StringBuilder messageContent = new StringBuilder();
			System.out.println("--- StringBuilder messageContent: " + messageContent.toString());
			BufferedReader messageReader = new BufferedReader(new FileReader(message_text));
			System.out.println("--- BufferedReader messageReader: " + messageReader.toString());

			while ((line = messageReader.readLine()) != null) {
				messageContent.append(line);
				messageContent.append("\n");
				System.out.println("--- messageReader Line: " + line.toString());
			}
			System.out.println("--- messageContent: " + messageContent.toString());
			messageReader.close();
			// Attach PDF file
			EmailAttachment attachment = new EmailAttachment();
			System.out.println("--- EmailAttachment attachment: " + attachment.toString());
			attachment.setPath(path_to_pdf_file); // Update with your PDF file path
			System.out.println("--- attachment setPath: " + attachment.toString());
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			System.out.println("--- attachment setDisposition: " + attachment.toString());
			attachment.setDescription("PDF File");
			System.out.println("--- attachment setDescription: " + attachment.toString());
			attachment.setName("mohamed_said_software_resume.pdf");
			System.out.println("--- attachment setName: " + attachment.toString());

			while ((to_email = csvReader.readLine()) != null) {
				MultiPartEmail multiPartEmail = new MultiPartEmail();
				System.out.println("--- MultiPartEmail multiPartEmail: " + multiPartEmail.toString());
				multiPartEmail.setHostName("smtp.gmail.com");
				System.out.println("--- MultiPartEmail setHostName: " + multiPartEmail.toString());
				multiPartEmail.setSmtpPort(465);
				System.out.println("--- MultiPartEmail setSmtpPort: " + multiPartEmail.toString());
				multiPartEmail.setAuthenticator(new javax.mail.Authenticator() {
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication(senderEmail, password);
					}
				});
				multiPartEmail.setSSLOnConnect(true);
				System.out.println("--- MultiPartEmail setSSLOnConnect: " + multiPartEmail.toString());
				multiPartEmail.setFrom(senderEmail);
				System.out.println("--- MultiPartEmail setFrom: " + multiPartEmail.toString());
				multiPartEmail.setSubject("Apply for Software Test Engineer");
				System.out.println("--- MultiPartEmail setSubject: " + multiPartEmail.toString());
				multiPartEmail.setMsg(messageContent.toString());
				System.out.println("--- MultiPartEmail setMsg: " + multiPartEmail.toString());
				multiPartEmail.attach(attachment);
				System.out.println("--- MultiPartEmail attach(attachment: " + multiPartEmail.toString());

				// Send email
				multiPartEmail.addTo(to_email);
				System.out.println("---Email added successfully to: multiPartEmail: " + to_email);
				multiPartEmail.send();
				System.out.println("---Email sent successfully to: " + to_email);
				Thread.sleep(1000);
			}
			csvReader.close();
			System.out.println("--- All Emails are sent successfully.");
		} catch (IOException | EmailException e) {
			e.printStackTrace();
		}
	}
}
