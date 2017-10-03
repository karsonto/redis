package com.karson.oauthdemo;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class OauthdemoApplication implements CommandLineRunner {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisTemplate<String, Author> template;
 

	public static void main(String[] args) {
		SpringApplication.run(OauthdemoApplication.class, args);
	}
	
	@Bean
	public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, T> template = new RedisTemplate<String, T>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}

	@Override
	public void run(String... args) throws Exception {
		Author author =  new Author();
		author.setId(1);
		author.setAge(123);
		author.setName("karson");
		template.opsForValue().set("karson", author);
	    System.out.println(	template.opsForValue().get("karson").getAge());
	    Set<String> a = stringRedisTemplate.keys("*");
		for(String ss:a) {
			System.out.println(ss);
			System.out.println(stringRedisTemplate.opsForValue().get("karson"));
		}
	}
}
