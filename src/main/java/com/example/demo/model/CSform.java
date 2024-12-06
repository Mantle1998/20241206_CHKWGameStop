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
// 指定資料庫表格名稱為 "CSForm"
@Table(name = "CSForm")
public class CSform {

    // 指定主鍵 (Primary Key) 並自動生成 ID 值
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CSFormId")
    private Integer CSFormId;
    // 用於儲存表格中的 ID 欄位

    // 指定 "CSFormSort" 欄位，必須是唯一且不可為空值
    @Column(name = "CSFormSort", unique = true, nullable = false)
    private String CSFormSort;
    // 用於儲存分類資訊

    // 指定 "CSFormTitle" 欄位，不可為空值
    @Column(name = "CSFormTitle", nullable = false)
    private String CSFormTitle;
    // 用於儲存標題資訊

    // 指定 "CSFormContent" 欄位，不可為空值
    @Column(name = "CSFormContent", nullable = false)
    private String CSFormContent;
    // 用於儲存文章內容

    // 使用 @DateTimeFormat 來指定日期格式，@Temporal 來指定資料庫中的時間型態
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 格式化日期為 yyyy-MM-dd HH:mm:ss
    @Temporal(TemporalType.TIMESTAMP)
    private Date CSFormDate;
    // 用於儲存文章建立的日期與時間

    // 指定 "CSformChack" 欄位，不可為空值
    @Column(name = "CSformChack", nullable = false)
    private Integer CSformChack;
    // 用於儲存文章的狀態（例如：0 表示未審核，1 表示已發布）

    // 以下是 getter 和 setter 方法，用於取得和設定屬性值

    public Integer getCSFormId() {
        return CSFormId;
    }

    public void setCSFormId(Integer cSFormId) {
        this.CSFormId = cSFormId;
    }

    public String getCSFormSort() {
        return CSFormSort;
    }

    public void setCSFormSort(String cSFormSort) {
        this.CSFormSort = cSFormSort;
    }

    public String getCSFormTitle() {
        return CSFormTitle;
    }

    public void setCSFormTitle(String cSFormTitle) {
        this.CSFormTitle = cSFormTitle;
    }

    public String getCSFormContent() {
        return CSFormContent;
    }

    public void setCSFormContent(String cSFormContent) {
        this.CSFormContent = cSFormContent;
    }

    public Date getCSFormDate() {
        return CSFormDate;
    }

    public void setCSFormDate(Date cSFormDate) {
        this.CSFormDate = cSFormDate;
    }

    public Integer getCSformChack() {
        return CSformChack;
    }

    public void setCSformChack(Integer cSformChack) {
        this.CSformChack = cSformChack;
    }

	public static void addAttribute(String string, List<CSform> csForms) {
		// TODO Auto-generated method stub
		
	}


}