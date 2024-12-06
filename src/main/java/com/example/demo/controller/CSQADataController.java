package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.CSQAData;
import com.example.demo.model.CSQADataRepository;

@Controller  // 使用 @Controller 來渲染視圖
public class CSQADataController {

    @Autowired
    private CSQADataRepository cSQADataRepository;
    
 // 顯示會員資料總覽頁面 (http://localhost:8080/QA_manage)
    @GetMapping("/QA_manage")
    public String showQA_manage() {
        return "Customer_Service/QA_manage";
    }

    // 取得所有會員資料，回傳 JSON 格式 (http://localhost:8080/QA_manage/json)
    @GetMapping("/QA_manage/json")
    @ResponseBody
    public List<CSQAData> getQA_manageJson() {
        return cSQADataRepository.findAll();
    }
}