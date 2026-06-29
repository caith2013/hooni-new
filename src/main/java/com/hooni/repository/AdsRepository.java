package com.hooni.repository;

import com.hooni.db.AdCategory;
import com.hooni.db.Ads;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {
    List<Ads> findAllByOrderByTimeCreatedDesc(Pageable pageable);
    List<Ads> findByAdcat(AdCategory category);
}
