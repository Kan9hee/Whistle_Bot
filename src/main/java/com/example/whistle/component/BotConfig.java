package com.example.whistle.component;

import com.example.whistle.service.DiscordService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BotConfig {
    private final ApplicationContext applicationContext;

    @Bean
    public JDA jda() {
        TokenConfig tokenConfig = applicationContext.getBean(TokenConfig.class);
        return JDABuilder.createDefault(tokenConfig.getBot().getToken())
                .setActivity(Activity.playing("Bot online"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(applicationContext.getBean(DiscordService.class))
                .build();
    }

    @Bean
    public DiscordService discordService() {
        return new DiscordService(jda());
    }
}