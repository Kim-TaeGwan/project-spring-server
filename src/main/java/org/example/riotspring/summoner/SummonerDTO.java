package org.example.riotspring.summoner;

import lombok.Data;

@Data
public class SummonerDTO {
 private String id;
 private String accountId;
 private Integer profileIconId;
 private Long revisionDate;
 private String name;
 private String puuid;
 private Long summonerLevel;
}