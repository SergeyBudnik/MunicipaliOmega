package acropollis.municipali.omega.tests.steps.admin;

import acropollis.municipali.omega.admin.data.dto.customer.CustomerCredentials;
import acropollis.municipali.omega.admin.data.dto.customer.CustomerToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class AdminLoginSteps {
    private ObjectMapper objectMapper = new ObjectMapper();

    public CustomerToken login(MockMvc mockMvc, String login, String password) throws Exception {
        CustomerCredentials customerCredentials = new CustomerCredentials(); {
            customerCredentials.setLogin(login);
            customerCredentials.setPassword(password);
        }

        String resultJson = mockMvc.perform(
                post("/login")
                    .content(objectMapper.writeValueAsString(customerCredentials))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        return objectMapper.readValue(resultJson, CustomerToken.class);
    }

    public void failedLogin(MockMvc mockMvc, String login, String password) throws Exception {
        CustomerCredentials customerCredentials = new CustomerCredentials(); {
            customerCredentials.setLogin(login);
            customerCredentials.setPassword(password);
        }

        mockMvc.perform(
                post("/login")
                        .content(objectMapper.writeValueAsString(customerCredentials))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isForbidden());
    }
}
