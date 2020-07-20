package pl.javastart.sellegro.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.javastart.sellegro.auction.service.AuctionService;
import pl.javastart.sellegro.auction.model.AuctionFilters;

import java.util.Optional;

@Controller
public class AuctionController {

    private AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/auctions")
    public String auctions(Model model,
                           @RequestParam(name = "sort") Optional<String> optionalSort,
                           AuctionFilters auctionFilters) {

        System.out.println(auctionFilters);
        optionalSort.ifPresentOrElse(
                //columnName      -> {model.addAttribute("cars", auctionRepository.findAll(Sort.by(Sort.Direction.ASC, columnName)));},
                columnName      -> {model.addAttribute("cars", auctionService.findAllSortByColumnName(columnName));},
                ()              -> {model.addAttribute("cars", auctionService.findAllWithSearchCriteria(auctionFilters));}
                );
        model.addAttribute("filters", auctionFilters);
        return "auctions";
    }
}
