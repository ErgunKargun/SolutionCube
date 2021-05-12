package com.solutioncube.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoClientConfig {

	@Value("${config.mongoDbUri}")	 
	private String connString;
	
    public @Bean MongoClient mongoClient() {
     	
    	connString = System.getenv("MONGODB_URI"); // Production
    	return MongoClients.create(connString); 
    }
}