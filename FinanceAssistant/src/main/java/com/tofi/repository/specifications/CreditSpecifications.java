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
public class CreditSpecifications {

    public static Specification<Credit> matchFilter(CreditFilter filter) {
        return new Specification<Credit>() {

            @Override
            public Predicate toPredicate(Root<Credit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Credit,ClientType> clientTypeJoin = root.join( Credit_.clientType );
                Join<Credit,CreditGoal> goalJoin = root.join( Credit_.goal );
                Join<Credit,PaymentPosibility> paymentPosibilityJoin = root.join(Credit_.paymentPosibilities );
                Join<Credit, PercentageTerm> percentageTermsJoin = root.join(Credit_.terms);
                Join<PercentageTerm,Currency> currencyJoin = percentageTermsJoin.join(PercentageTerm_.currency);
                Join<Credit, RepaymentMethod> repaymentMethodJoin = root.join(Credit_.repaymentMethod);

                List<Order> orderList = new ArrayList();
                orderList.add(criteriaBuilder.asc(percentageTermsJoin.get(PercentageTerm_.percentage)));
                orderList.add(criteriaBuilder.desc(root.get(Credit_.updateDate)));

                criteriaQuery.orderBy(orderList);

                return criteriaBuilder.and(
                        criteriaBuilder.equal(clientTypeJoin.get(ClientType_.name), filter.getClientType().getName()),
                        criteriaBuilder.equal(goalJoin.get(CreditGoal_.name), filter.getGoal().getName()),

                        filter.getRepaymentMethod() != null ?
                                criteriaBuilder.equal(repaymentMethodJoin.get(RepaymentMethod_.name),
                                    filter.getRepaymentMethod().getName()) :
                                criteriaBuilder.isNotNull(repaymentMethodJoin.get(RepaymentMethod_.name)),

                        filter.getCertificates() != null ?
                                criteriaBuilder.equal(root.get(Credit_.needCertificates), filter.getCertificates()) :
                                criteriaBuilder.isNotNull(root.get(Credit_.needCertificates)),

                        filter.getNeedGurantor() != null ?
                                criteriaBuilder.equal(root.get(Credit_.needsGurantor), filter.getNeedGurantor()) :
                                criteriaBuilder.isNotNull(root.get(Credit_.needsGurantor)),

                        filter.getPaymentPosibility() != null ?
                                criteriaBuilder.equal(paymentPosibilityJoin.get(PaymentPosibility_.name),
                                    filter.getPaymentPosibility().getName()) :
                                criteriaBuilder.isNotNull(paymentPosibilityJoin.get(PaymentPosibility_.name)),

                        filter.getPledge() != null ?
                                criteriaBuilder.equal(root.get(Credit_.pledge), filter.getPledge()):
                                criteriaBuilder.isNotNull(root.get(Credit_.pledge)),

                        criteriaBuilder.equal(currencyJoin.get(Currency_.name), filter.getCurrency().getName()),

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
                                        percentageTermsJoin.get(PercentageTerm_.maxAmmount), filter.getAmmount())
                        ),
                        criteriaBuilder.or(
                                criteriaBuilder.isNull(percentageTermsJoin.get(PercentageTerm_.minAmmount)),
                                criteriaBuilder.lessThanOrEqualTo(
                                        percentageTermsJoin.get(PercentageTerm_.minAmmount), filter.getAmmount())
                        ),

                        criteriaBuilder.or(
                                criteriaBuilder.isNull(percentageTermsJoin.get(PercentageTerm_.percentage)),
                                criteriaBuilder.lessThanOrEqualTo(
                                        percentageTermsJoin.get(PercentageTerm_.percentage), filter.getMaxPercentage())
                        )

//                      criteriaBuilder.equal(root.get(Credit_.prePayments), filter.getPrePayments())
                );
            }
        };
    }
}
