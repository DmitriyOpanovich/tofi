package com.tofi.dto.converters;

import com.tofi.dto.EnumDTO;
import com.tofi.model.enums.BaseEnumEntity;
import org.modelmapper.AbstractConverter;

/**
 * Created by ulian_000 on 26.12.2016.
 */
public class EnumEntityToDtoConverter<T extends BaseEnumEntity> extends AbstractConverter<T, EnumDTO> {
    private Base64ToUtfConverter base64ToUtfConverter = new Base64ToUtfConverter();

    @Override
    protected EnumDTO convert(T clientType) {
        if (clientType == null) return null;

        EnumDTO enumDTO = new EnumDTO();
        enumDTO.setName(base64ToUtfConverter.convert(clientType.getName()));
        enumDTO.setRu_descr(base64ToUtfConverter.convert(clientType.getRu_descr()));

        return enumDTO;//map(clientType, EnumDTO.class);
    }
}
