package com.example.demo.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class PaymentController {


    @PostMapping("token")
    public String getAccessToken(@RequestBody PaymentRequestDto.getToken tokenData)  {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.iamport.kr/users/getToken";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentRequestDto.getToken> request = new HttpEntity<>(tokenData, headers);
        System.out.println(tokenData + " : tokenData 직렬화하지않음 ");


        ResponseEntity<IamportResponseDto> response = restTemplate.postForEntity(url, request, IamportResponseDto.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            System.out.println("토큰발급완료");
            return response.getBody().response().access_token();
        } else {
            throw new RuntimeException("액세스 토큰을 받아오는데 실패했습니다. 상태 코드: " + response.getStatusCode());
        }
    }

    // 보내드린 사진에있는 직접 직렬화 한 요청과 자동직렬화 동시에 보내는 로직입니다.

    @PostMapping("/api/payment/token")
    public String getToken(@RequestBody PaymentRequestDto.getToken tokenData) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper object = new ObjectMapper();
        String url = "https://api.iamport.kr/users/getToken";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        String requestData = object.writeValueAsString(tokenData);
        HttpEntity<String> request1 = new HttpEntity<>(requestData, headers);
        HttpEntity<PaymentRequestDto.getToken> request = new HttpEntity<>(tokenData, headers);
        System.out.println(request1 + " request1");
        System.out.println(request + " :request");
        System.out.println(tokenData + " : tokenData 직렬화하지않음 ");
        System.out.println(object.writeValueAsString(tokenData) + " 토큰데이터 직렬화");
        System.out.println(requestData + " 직렬화 스트링타입으로 저장");

        ResponseEntity<IamportResponseDto> response1 = restTemplate.postForEntity(url, request1, IamportResponseDto.class);
        ResponseEntity<IamportResponseDto> response = restTemplate.postForEntity(url, request, IamportResponseDto.class);
        System.out.println(response1.getBody().response().access_token());

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            System.out.println("토큰발급완료");
            return response.getBody().response().access_token();
        } else {
            throw new RuntimeException("액세스 토큰을 받아오는데 실패했습니다. 상태 코드: " + response.getStatusCode());
        }
    }

}
