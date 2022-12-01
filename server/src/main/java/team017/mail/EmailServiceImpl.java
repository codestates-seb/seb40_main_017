package team017.mail;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;

import lombok.RequiredArgsConstructor;
import team017.member.entity.Member;
import team017.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

	@Autowired
	JavaMailSender emailSender;
	private final MemberService memberService;
	private final TemplateEngine templateEngine;


	private MimeMessage createMessage(String to)throws Exception{
		System.out.println("보내는 대상 : "+ to);

		MimeMessage  message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
		Context context = new Context();
		helper.setTo(to); /* 보내는 대상 */
		helper.setSubject("Welcome to sign up 17 Farm!"); /* 메일 제목 */
		String html = templateEngine.process("mail", context);
		helper.setText(html, true);
		helper.setFrom("snakellie97@gmail.com", "17시 내고향"); /* 보내는 사람 */

		return message;
	}


	@Override
	@Async
	public String sendSimpleMessage(String to)throws Exception {
		MimeMessage message = createMessage(to);
		try{
			emailSender.send(message);
		}catch(MailException es){
			es.printStackTrace();
			throw new IllegalArgumentException();
		}
		return to;
	}
}
