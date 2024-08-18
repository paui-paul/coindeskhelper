package com.cathay.bank.coindeskhelper.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JsonDeserialize {
    private JsonDeserialize() {}

    public static class CoindeskApiTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        private static final List<DateTimeFormatter> FORMATTERS = Arrays.asList(
                DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss z", Locale.ENGLISH),
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm z", Locale.ENGLISH));

        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext context)
                throws IOException {
            String date = jsonParser.getText();
            for (DateTimeFormatter formatter : FORMATTERS) {
                try {
                    return LocalDateTime.parse(date, formatter);
                } catch (DateTimeParseException e) {
                    // Try the next formatter
                }
            }
            throw new RuntimeException("Failed to parse date: " + date);
        }

    }

}
