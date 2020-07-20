package pl.javastart.sellegro.auction.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionFilters {

    private String title;
    private String carMake;
    private String carModel;
    private String color;

    @Override
    public String toString() {
        return "AuctionFilters{" +
                "title='" + title + '\'' +
                ", carMake='" + carMake + '\'' +
                ", carModel='" + carModel + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
