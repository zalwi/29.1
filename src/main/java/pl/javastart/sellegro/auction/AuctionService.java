package pl.javastart.sellegro.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.javastart.sellegro.auction.model.Auction;
import pl.javastart.sellegro.auction.model.AuctionFilters;
import pl.javastart.sellegro.auction.repository.AuctionRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AuctionService {

    private List<Auction> auctions;
    private AuctionRepository auctionRepository;

    private static final String[] ADJECTIVES = {"Niesamowity", "Jedyny taki", "IGŁA", "HIT", "Jak nowy",
            "Perełka", "OKAZJA", "Wyjątkowy"};

    @Autowired
    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
        try {
            loadData();
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
        this.auctionRepository.findAll().stream()
                .forEach(auction -> auctionRepository.updateTitleForAuction(auction.generateRandomTitle(), auction.getId()));
    }

    private void loadData() throws IOException {
        auctions = new ArrayList<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("dane.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

        Random random = new Random();

        String line = bufferedReader.readLine(); // skip first line
        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(",");
            long id = Long.parseLong(data[0]);
            String randomAdjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
            String title = randomAdjective + " " + data[1] + " " + data[2];
            BigDecimal price = new BigDecimal(data[4].replace("\\.", ","));
            LocalDate endDate = LocalDate.parse(data[5]);
            Auction auction = new Auction(id, title, data[1], data[2], data[3], price, endDate);
            auctions.add(auction);
        }
    }

    public List<Auction> find4MostExpensive() {
        return auctions.stream()
                .sorted(Comparator.comparing(Auction::getPrice).reversed())
                .limit(4)
                .collect(Collectors.toList());
    }

//    public List<Auction> findAllForFilters(AuctionFilters auctionFilters) {
//        return auctions.stream()
//                .filter(auction -> auctionFilters.getTitle() == null || auction.getTitle().toUpperCase().contains(auctionFilters.getTitle().toUpperCase()))
//                .collect(Collectors.toList());
//    }
//
//    public List<Auction> findAllSorted(String sort) {
//        Comparator<Auction> comparator = Comparator.comparing(Auction::getTitle);
//        if(sort.equals("title")) {
//            comparator = Comparator.comparing(Auction::getTitle);
//        } else if(sort.equals("price")) {
//            comparator = Comparator.comparing(Auction::getPrice);
//        }
//
//        return auctions.stream()
//                .sorted(comparator)
//                .collect(Collectors.toList());
//    }
}
