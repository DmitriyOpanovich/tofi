package com.tofi.dto.mapping;

import com.tofi.dto.CreditDTO;
import com.tofi.model.Credit;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ulian_000 on 17.12.2016.
 */
public class CreditDto2EntityMapping extends PropertyMap<CreditDTO, Credit>{
    @Autowired
    ModelMapper modelMapper;

    @Override
    protected void configure() {
     //   map().setAgregatorId(source.getAgregatorId());

//        using(toClientType).map(source.getClientType()).setClientType(null);
//          using(toCreditGoal).map(source.getGoal()).setGoal(null);
//        using(toRepaymentMethod).map(source.getRepaymentMethod()).setRepaymentMethod(null);
//        using(toPaymentPossibilities).map(source.getPaymentPosibilities()).setPaymentPosibilities(null);

    }

//    Converter<String, ClientType> toClientType = new AbstractConverter<String, ClientType>() {
//        protected ClientType convert(String str) {
//            if (str == null || str.isEmpty()) return null;
//
//            return new ClientType(str);
//        }
//    };
//
//    Converter<EnumDTO, CreditGoal> toCreditGoal = new AbstractConverter<EnumDTO, CreditGoal>() {
//        protected CreditGoal convert(EnumDTO dto) {
//            return modelMapper.map(dto, CreditGoal.class);
//        }
//    };
//
//    Converter<String, RepaymentMethod> toRepaymentMethod = new AbstractConverter<String, RepaymentMethod>() {
//        protected RepaymentMethod convert(String str) {
//            if (str == null || str.isEmpty()) return null;
//
//            return new RepaymentMethod(str);
//        }
//    };
//
//    Converter<List<String>, List<PaymentPosibility>> toPaymentPossibilities = new AbstractConverter<List<String>, List<PaymentPosibility>>() {
//        protected List<PaymentPosibility> convert(List<String> strList) {
//            if (strList == null || strList.isEmpty()) return null;
//
//            return strList.stream()
//                    .map(str -> new PaymentPosibility(str))
//                    .collect(Collectors.toList());
//
//        }
//    };


//    private List<PercentageTermDTO> terms;

}
