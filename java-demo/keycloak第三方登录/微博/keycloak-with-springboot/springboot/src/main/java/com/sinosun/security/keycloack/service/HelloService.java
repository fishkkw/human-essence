package com.sinosun.security.keycloack.service;

import com.alibaba.fastjson.JSONObject;
import com.sinosun.security.keycloack.config.BaseConfig;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class HelloService {

    @Autowired
    BaseConfig baseConfig;

    public boolean logout(RefreshableKeycloakSecurityContext securityContext){
        if (securityContext == null){
            return false;
        }
        //请求路径
        String url = "http://localhost:8080/auth/realms/sinosun/protocol/openid-connect/logout";
        //使用Restemplate来发送HTTP请求
        RestTemplate restTemplate = new RestTemplate();
        // json对象
        JSONObject jsonObject = new JSONObject();

        /**
         *      * POST http://localhost:8080/auth/realms/<my_realm>/protocol/openid-connect/logout
         Authorization: Bearer <access_token>
         Content-Type: application/x-www-form-urlencoded

         client_id=<my_client_id>&refresh_token=<refresh_token>
         */
        // LinkedMultiValueMap 有点像JSON，用于传递post数据，网络上其他教程都使用
        // MultiValueMpat<>来传递post数据
        // 但传递的数据类型有限，不能像这个这么灵活，可以传递多种不同数据类型的参数
        LinkedMultiValueMap body=new LinkedMultiValueMap();
        body.add("client_id", baseConfig.getClientId());
        body.add("client_secret", baseConfig.getClientSecret());
        body.add("refresh_token", securityContext.getRefreshToken());

        //设置请求header 为 APPLICATION_FORM_URLENCODED
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        headers.setBearerAuth(securityContext.getTokenString());

        // 请求体，包括请求数据 body 和 请求头 headers
        HttpEntity httpEntity = new HttpEntity(body,headers);


        try {
            //使用 exchange 发送请求，以String的类型接收返回的数据
            //ps，我请求的数据，其返回是一个json
            ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            //解析返回的数据(实际返回的状态码204：表示成功，但不会返回任何数据。如果是浏览器，则告诉当前页面不会发生变化。其实对用户不友好，需要开发人员手动提示或跳转到登出页面)
            System.out.println(strbody.getStatusCode());
            return true;

        }catch (Exception e){
            System.out.println(e);
        }
        return  false;
    }
}
