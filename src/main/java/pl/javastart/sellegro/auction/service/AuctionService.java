package pl.javastart.sellegro.auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.javastart.sellegro.auction.model.Auction;
import pl.javastart.sellegro.auction.model.AuctionFilters;
import pl.javastart.sellegro.auction.repository.AuctionRepository;

import java.util.List;

@Service
public class AuctionService {

    private AuctionRepository auctionRepository;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
        generateRandomTitlesForEachAuction();
    }

    private void generateRandomTitlesForEachAuction() {
        List<Auction> allData = auctionRepository.findAll();
        for(Auction auction: allData){
            auction.setTitle(auction.generateRandomTitle());
        }
        auctionRepository.saveAll(allData);
    }

    private Specification<Auction> addSpecification(Specification<Auction> specification, String fieldName, String fieldValue) {
        if (!StringUtils.isEmpty(fieldValue)) {
            Specification<Auction> secondSpecification = specification.and(
                                                                            (Specification<Auction>) (root, query, criteriaBuilder)
                                                                                -> criteriaBuilder.like(   criteriaBuilder.upper(root.get(fieldName)),
                                                                                                        "%" + fieldValue.toUpperCase() + "%"));
            return specification.and(secondSpecification);
        }else{
            return specification;
        }
    }

    public List<Auction> find4MostExpensive() {
        return auctionRepository.findTop4ByOrderByPriceDesc();
    }

    public Page<Auction> findAllForFiltersAndSort(AuctionFilters auctionFilters, PageRequest pageable) {
        Specification<Auction> specification = Specification.where(null);
        specification = addSpecification(specification, "title", auctionFilters.getTitle());
        specification = addSpecification(specification, "carModel", auctionFilters.getCarModel());
        specification = addSpecification(specification, "carMake", auctionFilters.getCarMake());
        specification = addSpecification(specification, "color", auctionFilters.getColor());
        return auctionRepository.findAll(specification, pageable);
    }
}
