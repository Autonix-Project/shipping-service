package com.example.shipping.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// CORS는 api-gateway에서 일괄 처리
// 직접 호출 테스트 시에는 CorsRegistry addCorsMappings 추가
@Configuration
public class WebConfig implements WebMvcConfigurer {
}
