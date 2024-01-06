package org.example.riotspring.entries;

import lombok.Data;

import java.util.List;

@Data
class MiniSeriesDTO{
private Integer losses;
private String progress;
private Integer target;
private Integer wins;
}

@Data
public class LeagueEntryDTO {
 private String leagueId;
 private String summonerId;
 private String summonerName;
 private String queueType;
 private String tier;
 private String rank;
 private Integer leaguePoints;
 private Integer wins;
 private Integer losses;
 private Boolean hotStreak;
 private Boolean veteran;
 private Boolean freshBlood;
 private Boolean inactive;
 private List<MiniSeriesDTO> miniSeries;
}
