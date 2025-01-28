package auth.server.controllers;

import auth.server.DTO.RegistrationRequest;
import auth.server.DTO.RegistrationResponse;
import auth.server.entities.Role;
import auth.server.services.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RegistrationController.class)
@AutoConfigureMockMvc
@WithMockUser(roles = "DEFAULT")
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegistrationService registrationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testRegister() throws Exception {

        RegistrationRequest mockRequest = new RegistrationRequest();
        mockRequest.setName("John");
        mockRequest.setSurname("Doe");
        mockRequest.setEmail("john.doe@example.com");
        mockRequest.setPassword("password123");
        mockRequest.setRole(Role.DEFAULT);

        RegistrationResponse mockResponse = new RegistrationResponse("John", "Doe", "john.doe@example.com", Role.DEFAULT);

        when(registrationService.register(
                mockRequest.getName(),
                mockRequest.getSurname(),
                mockRequest.getEmail(),
                mockRequest.getPassword(),
                mockRequest.getRole()
        )).thenReturn(mockResponse);

        mockMvc.perform(post("/api/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.role").value("DEFAULT"));
    }
}