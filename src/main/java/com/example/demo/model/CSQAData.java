package com.example.demo.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

// 指定這是一個實體類別，對應到資料庫中的表格
@Entity
// 指定資料庫表格名稱為 "CSQAData"
@Table(name = "CSQAData")
public class CSQAData {

    // 指定主鍵 (Primary Key) 並自動生成 ID 值
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CSQADataid")
    private Integer CSQADataid;
    // 用於儲存表格中的 ID 欄位

    // 指定 "CSQADataSort" 欄位，必須是唯一且不可為空值
    @Column(name = "CSQADataSort", unique = true, nullable = false)
    private String CSQADataSort;
    // 用於儲存分類資訊

    // 指定 "CSQADataTitle" 欄位，不可為空值
    @Column(name = "CSQADataTitle", nullable = false)
    private String CSQADataTitle;
    // 用於儲存標題資訊

    // 指定 "CSQADataContent" 欄位，不可為空值
    @Column(name = "CSQADataContent", nullable = false)
    private String CSQADataContent;
    // 用於儲存文章內容

    // 使用 @DateTimeFormat 來指定日期格式，@Temporal 來指定資料庫中的時間型態
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 格式化日期為 yyyy-MM-dd HH:mm:ss
    @Temporal(TemporalType.TIMESTAMP)
    private Date CSQADataDATE;
    // 用於儲存文章建立的日期與時間

    // 指定 "CSQADataChack" 欄位，不可為空值
    @Column(name = "CSQADataChack", nullable = false)
    private Integer CSQADataChack;
    // 用於儲存文章的狀態（例如：0 表示未審核，1 表示已發布）

 // 以下是 getter 和 setter 方法，用於取得和設定屬性值
    
	public Integer getCSformid() {
		return CSQADataid;
	}

	public void setCSformid(Integer CSQADataid) {
		this.CSQADataid = CSQADataid;
	}

	public String getCSQADataSort() {
		return CSQADataSort;
	}

	public void setCSQADataSort(String cSQADataSort) {
		this.CSQADataSort = cSQADataSort;
	}

	public String getCSQADataTitle() {
		return CSQADataTitle;
	}

	public void setCSQADataTitle(String cSQADataTitle) {
		this.CSQADataTitle = cSQADataTitle;
	}

	public String getCSQADataContent() {
		return CSQADataContent;
	}

	public void setCSQADataContent(String cSQADataContent) {
		this.CSQADataContent = cSQADataContent;
	}

	public Date getCSQADataDATE() {
		return CSQADataDATE;
	}

	public void setCSQADataDATE(Date cSQADataDATE) {
		this.CSQADataDATE = cSQADataDATE;
	}

	public Integer getCSQADataChack() {
		return CSQADataChack;
	}

	public void setCSQADataChack(Integer cSQADataChack) {
		this.CSQADataChack = cSQADataChack;
	}

}