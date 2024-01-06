package org.example.riotspring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RiotAPIService {
    // RestTemplate 은 HTTP 클라이언트 라이브러리로, 외부 API 와 통신을 단순화하고 쉽게 수행할 수 있게 해준다.
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String riotApiBaseUrl;

    // RestTemplateBuilder 는 RestTemplate 을 더 쉽게 구성하고 생성하는 데 사용되는 빌더 클래스이다.
    public RiotAPIService(RestTemplateBuilder restTemplateBuilder, @Value("${riot.api.key}") String apiKey, @Value("${riot.api.baseUrl}") String riotApiBaseUrl){
        this.restTemplate = restTemplateBuilder.build();
        this.apiKey = apiKey;
        this.riotApiBaseUrl=riotApiBaseUrl;
    }

    public String callRiotAPI(String url) {
        try {
            String apiUrl = riotApiBaseUrl + url + "?api_key=" + apiKey;
            return restTemplate.getForObject(apiUrl, String.class);
        }
        catch (HttpClientErrorException | HttpServerErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            System.out.println("Error Message: " + errorMessage);
            return errorMessage; // 에러 메시지 반환
        } catch (Exception e) {
            // 기타 예외 처리
            throw new RuntimeException("서버 오류 발생: " + e.getMessage(), e);
        }

    }
}
