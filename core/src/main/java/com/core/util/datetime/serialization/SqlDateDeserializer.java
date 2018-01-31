package com.core.util.datetime.serialization;

import com.core.util.datetime.DateTimeUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Date;

public class SqlDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonToken t = jp.getCurrentToken();
        if(t == JsonToken.VALUE_STRING) {
            String str = jp.getText().trim();
            return DateTimeUtils.toSqlDate(str);
        } else if(t == JsonToken.VALUE_NUMBER_INT) {
            return new Date(jp.getLongValue());
        } else {
            throw ctxt.mappingException(this.handledType());
        }
    }
}
