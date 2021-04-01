package mainPackage.web;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class RSMErrorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    HttpServletRequest request;

    @Test
    @WithMockUser(username = "Ivan")
    void handleError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/abc"))
                .andExpect(status().is4xxClientError());
    }
    @Test
    @WithMockUser(username = "Ivan", roles = {"FRONT_OFFICE"})
    void handleErrorWithInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/front-office/pay-order/{id}",25L))
                .andExpect(status().is4xxClientError());
    }
    @Test
    @WithMockUser(username = "Ivan", roles = {"FRONT_OFFICE"})
    void handleErrorNotAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/back-office/not-fixed/{id}",1L))
                .andExpect(status().is4xxClientError());
    }
}