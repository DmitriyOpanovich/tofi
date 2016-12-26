package com.tofi.repository.specifications;

import com.tofi.model.*;
import com.tofi.model.enums.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ulian_000 on 21.12.2016.
 */
public class DepositSpecification {
    public static Specification<Deposit> matchFilter(DepositFilter filter) {
        return new Specification<Deposit>() {
            @Override
            public Predicate toPredicate(Root<Deposit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Deposit, ClientType> clientTypeJoin = root.join(Deposit_.clientType);
                Join<Deposit, PercentageType> percentageTypeJoin = root.join(Deposit_.percentageType);
                Join<Deposit, PercentageTerm> percentageTermsJoin = root.join(Deposit_.terms);
                Join<PercentageTerm, Currency> currencyJoin = percentageTermsJoin.join(PercentageTerm_.currency);

                List<Order> orderList = new ArrayList();
                orderList.add(criteriaBuilder.desc(percentageTermsJoin.get(PercentageTerm_.percentage)));
                orderList.add(criteriaBuilder.desc(root.get(Deposit_.updateDate)));
                criteriaQuery.orderBy(orderList);

                return criteriaBuilder.and(
                        filter.getCapitalization() != null ?
                                criteriaBuilder.equal(root.get(Deposit_.capitalization), filter.getCapitalization()) :
                                criteriaBuilder.isNotNull(root.get(Deposit_.capitalization)),

                        filter.getRefilling() != null ?
                                criteriaBuilder.equal(root.get(Deposit_.refilling), filter.getRefilling()) :
                                criteriaBuilder.isNotNull(root.get(Deposit_.refilling)),

                        filter.getBeforeTermWithdrawal() != null ?
                                criteriaBuilder.equal(root.get(Deposit_.beforeTermWithdrawal), filter.getBeforeTermWithdrawal()) :
                                criteriaBuilder.isNotNull(root.get(Deposit_.beforeTermWithdrawal)),
                        criteriaBuilder.equal(clientTypeJoin.get(ClientType_.name), filter.getClientType().getName()),
                        criteriaBuilder.equal(currencyJoin.get(Currency_.name), filter.getCurrency().getName()),

                        filter.getPercentageType() != null ?
                                criteriaBuilder.equal(percentageTypeJoin.get(PercentageType_.name),
                                        filter.getPercentageType().getName()):
                                criteriaBuilder.isNotNull(percentageTypeJoin.get(PercentageType_.name)),

                        criteriaBuilder.or(
                                criteriaBuilder.isNull(percentageTermsJoin.get(PercentageTerm_.minTermMonth)),
                                criteriaBuilder.lessThanOrEqualTo(
                                        percentageTermsJoin.get(PercentageTerm_.minTermMonth), filter.getTermInMounth())
                        ),
                        criteriaBuilder.or(
                                criteriaBuilder.isNull(percentageTermsJoin.get(PercentageTerm_.maxTermMonth)),
                                criteriaBuilder.greaterThanOrEqualTo(
                                        percentageTermsJoin.get(PercentageTerm_.maxTermMonth), filter.getTermInMounth())
                        ),
                        criteriaBuilder.or(
                                criteriaBuilder.isNull(percentageTermsJoin.get(PercentageTerm_.maxAmmount)),
                                criteriaBuilder.greaterThanOrEqualTo(
                                        percentageTermsJoin.get(PercentageTerm_.maxAmmount), filter.getInitFee())
                        ),
                        criteriaBuilder.or(
                                criteriaBuilder.isNull(percentageTermsJoin.get(PercentageTerm_.minAmmount)),
                                criteriaBuilder.lessThanOrEqualTo(
                                        percentageTermsJoin.get(PercentageTerm_.minAmmount), filter.getInitFee())
                        ),
                        criteriaBuilder.or(
                                criteriaBuilder.isNull(percentageTermsJoin.get(PercentageTerm_.percentage)),
                                criteriaBuilder.lessThanOrEqualTo(
                                        percentageTermsJoin.get(PercentageTerm_.percentage), filter.getMinPercentage())
                        )
                );
            }
        };
    }
}
