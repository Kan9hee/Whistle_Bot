package com.example.whistle.component;

import com.example.whistle.service.DiscordService;
import io.undertow.Undertow;
import io.undertow.util.Headers;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GitHubWebhookComponent {
    private final DiscordService discordService;

    public void start() {
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "0.0.0.0")
                .setHandler(exchange -> {
                    if (exchange.getRequestMethod().toString().equals("POST")) {
                        exchange.getRequestReceiver().receiveFullString((ex, message) -> {
                            String event = exchange.getRequestHeaders().getFirst("X-GitHub-Event");
                            processGitHubEvent(message, event);
                            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                            exchange.getResponseSender().send("Webhook received");
                        });
                    } else {
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        exchange.getResponseSender().send("Use POST method");
                    }
                }).build();
        server.start();
    }

    private void processGitHubEvent(String payload, String event) {
        JSONObject json = new JSONObject(payload);
        String message = "";

        switch (event) {
            case "push":
                message = handlePushEvent(json);
                break;
            case "pull_request":
                message = handlePullRequestEvent(json);
                break;
            // 추가적인 이벤트 처리
        }

        discordService.sendMessageToDiscordChannel(message);
    }

    private String handlePushEvent(JSONObject json) {
        String repoName = json.getJSONObject("repository").getString("name");
        String pusher = json.getJSONObject("pusher").getString("name");
        int commitCount = json.getJSONArray("commits").length();
        return String.format("Repository %s has received %d new commits from %s.", repoName, commitCount, pusher);
    }

    private String handlePullRequestEvent(JSONObject json) {
        String repoName = json.getJSONObject("repository").getString("name");
        String action = json.getString("action");
        String prTitle = json.getJSONObject("pull_request").getString("title");
        return String.format("Pull request '%s' in repository %s has been %s.", prTitle, repoName, action);
    }
}
