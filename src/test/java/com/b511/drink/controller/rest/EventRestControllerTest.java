package com.b511.drink.controller.rest;

import com.b511.drink.config.AppProperties;
import com.b511.drink.domain.accounts.AccountRepository;
import com.b511.drink.domain.events.Event;
import com.b511.drink.domain.events.EventRepository;
import com.b511.drink.service.accounts.AccountService;
import com.b511.drink.service.dtos.EventCreateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventRestControllerTest {


    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;


    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    AppProperties appProperties;

    @Autowired
    EventRepository eventRepository;

    @Test
    @DisplayName("이벤트 생성 테스트")
    public void createEvnet_Success() throws Exception {
        //given
        EventCreateRequestDto eventCreateRequestDto = EventCreateRequestDto.builder()
                .cup(3)
                .drinkDate(LocalDate.now())
                .drinkType(0)
                .memo("test")
                .build();


        mockMvc.perform(post("/api/event")
                .header(HttpHeaders.AUTHORIZATION, gerBearerToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(eventCreateRequestDto))
        )
                .andExpect(status().isCreated())
                .andDo(print());
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