package com.example.whistle.service;

import com.example.whistle.component.TokenConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GitHubApiService {
    private final TokenConfig tokenConfig;
    private static final String GITHUB_API_BASE_URL = "https://api.github.com";

    @Autowired
    private RestTemplate restTemplate;

    private ResponseEntity<String> createResponse(String url,String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    public String getOrganizationMembers(String orgName, String token) {
        String url = GITHUB_API_BASE_URL + "/orgs/" + orgName + "/members";
        return createResponse(url,tokenConfig.getBot().getToken()).getBody();
    }

    public String getOrganizationRepos(String orgName, String token) {
        String url = GITHUB_API_BASE_URL + "/orgs/" + orgName + "/repos";
        return createResponse(url,tokenConfig.getBot().getToken()).getBody();
    }

    public String getPullRequests(String orgName, String repoName, String token) {
        String url = GITHUB_API_BASE_URL + "/repos/" + orgName + "/" + repoName + "/pulls";
        return createResponse(url,tokenConfig.getBot().getToken()).getBody();
    }

    public String getPullRequestComments(String orgName, String repoName, int pullRequestId, String token) {
        String url = GITHUB_API_BASE_URL + "/repos/" + orgName + "/" + repoName + "/issues/" + pullRequestId + "/comments";
        return createResponse(url,tokenConfig.getBot().getToken()).getBody();
    }
}
