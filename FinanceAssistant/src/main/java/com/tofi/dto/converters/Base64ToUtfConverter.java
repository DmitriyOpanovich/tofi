package com.tofi.dto.converters;

import org.modelmapper.AbstractConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

/**
 * Created by ulian_000 on 26.12.2016.
 */
public class Base64ToUtfConverter extends AbstractConverter<String, String>{
    Logger log = LoggerFactory.getLogger(Base64ToUtfConverter.class);

    @Override
    public String convert(String source) {
        String result = source;

        try {
            result = new String(Base64.getDecoder().decode(source));
        }
        catch(IllegalArgumentException ex) {
            log.error("Illegal base64 string", source);
        }

        return result;
    }


}
