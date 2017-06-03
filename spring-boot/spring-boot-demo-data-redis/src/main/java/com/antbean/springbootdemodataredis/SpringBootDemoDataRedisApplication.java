package com.antbean.springbootdemodataredis;

import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class SpringBootDemoDataRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoDataRedisApplication.class, args);
	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
//			throws UnknownHostException {
//		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
//		template.setConnectionFactory(redisConnectionFactory);
//		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//		ObjectMapper om = new ObjectMapper();
//		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//		jackson2JsonRedisSerializer.setObjectMapper(om);
//		template.setValueSerializer(new StringRedisSerializer());
//		template.afterPropertiesSet();
//		return template;
//	}
}
