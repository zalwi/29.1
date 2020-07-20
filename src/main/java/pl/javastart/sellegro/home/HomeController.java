package pl.javastart.sellegro.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.javastart.sellegro.auction.AuctionService;
import pl.javastart.sellegro.auction.repository.AuctionRepository;

@Controller
public class HomeController {

    private AuctionService auctionService;
    private AuctionRepository auctionRepository;

    public HomeController(AuctionService auctionService, AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
        this.auctionService = auctionService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("cars", auctionService.find4MostExpensive());
        return "home";
    }
}
