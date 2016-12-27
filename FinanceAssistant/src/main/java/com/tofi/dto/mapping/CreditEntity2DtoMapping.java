package com.tofi.dto.mapping;

import com.tofi.dto.CreditDTO;
import com.tofi.dto.EnumDTO;
import com.tofi.dto.converters.Base64ToUtfConverter;
import com.tofi.dto.converters.EnumEntityToDtoConverter;
import com.tofi.model.Credit;
import com.tofi.model.enums.ClientType;
import com.tofi.model.enums.CreditGoal;
import com.tofi.model.enums.RepaymentMethod;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.modelmapper.AbstractConverter;
//import org.modelmapper.Converter;
//import org.modelmapper.PropertyMap;

/**
 * Created by ulian_000 on 26.12.2016.
 */
public class CreditEntity2DtoMapping extends PropertyMap<Credit, CreditDTO> {
    private Logger log = LoggerFactory.getLogger(CreditEntity2DtoMapping.class);

    @Override
    protected void configure() {
        using(base64ToUtfConverter).map().setName(source.getName());
        using(base64ToUtfConverter).map().setDescription(source.getDescription());
        using(base64ToUtfConverter).map().setBankName(source.getBank().getName());
        using(clientTypeConverter).map(source.getClientType()).setClientType(null);
        using(creditGoalConverter).map(source.getGoal()).setGoal(null);
        using(repaymentMethodConverter).map(source.getRepaymentMethod()).setRepaymentMethod(null);
    }

    private Converter<String, String> base64ToUtfConverter = new Base64ToUtfConverter();
    private Converter<ClientType, EnumDTO> clientTypeConverter = new EnumEntityToDtoConverter();
    private Converter<CreditGoal, EnumDTO> creditGoalConverter = new EnumEntityToDtoConverter();
    private Converter<RepaymentMethod, EnumDTO> repaymentMethodConverter = new EnumEntityToDtoConverter();


//    Condition bankProvided = new Condition<Credit, CreditDTO>() {
//
//        @Override
//        public boolean applies(MappingContext<Credit, CreditDTO> mappingContext) {
//            return mappingContext.getSource().getBank() != null;
//        }
//    };

}
