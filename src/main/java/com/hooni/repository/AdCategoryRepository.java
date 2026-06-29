package com.hooni.repository;

import com.hooni.db.AdCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdCategoryRepository extends JpaRepository<AdCategory, String> {}
