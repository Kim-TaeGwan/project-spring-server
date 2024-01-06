package org.example.riotspring.summoner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.riotspring.RiotAPIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summoners")
public class SummonerController {

    private final RiotAPIService riotAPIService;
    private final ObjectMapper objectMapper;

    public SummonerController(RiotAPIService riotAPIService, ObjectMapper objectMapper){
        this.riotAPIService = riotAPIService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/by-name")
    public ResponseEntity<SummonerDTO> summonerData(@RequestParam("name") String summonerName) {
        String response = riotAPIService.callRiotAPI("/lol/summoner/v4/summoners/by-name/"+ summonerName);
        System.out.println("summonerData : " + response);
        if (response != null) {
            try {
                SummonerDTO summonerDTO = objectMapper.readValue(response, SummonerDTO.class);
                return ResponseEntity.ok(summonerDTO);
            } catch (Exception e) {
                // JSON 파싱 예외 처리
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            // 다른 상태 코드 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
