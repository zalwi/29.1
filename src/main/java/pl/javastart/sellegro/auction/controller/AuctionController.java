package pl.javastart.sellegro.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.javastart.sellegro.auction.model.Auction;
import pl.javastart.sellegro.auction.service.AuctionService;
import pl.javastart.sellegro.auction.model.AuctionFilters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
                columnName -> {
                    model.addAttribute("cars", auctionService.findAllSortByColumnName(columnName));
                },
                () -> {
                    model.addAttribute("cars", auctionService.findAllWithSearchCriteria(auctionFilters));
                }
        );
        model.addAttribute("filters", auctionFilters);
        return "auctions";
    }

    @RequestMapping(value = "/auctions/page/{page}")
    public ModelAndView listAuctionsPageByPage(@PathVariable(name = "page") int page) {
        ModelAndView modelAndView = new ModelAndView("auctionsPages");
        PageRequest pageable = PageRequest.of(page - 1, 50);
        Page<Auction> auctionPage = auctionService.getPaginatesAuctions(pageable);
        int totalPages = auctionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("auctionList", auctionPage.getContent());
        return modelAndView;
    }

    @GetMapping("/auctions/pages")
    public String listAuctionsPageByPage2(Model model,
                                          @RequestParam(name = "page", defaultValue = "1") int page,
                                          @RequestParam(name = "sort", required = false) String sort,
                                          AuctionFilters auctionFilters) {
        PageRequest pageable;
        if (sort == null) {
            pageable = PageRequest.of(page - 1, 50);
        } else {
            pageable = PageRequest.of(0, 3, Sort.by(sort).descending());
        }
        Page<Auction> auctionPage = auctionService.findAllForFiltersAndSort(auctionFilters, pageable);
        int totalPages = auctionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("auctionList", auctionPage.getContent());
        return "auctionsPages2";
    }
}
