package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity @Table(name="reviews")
public class Reviews {
	
	@Id @Column(name = "reviewId")
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private int reviewId;
	
	@Column(name = "reviewImg")
	private byte[] reviewImg;
	
	@Column(name = "reviewEvaluation")
	private int reviewEvaluation;
	
	@Column(name = "reviewComment")
	private String reviewComment;
	
	@Column(name = "reviewStatus")
	private int reviewStatus;
	
	@Column(name = "reviewDate")
	private Date reviewDate;
	
	//要對接訂單Orders orderId
	@Column(name = "reviewOrderId")
	private int reviewOrderId;
	
//	@JoinColumn(name = "reviewOrderId")
//	@OneToOne
//	private Orders orderId;
	
	//要對接Orders sellerId
	@Column(name = "beReviewed")
	private int beReviewed;
	
//	@JoinColumn(name = "beReviewed")
//	@OneToOne
//	private Orders sellerId;
	
	//要對接Orders buyerId
	@Column(name = "reviewer")
	private int reviewer;
	
//	@JoinColumn(name = "reviewer")
//	@OneToOne
//	private Orders buyerId;
	
	@PrePersist // 當物件要轉換成 Persistent 狀態以前，執行這個方法
	public void onCreate() {
		if (reviewDate == null) {
			reviewDate = new Date();
		}
	}
	
	public Reviews() {
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public byte[] getReviewImg() {
		return reviewImg;
	}

	public void setReviewImg(byte[] reviewImg) {
		this.reviewImg = reviewImg;
	}

	public int getReviewEvaluation() {
		return reviewEvaluation;
	}

	public void setReviewEvaluation(int reviewEvaluation) {
		this.reviewEvaluation = reviewEvaluation;
	}

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public int getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(int reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public int getReviewOrderId() {
		return reviewOrderId;
	}

	public void setReviewOrderId(int reviewOrderId) {
		this.reviewOrderId = reviewOrderId;
	}

	public int getBeReviewed() {
		return beReviewed;
	}

	public void setBeReviewed(int beReviewed) {
		this.beReviewed = beReviewed;
	}

	public int getReviewer() {
		return reviewer;
	}

	public void setReviewer(int reviewer) {
		this.reviewer = reviewer;
	}
	
	
	
}