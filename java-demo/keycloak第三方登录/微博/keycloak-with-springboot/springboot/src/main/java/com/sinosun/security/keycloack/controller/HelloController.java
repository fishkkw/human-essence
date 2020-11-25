package com.sinosun.security.keycloack.controller;

import com.sinosun.security.keycloack.service.HelloService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class HelloController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    HelloService helloService;

    @RequestMapping("/")
    public String indexPage(Principal principal, ModelMap map) {
        map.addAttribute("host", "www.baidu.com");
        if (principal != null){
            map.addAttribute("user", principal.getName());
        }
        return "index";
    }

    @RequestMapping("/customer")
    public String customer(Principal principal, ModelMap map) {

        for (String key : map.keySet()){
            System.out.println(key);
        }
        System.out.println("/customer");
        map.addAttribute("role", "customer");
        map.addAttribute("name", principal.getName());
        return "customer";
    }

    /**
     * POST http://localhost:8080/auth/realms/<my_realm>/protocol/openid-connect/logout
     Authorization: Bearer <access_token>
     Content-Type: application/x-www-form-urlencoded

     client_id=<my_client_id>&refresh_token=<refresh_token>
     * @param principal
     * @param map
     * @return
     */
    @RequestMapping("/logout")
    public String logout(Principal principal, ModelMap map) {

        if (principal == null || !(principal instanceof KeycloakPrincipal)){
            return indexPage(principal,  map);
        }
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal)principal;
        RefreshableKeycloakSecurityContext securityContext = (RefreshableKeycloakSecurityContext)keycloakPrincipal.getKeycloakSecurityContext();
//        String accessToken = securityContext.getTokenString();
//        String refreshToken = securityContext.getRefreshToken();

        boolean result = helloService.logout(securityContext);

        map.addAttribute("result", result ? "logout success" : "logout failure");
        return "logout";
    }

    @RequestMapping("/admin")
    public String admin(ModelMap map) {
        map.addAttribute("role", "admin");
        return "admin";
    }
}
