package pl.javastart.sellegro.auction.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String carMake;
    private String carModel;
    private String color;
    private BigDecimal price;
    private LocalDate endDate;

    public Auction(Long id, String title, String carMake, String carModel, String color, BigDecimal price, LocalDate endDate) {
        this.id = id;
        this.carMake = carMake;
        this.carModel = carModel;
        this.color = color;
        this.price = price;
        this.endDate = endDate;
        this.title = generateRandomTitle();
    }

    public String generateRandomTitle() {
        String[] ADJECTIVES = {"Niesamowity", "Jedyny taki", "IGŁA", "HIT", "Jak nowy",
                "Perełka", "OKAZJA", "Wyjątkowy"};
        Random random = new Random();
        String randomAdjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];

        return randomAdjective + " " + carMake + " " + carModel;
    }

    public void setTitle(String title) {
        if (title == null) {
            this.title = generateRandomTitle();
        } else {
            this.title = title;
        }
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", carMake='" + carMake + '\'' +
                ", carModel='" + carModel + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", endDate=" + endDate +
                '}';
    }
}
