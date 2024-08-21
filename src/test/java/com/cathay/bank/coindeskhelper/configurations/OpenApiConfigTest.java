package com.cathay.bank.coindeskhelper.configurations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@ExtendWith(SpringExtension.class)
class OpenApiConfigTest {
    @Test
    void commonOpenAPI_shouldReturnConfiguredOpenAPI() {
        OpenApiConfig config = new OpenApiConfig();
        OpenAPI openAPI = config.commonOpenAPI();
        Info info = openAPI.getInfo();
        assertEquals(info.getTitle(), "Cathay Bank Coin Desk Helper");
        assertEquals(info.getVersion(), "1.0.0");
        assertEquals(info.getDescription(), "Cathay bank coin desk helper for interview");
    }
}
