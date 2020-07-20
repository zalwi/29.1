package pl.javastart.sellegro.auction.model;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class AuctionSpecs implements Specification<Auction> {

    private AuctionFilters auctionFilters;
    private String fieldName;
    private String fieldValue;

    public AuctionSpecs(AuctionFilters auctionFilters) {
        this.auctionFilters = auctionFilters;
    }

    @Override
    public Predicate toPredicate(Root<Auction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if(auctionFilters.getCarMake() == null){
            return criteriaBuilder.conjunction();
        }
        return criteriaBuilder.like(root.get("carMake"),auctionFilters.getCarMake());
    }
}
