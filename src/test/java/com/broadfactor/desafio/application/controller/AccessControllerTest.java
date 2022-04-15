package com.broadfactor.desafio.application.controller;

import com.broadfactor.desafio.application.payload.UserProfile;
import com.broadfactor.desafio.application.payload.UserSummary;
import com.broadfactor.desafio.domain.security.UserPrincipal;
import com.broadfactor.desafio.domain.service.AccessInformationService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({
        AccessInformationService.class
})

@WebMvcTest(InformationController.class)
public class AccessControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccessInformationService accessInformationService;

    private final ObjectMapper objectMapper = getObjectMapper();

    @Test
    @WithMockUser(username = "alonso", roles = {"USER"})
    void testGetCurrentUser() throws Exception {
        UserPrincipal userPrincipal = new UserPrincipal(2L, "Alonso", "alonso", "alonso@gmail.com", "pass", "111111111111111", null);
        UserSummary applicationFormDb = objectMapper.readValue("{\r\n" +
                "    \"id\": 2L,\r\n" +
                "    \"username\": \"Teste nome\",\r\n" +
                "    \"name\": \"teste\",\r\n" +
                "    \"cnpj\": \"111111111111111\"\r\n" +
                "}", UserSummary.class);

        Mockito.when(accessInformationService.getCurrentUser(userPrincipal)).thenReturn(applicationFormDb);

        String uri = "/api/user/me";
        mockMvc.perform(get(uri).contentType(APPLICATION_JSON_UTF8).
                content("{\r\n" +
                        "    \"id\": 2L,\r\n" +
                        "    \"email\": \"alonso@gmail.com\",\r\n" +
                        "    \"name\": \"Alonso\",\r\n" +
                        "    \"password\": \"pass\"\r\n" +
                        "    \"cnpj\": \"111111111111111\"\r\n" +
                        "    \"authorities\":\"\",\r\n" +
                        "}")).andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "alonso", roles = {"USER"})
    void testGetUserProfile() throws Exception {
        UserProfile userProfile = new UserProfile(2L, "alonso", "Alonso", null);

        Mockito.when(accessInformationService.getUserProfile("alonso")).thenReturn(userProfile);

        String uri = "/api/user/alonso";
        mockMvc.perform(get(uri).contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

    protected ObjectMapper getObjectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        objectMapper.setDateFormat(df);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }
}
