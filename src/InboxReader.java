import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class InboxReader {

	public static void main(String args[]) {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", "<username>",
					"<password>");
			System.out.println(store);

			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			Message messages[] = inbox.getMessages();
			for (Message message : messages) {
				printContent(message.getContent());
				System.out.println(message.getFileName());
			}
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (MessagingException e) {
			e.printStackTrace();
			System.exit(2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void printContent(Object content) throws MessagingException, IOException {
		// TODO Auto-generated method stubObject content = message.getContent();
		if (content instanceof Multipart) {
		    Multipart mp = (Multipart) content;
		    for (int i = 0; i < mp.getCount(); i++) {
		        BodyPart bp = mp.getBodyPart(i);
		        if (Pattern
		                .compile(Pattern.quote("text/html"),
		                        Pattern.CASE_INSENSITIVE)
		                .matcher(bp.getContentType()).find()) {
		            // found html part
		            System.out.println((String) bp.getContent());
		        } else {
		            // some other bodypart...
		        }
		    }
		}
		
	}

}