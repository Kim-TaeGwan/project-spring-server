package org.example.riotspring.riot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController<T> {
    private final RiotAPIService riotAPIService;
    private final ObjectMapper objectMapper;

    public BaseController(RiotAPIService riotAPIService, ObjectMapper objectMapper) {
        this.riotAPIService = riotAPIService;
        this.objectMapper = objectMapper;
    }

    protected ResponseEntity<RiotApiDTO<T>> fetchData(String apiPath) {
        String response = riotAPIService.callRiotAPI(apiPath);
        System.out.println("fetchData: " + response);

        if (response == null || response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try {
            JsonNode responseNode = objectMapper.readTree(response);
            RiotApiDTO<T> responseDTO = new RiotApiDTO<>();

            if (responseNode.has("status")) {
                JsonNode status = responseNode.get("status");
                String errorMessage = status.get("message").asText();
                int statusCode = status.get("status_code").asInt();

                System.out.println("API Error: " + errorMessage);
                responseDTO.setStatusCode(statusCode);
                responseDTO.setMessage(errorMessage);
            } else {
                // 성공적인 응답 처리
                T data = objectMapper.convertValue(responseNode, new TypeReference<T>() {});
                responseDTO.setStatusCode(200);
                responseDTO.setData(data);
            }

            return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
        } catch (Exception e) {
            // JSON 파싱 예외 처리
            System.out.println("JSON Parsing Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
