package com.hooni.repository;

import com.hooni.db.Blog;
import com.hooni.db.Share;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findAllByOrderByTimeCreatedDesc(Pageable pageable);

    List<Blog> findByShareOrderByTimeCreatedDesc(Share share);

    @Query("select b from Blog b where b.share.id = :shareId order by b.timeCreated desc")
    List<Blog> findBlogsByShareId(@Param("shareId") Long shareId);

    @Query("select b from Blog b where b.share.id = :shareId order by b.timeCreated desc")
    List<Blog> findBlogsByShareId(@Param("shareId") Long shareId, Pageable pageable);

    long countByShare(Share share);
}