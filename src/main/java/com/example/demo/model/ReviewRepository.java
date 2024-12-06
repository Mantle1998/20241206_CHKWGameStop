package com.example.demo.model;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Reviews, Integer> {

	Page<Reviews> findByReviewStatus(Integer reviewStatus, Pageable pageable);
	
    // 查詢日期範圍內的評論
    Page<Reviews> findByReviewDateBetween(Date startDate, Date endDate, Pageable pageable);

    // 同時根據 status 和日期範圍查詢
    Page<Reviews> findByReviewStatusAndReviewDateBetween(Integer reviewStatus, Date startDate, Date endDate, Pageable pageable);

}