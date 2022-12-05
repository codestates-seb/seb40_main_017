package team017.security.jwt.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import team017.global.Exception.ExceptionCode;
import team017.global.error.ErrorResponse;

@Slf4j
@Component
public class MemberAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
		HttpServletResponse response, AuthenticationException exception) throws IOException {
		log.error("# 로그인 인증 실패 : {}", exception.getMessage());
		sendErrorResponse(response);
	}

	private void sendErrorResponse(HttpServletResponse response) throws IOException {
		Gson gson = new Gson();
		ErrorResponse errorResponse = ErrorResponse.of(ExceptionCode.LOGIN_ERROR);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(errorResponse.getStatus());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
	}
}
