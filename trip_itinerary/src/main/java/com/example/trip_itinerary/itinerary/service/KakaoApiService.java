package com.example.trip_itinerary.itinerary.service;

import com.example.trip_itinerary.itinerary.dto.response.KakaoAddressResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class KakaoApiService {
    @Value("${kakao.api.key}")
    private String kakaoApiKey;
    @Value("${kakao.url.keyword}")
    private String kakaoUrl;

    private final RestTemplate restTemplate;

    public KakaoApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public KakaoAddressResponse getAddressFromKakao(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            URI url = UriComponentsBuilder.fromUriString(kakaoUrl)
                    .queryParam("query", encodedQuery)
                    .build()
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + kakaoApiKey);

            RequestEntity<Void> requestEntity = RequestEntity.get(url).headers(headers).build();

            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseBody);

                List<String> roadAddressNames = objectMapper.convertValue(
                        rootNode.path("documents").findValuesAsText("road_address_name"),
                        List.class
                );

                KakaoAddressResponse addressResponse = new KakaoAddressResponse();
                addressResponse.setRoadAddressNames(roadAddressNames);

                return addressResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
