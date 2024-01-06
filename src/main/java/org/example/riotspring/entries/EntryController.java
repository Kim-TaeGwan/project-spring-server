package org.example.riotspring.entries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.riotspring.RiotAPIService;
import org.example.riotspring.RiotApiDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/entries")
public class EntryController {
    private final RiotAPIService riotAPIService;

    public EntryController(RiotAPIService riotAPIService) {
        this.riotAPIService = riotAPIService;
    }

    @GetMapping("/by-summoner")
    public ResponseEntity<RiotApiDTO<LeagueEntryDTO[]>> entriesData(@RequestParam("id") String id) {
        String response = riotAPIService.callRiotAPI("/lol/league/v4/entries/by-summoner/" + id);
        System.out.println("entriesData : " + response);

        if (response == null || response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.readTree(response);
            RiotApiDTO<LeagueEntryDTO[]> responseDTO = new RiotApiDTO<>();

            if (responseNode.isArray()) {
                // JSON 데이터가 배열인 경우
                LeagueEntryDTO[] leagueEntryDTOs = objectMapper.readValue(response, LeagueEntryDTO[].class);
                responseDTO.setData(leagueEntryDTOs);
            } else if (responseNode.isObject()) {
                // JSON 데이터가 객체인 경우
                LeagueEntryDTO leagueEntryDTO = objectMapper.convertValue(responseNode, LeagueEntryDTO.class);
                responseDTO.setData(new LeagueEntryDTO[]{leagueEntryDTO});
            }

            responseDTO.setStatusCode(200);
            return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
        } catch (Exception e) {
            // JSON 파싱 예외 처리
            System.out.println("JSON Parsing Error : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
