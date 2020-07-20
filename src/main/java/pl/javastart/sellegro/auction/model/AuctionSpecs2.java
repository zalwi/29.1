package pl.javastart.sellegro.auction.model;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AuctionSpecs2 implements Specification<Auction> {

    private String fieldName;
    private String fieldValue;

    public AuctionSpecs2(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public Predicate toPredicate(Root<Auction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (fieldValue == null) {
            return criteriaBuilder.conjunction();
        } else {
            return criteriaBuilder.like(root.get(fieldName), fieldValue);
        }
    }
}
