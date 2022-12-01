package team017.mail;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/* 메일 보내는게 길어지기 때문에 비동기 처리 */
@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5); /* 기본적으로 실행 대기 중인 스레드 개수 */
		executor.setMaxPoolSize(10); /* 동시에 동작하는 최대 스레드 개수 */
		executor.setQueueCapacity(500); /* CorePool 이 초과될때 Queue 에 저장했다가 꺼내서 실행 */
		executor.setThreadNamePrefix("async-");
		executor.initialize();
		return executor;
	}
}
