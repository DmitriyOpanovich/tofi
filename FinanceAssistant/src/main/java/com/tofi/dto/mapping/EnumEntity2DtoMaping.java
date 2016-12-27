package com.tofi.dto.mapping;

import com.tofi.dto.EnumDTO;
import com.tofi.dto.converters.Base64ToUtfConverter;
import com.tofi.model.enums.BaseEnumEntity;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

/**
 * Created by ulian_000 on 26.12.2016.
 */
public class EnumEntity2DtoMaping<T extends BaseEnumEntity> extends PropertyMap<T , EnumDTO> {

    @Override
    protected void configure() {
        using(base64ToUtfConverter).map().setName(source.getName());
        using(base64ToUtfConverter).map().setRu_descr(source.getRu_descr());
    }

    private Converter<String, String> base64ToUtfConverter = new Base64ToUtfConverter();
}
