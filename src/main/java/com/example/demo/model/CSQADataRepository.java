package com.example.demo.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// 定義一個介面 CSFormRepository，繼承 JpaRepository 來進行 CSForm 的 CRUD 操作
public interface CSQADataRepository extends JpaRepository<CSQAData, Integer> {

    // 自定義查詢方法，根據 CSFormId（使用字串型態）來查找對應的 CSForm 實體
	CSQAData findByCSQADataid(Integer CSQADataid);
	
}