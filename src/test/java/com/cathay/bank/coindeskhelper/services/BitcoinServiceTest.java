package com.cathay.bank.coindeskhelper.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslation;
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslationId;
import com.cathay.bank.coindeskhelper.db.projections.BitCoinInfoByLanguage;
import com.cathay.bank.coindeskhelper.db.repositories.IBitcoinRepo;
import com.cathay.bank.coindeskhelper.db.repositories.IBitcoinTranslationRepo;
import com.cathay.bank.coindeskhelper.services.impl.BitcoinService;
import com.cathay.bank.coindeskhelper.utils.exceptions.BitcoinException;
import com.cathay.bank.coindeskhelper.vos.BitcoinLanguage;
import com.cathay.bank.coindeskhelper.vos.BitcoinTranslationSetting;

class BitcoinServiceTest {
    @Mock
    private IBitcoinRepo bitcoinRepo;

    @Mock
    private IBitcoinTranslationRepo bitcoinTranslationRepo;

    @InjectMocks
    private BitcoinService bitcoinService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindBitCoinInfoByLanguage() {
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
        BitCoinInfoByLanguage bitCoinInfo2 = new BitCoinInfoByLanguage() {
            @Override
            public String getCode() {
                return "ETH";
            }

            @Override
            public float getRateFloat() {
                return 2000.0f;
            }

            @Override
            public String getName() {
                return "Ethereum";
            }

            @Override
            public String getDescription() {
                return "ETH Description";
            }

            @Override
            public LocalDateTime getUpdated() {
                return LocalDateTime.now();
            }
        };

        List<BitCoinInfoByLanguage> expected = Arrays.asList(bitCoinInfo1, bitCoinInfo2);

        when(bitcoinRepo.findBitcoinInfoByLanguage(language)).thenReturn(expected);
        List<BitCoinInfoByLanguage> actual = bitcoinService.findBitCoinInfoByLanguage(language);
        assertEquals(expected, actual);
    }

    @Test
    void testAddOrUpdateTranslation_NewTranslation() throws BitcoinException {
        when(bitcoinRepo.existsById("BTC")).thenReturn(true);

        when(bitcoinTranslationRepo.findById(any(BitcoinTranslationId.class)))
                .thenReturn(Optional.empty());

        BitcoinTranslationSetting setting = new BitcoinTranslationSetting();
        setting.setCode("BTC");
        setting.setLanguage(BitcoinLanguage.EN);
        setting.setName("Bitcoin");
        setting.setDescription("Description");

        BitcoinTranslation translation = new BitcoinTranslation();
        translation.setCode("BTC");
        translation.setLanguage("EN");
        translation.setName("Bitcoin");
        translation.setDescription("Description");
        translation.setCreatedTime(LocalDateTime.now());

        when(bitcoinTranslationRepo.save(any(BitcoinTranslation.class))).thenReturn(translation);

        BitcoinTranslation result = bitcoinService.addOrUpdateTranslation(setting);

        assertEquals("BTC", result.getCode());
        assertEquals("EN", result.getLanguage());
        assertEquals("Bitcoin", result.getName());
        assertEquals("Description", result.getDescription());

        verify(bitcoinRepo, times(1)).existsById("BTC");
        verify(bitcoinTranslationRepo, times(1)).findById(any(BitcoinTranslationId.class));
        verify(bitcoinTranslationRepo, times(1)).save(any(BitcoinTranslation.class));
    }

    @Test
    void testAddOrUpdateTranslation_UpdateExistingTranslation() throws BitcoinException {
        when(bitcoinRepo.existsById("BTC")).thenReturn(true);

        BitcoinTranslation existingTranslation = new BitcoinTranslation();
        existingTranslation.setCode("BTC");
        existingTranslation.setLanguage("EN");
        existingTranslation.setName("Old Bitcoin");
        existingTranslation.setDescription("Old Description");
        existingTranslation.setCreatedTime(LocalDateTime.now());

        when(bitcoinTranslationRepo.findById(any(BitcoinTranslationId.class)))
                .thenReturn(Optional.of(existingTranslation));

        BitcoinTranslationSetting setting = new BitcoinTranslationSetting();
        setting.setCode("BTC");
        setting.setLanguage(BitcoinLanguage.EN);
        setting.setName("Updated Bitcoin");
        setting.setDescription("Updated Description");

        existingTranslation.setName("Updated Bitcoin");
        existingTranslation.setDescription("Updated Description");
        existingTranslation.setUpdatedTime(LocalDateTime.now());

        when(bitcoinTranslationRepo.save(any(BitcoinTranslation.class)))
                .thenReturn(existingTranslation);

        BitcoinTranslation result = bitcoinService.addOrUpdateTranslation(setting);

        assertEquals("BTC", result.getCode());
        assertEquals("EN", result.getLanguage());
        assertEquals("Updated Bitcoin", result.getName());
        assertEquals("Updated Description", result.getDescription());

        verify(bitcoinRepo, times(1)).existsById("BTC");
        verify(bitcoinTranslationRepo, times(1)).findById(any(BitcoinTranslationId.class));
        verify(bitcoinTranslationRepo, times(1)).save(any(BitcoinTranslation.class));
    }

    @Test
    void testAddOrUpdateTranslation_BitcoinNotFound() {
        when(bitcoinRepo.existsById("BTC")).thenReturn(false);

        BitcoinTranslationSetting setting = new BitcoinTranslationSetting();
        setting.setCode("BTC");
        setting.setLanguage(BitcoinLanguage.EN);
        setting.setName("Bitcoin");
        setting.setDescription("Description");

        BitcoinException exception = assertThrows(BitcoinException.class, () -> {
            bitcoinService.addOrUpdateTranslation(setting);
        });

        assertEquals("code: BTC not exists", exception.getMessage());

        verify(bitcoinRepo, times(1)).existsById("BTC");
        verify(bitcoinTranslationRepo, times(0)).findById(any(BitcoinTranslationId.class));
        verify(bitcoinTranslationRepo, times(0)).save(any(BitcoinTranslation.class));
    }
}
