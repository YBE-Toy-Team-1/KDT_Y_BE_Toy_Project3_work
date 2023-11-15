package com.example.trip_itinerary.itinerary.service;

import com.example.trip_itinerary.itinerary.dto.response.AddressFindResponse;
import com.example.trip_itinerary.itinerary.exception.ItineraryErrorCode;
import com.example.trip_itinerary.itinerary.exception.KakaoApiException;
import com.example.trip_itinerary.itinerary.exception.ServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KakaoApiService {
//    @Value("${kakao.api.key}")
    private String kakaoApiKey;
//    @Value("${kakao.url.keyword}")
    private String kakaoUrl;

    public List<AddressFindResponse> getAddress(String keyword) {

        String encodedQuery = URLEncoder.encode(keyword, StandardCharsets.UTF_8);

        URI url = URI.create(kakaoUrl + "?query=" + encodedQuery);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        RequestEntity<Void> requestEntity = RequestEntity.get(url).headers(headers).build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new KakaoApiException(ItineraryErrorCode.API_REQUEST_FAILED); // 테스트 필요
        }

        List<AddressFindResponse> addressFindResponses = getAddressList(responseEntity.getBody());
        return addressFindResponses;
    }

    public List<AddressFindResponse> getAddressList(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException(ItineraryErrorCode.SERVER_ERROR);
        }

        return StreamSupport.stream(rootNode.path("documents")
                        .spliterator(), false)
                .map(node -> new AddressFindResponse(node.path("place_name")
                        .asText(), node.path("road_address_name").asText()))
                .collect(Collectors.toList());
    }

}
