package team017.mail;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

	@Autowired
	JavaMailSender emailSender;

	private MimeMessage createMessage(String to, String name)throws Exception{
		System.out.println("보내는 대상 : "+ to);

		MimeMessage  message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
		helper.setTo(to); /* 보내는 대상 */
		helper.setSubject("Dear. " + name + "!"); /* 메일 제목 */
		String text = "<div style=\"position: center\" align=\"center\">"
			+ "<img src = \"https://jihoon-bucket.s3-ap-northeast-2.amazonaws.com/mail.png\" height=\"500px\">"
			+ "</div>";
		helper.setText(text, true);
		helper.setFrom("snakellie97@gmail.com", "17시 내고향"); /* 보내는 사람 */

		return message;
	}


	@Override
	@Async
	public String sendSimpleMessage(String to, String name)throws Exception {
		MimeMessage message = createMessage(to, name);
		try{
			emailSender.send(message);
		}catch(MailException es){
			es.printStackTrace();
			throw new IllegalArgumentException();
		}
		return to;
	}
}
