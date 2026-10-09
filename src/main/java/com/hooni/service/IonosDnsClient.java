package com.hooni.service;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class IonosDnsClient {

    private String cachedZoneId;
    private String cachedRecordId;

    private static final String BASE_URL =
            "https://api.hosting.ionos.com/dns/v1";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Value("${ionos.api-key}")
    private String apiKey;

    private HttpSession getSessionIfAvailable() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                return attributes.getRequest().getSession();
            }
        } catch (Exception e) {
            // No request context, session not available
        }
        return null;
    }

    public String getZoneId() throws IOException, InterruptedException {
        HttpSession session = getSessionIfAvailable();
        if (session != null && session.getAttribute("zoneId") != null) {
            return session.getAttribute("zoneId").toString();
        }
        if (cachedZoneId != null) {
            return cachedZoneId;
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.hosting.ionos.com/dns/v1/zones"))
                .header("X-API-Key", apiKey)
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();

        JsonNode zones =
                mapper.readTree(response.body());

        String zoneId = StreamSupport.stream(
                        zones.spliterator(), false)
                .filter(z -> "hoo-ni.com"
                        .equals(z.get("name").asText()))
                .findFirst()
                .map(z -> z.get("id").asText())
                .orElseThrow();

        cachedZoneId = zoneId;
        if (session != null) {
            session.setAttribute("zoneId", zoneId);
        }
        return zoneId;
    }

    public String getRecordId() throws IOException, InterruptedException {
        HttpSession session = getSessionIfAvailable();
        if (session != null && session.getAttribute("recordId") != null) {
            return session.getAttribute("recordId").toString();
        }
        if (cachedRecordId != null) {
            return cachedRecordId;
        }
        String zoneId = getZoneId();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://api.hosting.ionos.com/dns/v1/zones/" + zoneId))
                .header("X-API-Key", apiKey)
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();

        JsonNode zone =
                mapper.readTree(response.body());

        String recordId = StreamSupport.stream(
                        zone.get("records").spliterator(), false)
                .filter(r -> "A".equals(r.get("type").asText()))
                .filter(r -> "hoo-ni.com".equals(r.get("name").asText()))
                .findFirst()
                .map(r -> r.get("id").asText())
                .orElseThrow();

        cachedRecordId = recordId;
        if (session != null) {
            session.setAttribute("recordId", recordId);
        }
        return recordId;
    }

    public void updateIp(String hostName, String newIp)
            throws IOException, InterruptedException {

        String zoneId = getZoneId();
        String recordId = getRecordId();

        log.info("Updating DNS record for host: {}, new IP: {}, zoneId: {}, recordId: {}", hostName, newIp, zoneId, recordId);

        String payload = """
                {
                  "name":"%s",
                  "type":"A",
                  "content":"%s",
                  "ttl":3600,
                  "prio":0,
                  "disabled":false
                }
                """.formatted(hostName, newIp);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        BASE_URL +
                                "/zones/" + zoneId +
                                "/records/" + recordId))
                .header("X-API-Key", apiKey)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response =
                httpClient.send(request,
                        HttpResponse.BodyHandlers.ofString());

        log.info("Response status code: {}", response.statusCode());
        log.info("Response body: {}", response.body());
    }
}