package com.cathay.bank.coindeskhelper.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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
import com.cathay.bank.coindeskhelper.db.projections.BitCoinInfoByLanguage;
import com.cathay.bank.coindeskhelper.services.IBitcoinService;
import com.cathay.bank.coindeskhelper.utils.exceptions.BitcoinException;
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
    void testFindBitcoinByLanguage() throws Exception {
        // Arrange
        String language = "EN";

        BitCoinInfoByLanguage bitCoinInfo1 = new BitCoinInfoByLanguage() {
            @Override
            public String getCode() {
                return "BTC";
            }

            @Override
            public float getRateFloat() {
                return 30000.0f;
            }

            @Override
            public String getName() {
                return "Bitcoin";
            }

            @Override
            public String getDescription() {
                return "BTC Description";
            }

            @Override
            public LocalDateTime getUpdated() {
                return LocalDateTime.now();
            }
        };

        List<BitCoinInfoByLanguage> expected = Arrays.asList(bitCoinInfo1);
        when(bitcoinService.findBitCoinInfoByLanguage(language)).thenReturn(expected);

        mockMvc.perform(get("/api/bitcoin/EN")).andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].code").value("BTC"))
                .andExpect(jsonPath("$.result[0].rateFloat").value("30000.0"))
                .andExpect(jsonPath("$.result[0].name").value("Bitcoin"))
                .andExpect(jsonPath("$.result[0].description").value("BTC Description"));
    }

    @Test
    void testAddOrUpdateTranslation_Success() throws Exception {
        BitcoinTranslationSetting setting = new BitcoinTranslationSetting();
        setting.setCode("BTC");
        setting.setLanguage("EN");
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
    void testAddOrUpdateTranslation_BadRequest() throws Exception {
        BitcoinTranslationSetting setting = new BitcoinTranslationSetting();
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
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddOrUpdateTranslation_BitcoinNotFound() throws Exception {
        BitcoinTranslationSetting setting = new BitcoinTranslationSetting();
        setting.setCode("BTC");
        setting.setLanguage("EN");
        setting.setName("Bitcoin");
        setting.setDescription("A description");

        when(bitcoinService.addOrUpdateTranslation(any(BitcoinTranslationSetting.class)))
                .thenThrow(new BitcoinException("code: BTC not exists"));

        mockMvc.perform(post("/api/bitcoin/translation/add-or-update")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(setting)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("code: BTC not exists"));
    }

    @Test
    void testDeleteTranslation_Success() throws Exception {
        String code = "BTC";
        String language = "EN";
        when(bitcoinService.deleteTranslation(code, language)).thenReturn(true);

        mockMvc.perform(delete("/api/bitcoin/translation/{code}/{language}", code, language))
                .andExpect(status().isOk()).andExpect(jsonPath(".result").value(true));
    }

    @Test
    void testDeleteTranslation_NotFound() throws Exception {
        String code = "BTC";
        String language = "EN";
        when(bitcoinService.deleteTranslation(code, language)).thenReturn(false);

        mockMvc.perform(delete("/api/bitcoin/translation/{code}/{language}", code, language))
                .andExpect(status().isOk()).andExpect(jsonPath(".result").value(false));
    }

    @Test
    void testDeleteTranslation_CodeNotExists() throws Exception {
        String code = "BTC";
        String language = "EN";
        when(bitcoinService.deleteTranslation(code, language))
                .thenThrow(new BitcoinException("code: " + code + " not exists"));


        when(bitcoinService.addOrUpdateTranslation(any(BitcoinTranslationSetting.class)))
                .thenThrow(new BitcoinException("code: BTC not exists"));

        mockMvc.perform(delete("/api/bitcoin/translation/{code}/{language}", code, language))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("code: BTC not exists"));
    }

    @Test
    void delete_Success() throws Exception {
        String code = "BTC";
        when(bitcoinService.deleteBitcoin(code)).thenReturn(true);
        mockMvc.perform(delete("/api/bitcoin/{code}", code))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true));
    }

    @Test
    void delete_CodeNotExists() throws Exception {
        String code = "BTC";
        when(bitcoinService.deleteBitcoin(code)).thenThrow(new BitcoinException("code: BTC not exists"));
        mockMvc.perform(delete("/api/bitcoin/{code}", code))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("code: BTC not exists"));
    }

    @Test
    void delete_Internal_Server_Error() throws Exception {
        String code = "BTC";
        when(bitcoinService.deleteBitcoin(code)).thenThrow(new RuntimeException("Unexpected error"));
        mockMvc.perform(delete("/api/bitcoin/{code}", code))
                .andExpect(status().isInternalServerError());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
