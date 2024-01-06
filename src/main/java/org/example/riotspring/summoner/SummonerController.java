package org.example.riotspring.summoner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SummonerController {
    @GetMapping("/summoner")
    public String summoner(){
        return "summoner";
    }
}
