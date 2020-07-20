package pl.javastart.sellegro.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.javastart.sellegro.auction.model.Auction;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long>, JpaSpecificationExecutor<Auction> {

    @Query(value = "SELECT TOP 4 * FROM auction ORDER BY price DESC",  nativeQuery = true)
    List<Auction> findTop4MostExpensive();

}
