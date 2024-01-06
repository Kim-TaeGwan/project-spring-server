package org.example.riotspring;

import lombok.Data;

@Data
public class RiotApiDTO {
    private int statusCode;
    private Object data;
    private String message;
}
