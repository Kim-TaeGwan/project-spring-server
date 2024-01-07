package org.example.riotspring.riot.lol.summoner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.riotspring.riot.RiotAPIService;
import org.example.riotspring.riot.RiotApiDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summoners")
public class SummonerController {

    private final RiotAPIService riotAPIService;
    private final ObjectMapper objectMapper;

    public SummonerController(RiotAPIService riotAPIService, ObjectMapper objectMapper) {
        this.riotAPIService = riotAPIService;
        this.objectMapper = objectMapper; // JSON 데이터를 객체로 변환하거나 객체를 JSON 으로 직렬화 하는데 사용.
    }

    @GetMapping("/by-name")
    public ResponseEntity<RiotApiDTO<SummonerDTO>> summonerData(@RequestParam("name") String summonerName) {
        String response = riotAPIService.callRiotAPI("/lol/summoner/v4/summoners/by-name/" + summonerName);
        System.out.println("summonerData: " + response);

        if (response == null || response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.readTree(response);
            RiotApiDTO<SummonerDTO> responseDTO = new RiotApiDTO<>();

            if (responseNode.has("status")) {
                JsonNode status = responseNode.get("status");
                String errorMessage = status.get("message").asText();
                int statusCode = status.get("status_code").asInt();

                System.out.println("API Error: " + errorMessage);
                responseDTO.setStatusCode(statusCode);
                responseDTO.setMessage(errorMessage);
            } else {
                // 성공적인 응답 처리
                SummonerDTO summonerDTO = objectMapper.convertValue(responseNode, SummonerDTO.class);
                responseDTO.setStatusCode(200);
                responseDTO.setData(summonerDTO);
            }

            return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
        } catch (Exception e) {
            // JSON 파싱 예외 처리
            System.out.println("JSON Parsing Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}



