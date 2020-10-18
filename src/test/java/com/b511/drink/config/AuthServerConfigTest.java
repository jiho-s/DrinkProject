package com.b511.drink.config;

import com.b511.drink.service.accounts.AccountService;
import com.b511.drink.service.dtos.AccountRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthServerConfigTest {


    @Autowired
    protected MockMvc mockMvc;


    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    AccountService accountService;

    @Autowired
    AppProperties appProperties;

    @Test
    @DisplayName("인증 토큰을 발급 받는 테스트")
    public void getAuthToken() throws Exception {

        //Given
        String email = "jiho2@email.com";
        String password = "jiho";
        AccountRequestDto jiho = AccountRequestDto.builder()
                .email(email)
                .password(password)
                .build();
        this.accountService.createAccount(jiho);


        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", email)
                .param("password", password)
                .param("grant_type", "password")

        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("access_token").exists());
    }
    @Test
    @DisplayName("토큰이 없으면 401 Unauthorized")
    public void createEvent_Unauthorized() throws Exception {

        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("토큰이 있으면 404")
    public void createEvent_404() throws Exception {

        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, gerBearerToken()))
                .andExpect(status().isNotFound());
    }

    private String gerBearerToken() throws Exception {

        return "Bearer" + getAccessToken();
    }

    private String getAccessToken() throws Exception {

        //Given


        String clientId = appProperties.getClientId();
        String clientSecret = appProperties.getClientSecret();
        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .param("username", appProperties.getAdminUsername())
                .param("password", appProperties.getAdminPassword())
                .param("grant_type", "password")
        );
        var responseBody = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(responseBody).get("access_token").toString();

    }
}