package de.hey_car.repository;


import de.hey_car.entity.MinerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MinerRepository extends JpaRepository<MinerEntity, String> {
    List<MinerEntity> findByResourceCurrency(String currencyCode);

    MinerEntity findByUserId(String userId);

    @Query(nativeQuery = true, value = "select * from miner, wallet, country_wallet where miner.user_id=wallet.user_id \n" +
            "ANd country_wallet.wallet_id=wallet.id AND country_wallet.currency=:currency_code AND amount > :amount")
    List<MinerEntity> findMiners(@Param("currency_code") String currencyCode, @Param("amount") Double amount);
}