package com.dm.DGCat.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.dm.DGCat.interceptor.LoginInterceptor;


@Configuration
class InterceptorConfiguration extends WebMvcConfigurerAdapter{

	@Bean
	public LoginInterceptor getLoginIntercepter() {
		return new LoginInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(getLoginIntercepter())
				.addPathPatterns("/**");
	}
}

