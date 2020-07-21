package pl.javastart.sellegro.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.javastart.sellegro.auction.model.Auction;
import pl.javastart.sellegro.auction.service.AuctionService;
import pl.javastart.sellegro.auction.model.AuctionFilters;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AuctionController {

    private AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/auctions/pages")
    public String listAuctionsPageByPage(Model model,
                                          @RequestParam(name = "page", defaultValue = "1") int page,
                                          @RequestParam(name = "sort", required = false) String sort,
                                          AuctionFilters auctionFilters) {
        PageRequest pageable;
        if (sort == null || sort.isEmpty()) {
            pageable = PageRequest.of(page - 1, 50);
            model.addAttribute("sortOption", "");
        } else {
            pageable = PageRequest.of(page - 1, 50, Sort.by(sort).descending());
            model.addAttribute("sortOption", sort);
        }
        Page<Auction> auctionPage = auctionService.findAllForFiltersAndSort(auctionFilters, pageable);
        int totalPages = auctionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("auctionList", auctionPage.getContent());
        model.addAttribute("filters", auctionFilters);
        model.addAttribute("currentPage", page);
        return "auctionsPages";
    }
}
