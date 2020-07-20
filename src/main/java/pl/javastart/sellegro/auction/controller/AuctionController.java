package pl.javastart.sellegro.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.javastart.sellegro.auction.AuctionService;
import pl.javastart.sellegro.auction.model.Auction;
import pl.javastart.sellegro.auction.model.AuctionFilters;
import pl.javastart.sellegro.auction.model.AuctionSpecs;
import pl.javastart.sellegro.auction.model.AuctionSpecs2;
import pl.javastart.sellegro.auction.repository.AuctionRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class AuctionController {

    private AuctionService auctionService;
    private AuctionRepository auctionRepository;

    @Autowired
    public AuctionController(AuctionService auctionService, AuctionRepository auctionRepository) {
        this.auctionService = auctionService;
        this.auctionRepository = auctionRepository;
    }

    @GetMapping("/auctions")
    public String auctions(Model model,
                           @RequestParam(name = "sort") Optional<String> optionalSort,
                           AuctionFilters auctionFilters) {

//        System.out.println("Update Tytułu");
//        auctionRepository.findAll().stream()
//                .forEach(auction -> auctionRepository.updateTitleForAuction(auction.generateRandomTitle(), auction.getId()));
        System.out.println(auctionFilters);
        optionalSort.ifPresentOrElse(
                columnName    -> {
                    Sort sortBySpecifiedColumnName = Sort.by(Sort.Direction.ASC, columnName);
                    model.addAttribute("cars", auctionRepository.findAll(sortBySpecifiedColumnName));
                    },
//                sort    -> {model.addAttribute("cars", auctionService.findAllSorted(sort));},
//                ()      -> {model.addAttribute("cars", auctionService.findAllForFilters(auctionFilters));}
//                ()      -> {model.addAttribute("cars", auctionRepository.findAll());}
//                ()      -> {model.addAttribute("cars", auctionRepository.findAllBySeachCriteria(auctionFilters.getTitle(),
//                                                                                                            auctionFilters.getCarMake(),
//                                                                                                            auctionFilters.getCarModel(),
//                                                                                                            auctionFilters.getColor()));}
//                ()      -> {model.addAttribute("cars", auctionRepository.findAllByCarMakeOnly(auctionFilters.getCarMake()));}
                ()      -> {
                    Specification titleSpec = new AuctionSpecs2("title", auctionFilters.getTitle());
                    Specification carMakeSpec = new AuctionSpecs2("carMake", auctionFilters.getCarMake());
                    Specification carModelSpec = new AuctionSpecs2("carModel", auctionFilters.getCarModel());
                    Specification colorSpec = new AuctionSpecs2("color", auctionFilters.getColor());

//                    model.addAttribute("cars", auctionRepository.findAll(new AuctionSpecs(auctionFilters)));}
                    model.addAttribute("cars", auctionRepository.findAll(titleSpec
                                                                                        .and(carMakeSpec)
                                                                                        .and(carModelSpec)
                                                                                        .and(colorSpec)
                                                                                    ));}
                );
        //System.out.println(auctionRepository.findAll());
        model.addAttribute("filters", auctionFilters);
        return "auctions";
    }
}
