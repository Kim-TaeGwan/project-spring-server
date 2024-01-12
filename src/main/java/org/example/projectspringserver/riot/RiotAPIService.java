package org.example.projectspringserver.riot;

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
    @Value("${riot.api.key}")
    private final String apiKey;
    @Value("${riot.api.baseUrl}")
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
            // getForObject 는 RestTemplate 의 메서드 중 하나로, 지정된 URL로 HTTP GET 요청을 보내고 헤당 요청에 대한 응답을 받아오는 역할을 한다.
            // String.class 는 getForObject 메소드에서 반환할 응답 데이터의 형식을 지정하는데 사용된다.
            // 여기서는 API 응답이 문자열로 예상되므로 String.class 를 사용하여 응답 데이터를 문자열로 변환한다.
            return restTemplate.getForObject(apiUrl, String.class); // GET 요청을 수행하고 응답을 문자열로 변환
        }
        catch (HttpClientErrorException | HttpServerErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            return errorMessage; // 에러 메시지 반환
        } catch (Exception e) {
            // 기타 예외 처리
            throw new RuntimeException("서버 오류 발생: " + e.getMessage(), e);
        }

    }
}
