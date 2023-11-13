package com.example.trip_itinerary.itinerary.service;

import com.example.trip_itinerary.itinerary.dto.data.KakaoAddress;
import com.example.trip_itinerary.itinerary.dto.response.KakaoAddressResponse;
import com.example.trip_itinerary.itinerary.exception.KakaoApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
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
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        URI url = UriComponentsBuilder.fromUriString(kakaoUrl)
                .queryParam("query", encodedQuery)
                .build()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        RequestEntity<Void> requestEntity = RequestEntity.get(url).headers(headers).build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new KakaoApiException("Kakao API request failed with status code: " + responseEntity.getStatusCode());
            // 테스트 필요
        }

        return new KakaoAddressResponse(getAddressList(responseEntity.getBody()));
    }
}
