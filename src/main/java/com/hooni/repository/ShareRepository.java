package com.hooni.repository;

import com.hooni.db.Share;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
    List<Share> findAllByOrderByTimeCreatedDesc(Pageable pageable);
}
