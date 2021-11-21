package de.exchange.repository;


import de.exchange.entity.MinerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MinerRepository extends JpaRepository<MinerEntity, String> {
    List<MinerEntity> findByResourceCurrency(String currencyCode);

    MinerEntity findByUserId(String userId);

    @Query(nativeQuery = true, value = "select * from miner, wallet, wallet_details where miner.user_id=wallet.user_id \n" +
            "ANd wallet_details.wallet_id=wallet.id AND wallet_details.currency=:currency_code AND amount > :amount")
    List<MinerEntity> findMiners(@Param("currency_code") String currencyCode, @Param("amount") Double amount);
}