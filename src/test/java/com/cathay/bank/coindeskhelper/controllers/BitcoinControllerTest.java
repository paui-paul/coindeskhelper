package com.cathay.bank.coindeskhelper.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.cathay.bank.coindeskhelper.controllers.impl.BitcoinController;
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslation;
import com.cathay.bank.coindeskhelper.services.IBitcoinService;
import com.cathay.bank.coindeskhelper.utils.exceptions.BitcoinException;
import com.cathay.bank.coindeskhelper.vos.BitcoinLanguage;
import com.cathay.bank.coindeskhelper.vos.BitcoinTranslationSetting;
import com.fasterxml.jackson.databind.ObjectMapper;

class BitcoinControllerTest {
    @Mock
    private IBitcoinService bitcoinService;

    @InjectMocks
    private BitcoinController bitcoinController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bitcoinController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void testAddOrUpdateTranslation_Success() throws Exception {
        BitcoinTranslationSetting setting = new BitcoinTranslationSetting();
        setting.setCode("BTC");
        setting.setLanguage(BitcoinLanguage.EN);
        setting.setName("Bitcoin");
        setting.setDescription("A description");

        BitcoinTranslation translation = new BitcoinTranslation();
        translation.setCode("BTC");
        translation.setLanguage("EN");
        translation.setName("Bitcoin");
        translation.setDescription("A description");

        when(bitcoinService.addOrUpdateTranslation(any(BitcoinTranslationSetting.class)))
                .thenReturn(translation);

        mockMvc.perform(post("/api/bitcoin/translation/add-or-update")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(setting)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.result.code").value("BTC"))
                .andExpect(jsonPath("$.result.language").value("EN"))
                .andExpect(jsonPath("$.result.name").value("Bitcoin"))
                .andExpect(jsonPath("$.result.description").value("A description"));
    }

    @Test
    void testAddOrUpdateTranslation_BitcoinNotFound() throws Exception {
        BitcoinTranslationSetting setting = new BitcoinTranslationSetting();
        setting.setCode("BTC");
        setting.setLanguage(BitcoinLanguage.EN);
        setting.setName("Bitcoin");
        setting.setDescription("A description");

        when(bitcoinService.addOrUpdateTranslation(any(BitcoinTranslationSetting.class)))
                .thenThrow(new BitcoinException("code: BTC not exists"));

        mockMvc.perform(post("/api/bitcoin/translation/add-or-update")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(setting)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("code: BTC not exists"));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
