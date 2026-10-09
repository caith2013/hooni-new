package com.hooni.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class IpService {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String getPublicIp() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.ipify.org"))
                .GET()
                .build();

        return httpClient.send(request,
                        HttpResponse.BodyHandlers.ofString())
                .body()
                .trim();
    }
}
