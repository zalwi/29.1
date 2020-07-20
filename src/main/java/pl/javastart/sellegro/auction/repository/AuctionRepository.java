package pl.javastart.sellegro.auction.repository;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.javastart.sellegro.auction.model.Auction;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long>, JpaSpecificationExecutor<Auction> {

    @Query("SELECT a FROM Auction a " +
            "ORDER BY :column ")
    List<Auction> findAllSorted( String column);

    @Query("SELECT a FROM Auction a " +
            "WHERE a.title LIKE CONCAT('%', :title, '%') " +
            "OR a.carMake LIKE CONCAT('%', :carMake, '%') " +
            "OR a.carModel LIKE CONCAT('%', :carModel, '%') " +
            "OR a.color LIKE CONCAT('%', :color, '%') ")
    List<Auction> findAllBySeachCriteria(   @Param("title") String title,
                                            @Param("carMake") String carMake,
                                            @Param("carModel") String carModel,
                                            @Param("color") String color);

    @Query("SELECT a FROM Auction a " +
            "WHERE a.carMake LIKE CONCAT('%', :carMake, '%')")
    List<Auction> findAllByCarMakeOnly(@Param("carMake") String carMake);

    @Modifying
    @Transactional
    @Query("update Auction a set a.title = ?1 where a.id = ?2")
    void updateTitleForAuction(String title, Long id);
}
