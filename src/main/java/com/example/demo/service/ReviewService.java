package com.example.demo.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Reviews;
import com.example.demo.model.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepo;
	
	//所有資料分頁Page
	public Page<Reviews> findReviewsByPage(Integer pageNumber){
		PageRequest pgr = PageRequest.of(pageNumber-1, 5,Sort.Direction.DESC ,"reviewDate");
		Page<Reviews> page = reviewRepo.findAll(pgr);
		return page != null ? page : Page.empty();  // 保證返回非空的 Page
	}
	
	//用ID對應每筆資料
	public Reviews findReviewById(Integer id) {
		Optional<Reviews> op = reviewRepo.findById(id);
		
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	//對應status的所有資料
	// 修改後的 findReviewsByPageAndStatus 方法
	public Page<Reviews> findReviewsByPageAndStatus(Integer pageNumber, Integer reviewStatus, String startDate, String endDate) {
	    // 分頁設置
	    PageRequest pageRequest = PageRequest.of(pageNumber - 1, 5, Sort.Direction.DESC, "reviewDate");

	    // 日期格式化
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date start = null;
	    Date end = null;

	    // 解析日期
	    try {
	        if (startDate != null && !startDate.isEmpty()) {
	            start = sdf.parse(startDate);
	        }
	        if (endDate != null && !endDate.isEmpty()) {
	            end = sdf.parse(endDate);
	        }
	    } catch (java.text.ParseException e) {
	        e.printStackTrace();
	        // 若日期格式錯誤，返回空的頁面資料
	        return Page.empty();
	    }

	    // 根據條件返回不同的查詢結果
	    if (reviewStatus == null && startDate == null && endDate == null) {
	        return reviewRepo.findAll(pageRequest); // 查詢全部
	    } else if (reviewStatus != null && startDate == null && endDate == null) {
	        return reviewRepo.findByReviewStatus(reviewStatus, pageRequest); // 查詢特定狀態
	    } else if (reviewStatus == null && startDate != null && endDate != null) {
	        return reviewRepo.findByReviewDateBetween(start, end, pageRequest); // 查詢日期範圍
	    } else {
	        // 同時查詢 reviewStatus 和日期範圍
	        return reviewRepo.findByReviewStatusAndReviewDateBetween(reviewStatus, start, end, pageRequest);
	    }
	   }


	//修改評論的狀態
	@Transactional
	public Reviews updateReviewStatusById(Integer id, Integer rvwStatus) {
        Optional<Reviews> op = reviewRepo.findById(id);
		
		if(op.isPresent()) {
			Reviews rvw = op.get();
			rvw.setReviewStatus(rvwStatus);
			return rvw;
		}
		return null;
	}

	
}