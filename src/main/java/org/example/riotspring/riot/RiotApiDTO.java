package org.example.riotspring.riot;

import lombok.Data;

@Data
public class RiotApiDTO<T> {
    private int statusCode;
    private T data;
    private String message;
}
