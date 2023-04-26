package com.example.petMitsuceta;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
@RestController
@RequestMapping("/api")
public class getInfo {

    @Value("${api.service-key}")
    private String serviceKey; // application.yml 파일에서 값을 가져옴

    @PostMapping("/abandonment")
    public ResponseEntity<?> getAbandonmentList(
            @RequestParam("keyword") String keyword,
            @RequestParam("where") String where
    ) throws UnsupportedEncodingException {
        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // 요청 URL과 파라미터 설정
        String url = "http://api.kcisa.kr/openapi/service/rest/convergence2019/getConver03";
        String numOfRows = "100";
        String pageNo = "10";


        // URL 파라미터 설정
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", pageNo)
                .queryParam("keyword", keyword)
                .queryParam("where", where);


        // HTTP GET 요청 보내기
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(), HttpMethod.GET, entity, String.class);

        // 응답 결과 반환
        return ResponseEntity.status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody());
    }
}
