package com.example.whistle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class DiscordService extends ListenerAdapter {
    private final JDA jda;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        User user = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();

        System.out.println(textChannel.getId());

        log.info("get message : " + message.getContentDisplay());

        if (user.isBot()) {
            return;
        } else if (message.getContentDisplay().equals("")) {
            log.info("디스코드 Message 문자열 값 공백");
        }

        String[] messageArray = message.getContentDisplay().split(",");

        if (messageArray[0].equalsIgnoreCase("!휘슬")) {
            String[] messageArgs = Arrays.copyOfRange(messageArray, 1, messageArray.length);

            for (String msg : messageArgs) {
                String returnMessage = createMessage(event, msg);
                textChannel.sendMessage(returnMessage).queue();
            }

        }
    }

    public void sendMessageToDiscordChannel(String message) {
        TextChannel channel = jda.getTextChannelById("YOUR_CHANNEL_ID");
        if (channel != null) {
            channel.sendMessage(message).queue();
        }
    }

    private String createMessage(MessageReceivedEvent event, String message) {
        User user = event.getAuthor();
        String returnMessage = "";

        switch (message) {
            case "프로젝트 등록" -> returnMessage = user.getName() + "안녕하세요! 좋은 하루 되세요";
            case "프로젝트 일정 정보" -> returnMessage = user.getName() + "안녕하세요! 좋은 하루 되세요";
            case "프로젝트 인원 정보" -> returnMessage = user.getAsMention() + "님 저는 Discord Bot 입니다.";
            case "1" -> returnMessage = user.getName() + " / 1번 옵션";
            case "2" -> returnMessage = user.getName() + " / 2번 옵션";
            default -> returnMessage = "올바르지 않은 명령어입니다.";
        }
        return returnMessage;
    }
}
