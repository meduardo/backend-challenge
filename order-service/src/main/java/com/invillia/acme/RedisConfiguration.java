package com.invillia.acme;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
@EnableConfigurationProperties
public class RedisConfiguration {

	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;
	
	@Value("${spring.redis.password}")
	private String redisPass;
	
	@Bean
	public RedisConnectionFactory connectionFactory(RedisStandaloneConfiguration configuration) {
		return new LettuceConnectionFactory(configuration);
	}
	
	@Bean
	public RedisTemplate<byte[], byte[]> redisTemplate(RedisConnectionFactory connectionFactory) {

		RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		return template;
	}
	
	@Bean
	public RedisStandaloneConfiguration redisStandaloneConfiguration() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
		configuration.setPassword(redisPass);
		return configuration;
	}

	//  TODO - Deixando sem config de pool por hora, precisa adicionar a dpeendencia do apache commons pool 2
	//	@Bean
	//	public ClientOptions clientOptions(){
	//	    return ClientOptions.builder()
	//				            .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
	//				            .autoReconnect(true)
	//				            .build();
	//	}
	
	//	@Bean
	//	public LettucePoolingClientConfiguration lettucePoolConfig(ClientOptions options){
	//	    return LettucePoolingClientConfiguration.builder()
	//									            .poolConfig(new GenericObjectPoolConfig())
	//									            .clientOptions(options)
	//									            .clientResources(DefaultClientResources.create())
	//									            .build();
	//	}
	//
	//	@Bean
	//	public RedisConnectionFactory connectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration,
	//	                                                LettucePoolingClientConfiguration lettucePoolConfig) {
	//	    return new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolConfig);
	//	}
}
