package org.devalurum.transactionsapp.controller;

import com.google.gson.Gson;
import org.devalurum.transactionsapp.service.AccountService;
import org.devalurum.transactionsapp.service.TransferService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@WebAppConfiguration
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    AccountService accountService;

    @MockBean
    @Qualifier("declarativeService")
    TransferService declarativeService;

    @MockBean
    @Qualifier("programmaticService")
    TransferService programmaticService;

    @MockBean
    @Qualifier("progTemplateService")
    TransferService progTemplateService;

    @MockBean
    @Qualifier("persistContextService")
    TransferService persistContextService;

    @Test
    void accountIfEmpty() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(Collections.emptyList());

        final String expectedJson = gson.toJson(Collections.emptyList());

        RequestBuilder requestBuilder = get("/api/v1/services/accounts/")
                .content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String actualJson = result.getResponse().getContentAsString();

        Assertions.assertEquals(expectedJson, actualJson);
    }
}
