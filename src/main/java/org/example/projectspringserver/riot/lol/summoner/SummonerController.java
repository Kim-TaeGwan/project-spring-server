package org.example.projectspringserver.riot.lol.summoner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.projectspringserver.riot.RiotAPIService;
import org.example.projectspringserver.riot.RiotApiDTO;
import org.example.projectspringserver.riot.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/riot/summoners")
public class SummonerController extends BaseController<SummonerDTO> {
    public SummonerController(RiotAPIService riotAPIService, ObjectMapper objectMapper) {
        super(riotAPIService, objectMapper);
    }

    @GetMapping("/by-name")
    public ResponseEntity<RiotApiDTO<SummonerDTO>> summonerData(@RequestParam("name") String summonerName) {
        String apiPath = "/lol/summoner/v4/summoners/by-name/" + summonerName;
        return fetchData(apiPath);
    }
}



