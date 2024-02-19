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

}
