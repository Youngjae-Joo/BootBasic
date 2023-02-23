package com.simple.basic.config;

import java.util.Arrays;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//
//import com.simple.basic.controller.HomeController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.simple.basic.util.UserAuthHandler;
import com.simple.basic.util.UserSuccessHandler;

@Configuration //개별적인 스프링 빈 설정 파일
public class WebConfig implements WebMvcConfigurer{
	
//	//빈을 보관하고 있는 장소(스프링 컨테이너)
//	@Autowired
//	ApplicationContext applicationContext;
//	
//	//properties파일에 선언된 변수를 바로 참조
//	@Value("${server.port}")
//	String port;
//	
//	@Bean //해당 메서드 실행하게됨.
//	public void test() {
//		
//		//생성한 빈
//		TestBean t = applicationContext.getBean(TestBean.class);
//		System.out.println(t);
//		t.hello();
//		
//		//컨트롤러
//		HomeController h = applicationContext.getBean(HomeController.class);
//		System.out.println(h);
//		
//		//가지고 있는 빈의 갯수
//		int c=applicationContext.getBeanDefinitionCount();
//		System.out.println("빈의 개수:"+c);
//		
//		//application.properties파일에 선언된 값
//		System.out.println("properties파일에 선언된 값: "+port);
//	}
//	
//	
//	@Bean //해당 메서드 실행하게됨
//	public TestBean testBean() {
//		System.out.println("테스트 빈 실행됨2");
//		return new TestBean();//빈으로 등록
//	}
	
	@Bean
	public UserAuthHandler userAuthHandler() {
		return new UserAuthHandler(); //빈으로 등록
	}
	
	@Bean
	public UserSuccessHandler userSuccessHandler() {
		return new UserSuccessHandler();
	}

	//webMvcConfigurer가 제공해주는 인터셉터 추가 메서드 오버라이딩
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userAuthHandler())//반환이 자기 자신. 부가적인 설정들은 .~로 계속 연결할 수 있음.
		.addPathPatterns("/user/*")//패스경로포함
		.excludePathPatterns("/user/login");//패스경로제외
		//.addPathPatterns("경로").addPathPatterns("경로")로 계속 추가할 수 있다.
		//.addPathPatterns(Arrays.asList("경로","경로","경로"))
		
		
		//경로별로 인터셉트를 다르게 등록...
		//registry.addInterceptor();
		
		registry.addInterceptor(userSuccessHandler()).addPathPatterns("/user/*");
		
	}
	
	
	
	
}
