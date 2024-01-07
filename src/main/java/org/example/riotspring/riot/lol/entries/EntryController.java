package org.example.riotspring.riot.lol.entries;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.riotspring.riot.RiotAPIService;
import org.example.riotspring.riot.RiotApiDTO;
import org.example.riotspring.riot.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.example.riotspring.riot.lol.summoner.entries.LeagueEntryDTO;

@RestController
@RequestMapping("/api/entries")
public class EntryController extends BaseController<LeagueEntryDTO[]> {
    public EntryController(RiotAPIService riotAPIService, ObjectMapper objectMapper) {
        super(riotAPIService, objectMapper);
    }

    @GetMapping("/by-summoner")
    protected ResponseEntity<RiotApiDTO<LeagueEntryDTO[]>> fetchData(@RequestParam("id") String id) {
        String apiPath = "/lol/league/v4/entries/by-summoner/" + id;
        return super.fetchData(apiPath);
    }
}