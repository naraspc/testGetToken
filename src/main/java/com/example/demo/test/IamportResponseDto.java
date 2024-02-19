package com.example.demo.test;

public record IamportResponseDto(Integer code, String message, Response response
){
    public record Response(String access_token, long now, long expired_at
    ) {

    }
}