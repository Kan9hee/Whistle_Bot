package com.example.whistle;

import com.example.whistle.component.GitHubWebhookComponent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.example.whistle")
public class WhistleApplication {
	private final GitHubWebhookComponent webhookComponent;
	public static void main(String[] args) {
		SpringApplication.run(WhistleApplication.class, args);
	}

	@PostConstruct
	public void init() {
		webhookComponent.start();
	}
}
