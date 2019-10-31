package com.tele.community.community.controller;

import com.tele.community.community.dto.AccessTokenDTO;
import com.tele.community.community.dto.GithubUser;
import com.tele.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")//@Value注解会去从配置文件中查找匹配的value属性值
    private String clientId;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Value("${github.client.secret}")
    private String clientSecret;

    @GetMapping("/callback")
    public String callback(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "state") String state
    ){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.githubUser(accessToken);
        System.out.println(githubUser.getName());
        return "index";
    }
}
