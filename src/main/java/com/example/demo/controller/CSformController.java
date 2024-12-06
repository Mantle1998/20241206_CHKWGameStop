package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.CSform;
import com.example.demo.model.CSformRepository;

@Controller  // 使用 @Controller 來渲染視圖
public class CSformController {

    @Autowired
    private CSformRepository cSformRepository;
    
 // 顯示會員資料總覽頁面 (http://localhost:8080/form_manage)
    @GetMapping("/form_manage")
    public String showform_manage() {
        return "Customer_Service/form_manage";
    }

    // 取得所有會員資料，回傳 JSON 格式 (http://localhost:8080/form_manage/json)
    @GetMapping("/form_manage/json")
    @ResponseBody
    public List<CSform> getform_manageJson() {
        return cSformRepository.findAll();
    }
}