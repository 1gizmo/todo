package com.example.todo.auth;

import com.example.todo.userapi.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
// 역활 : 토큰을 발급하고 , 서명명위조를 검사하는 객체
public class ToKenProvider {

    // 서명 사용할 값 (512바이트 이상의 랜덤 문자열)
   @Value("${jwt.secret}")
    private String SECRET_KEY;



   // 토큰 생성메서드
    public String createToken(User userEntity){

    /*
    *  {
    *       "iss" : "딸긔공듀",
    *       "exp" : "2023-07-12" ,
    *       "iat" : "2023-06-12" ,
    *       "email : "로그인한사람이메일",
    *       "role" : "Premium"
    * }
    * */
        // 토큰 만료시간 생성
        Date expiry = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS)
        );
        // 추가 클레임 정의
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userEntity.getEmail());
        claims.put("role", userEntity.getRole());

        //토큰 생성

        return Jwts.builder()
                // token header에 들어갈 서명
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes())
                        , SignatureAlgorithm.HS512
                )
                // token payload에 들어갈 클레임 설정
                .setIssuer("바닐라겅듀")    // iss : 발급자 정보
                .setIssuedAt(new Date()) // iat: 발급시간
                .setExpiration(expiry) // exp : 만료시간
                .setSubject(userEntity.getId()) // sub: 토큰을 식별할 수  있는 주요데이터
                .setClaims(claims)
                .compact();
    }

}
