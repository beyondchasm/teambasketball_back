package site.beyondchasm.teambasketball.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 예시 경로, 실제 경로로 수정 필요
		registry.addResourceHandler("/common/images/profile/**")
				.addResourceLocations("file:/Users/createogu/teambasketball/upload/");
	}
}