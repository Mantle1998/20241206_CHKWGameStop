package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Item;
import com.example.demo.model.ItemRepository;

@Controller
public class HomePageController {
	
	 @Autowired
	    private ItemRepository itemRepository;
	
	// 顯示商城首頁頁面 (http://localhost:8080/homePage)
    @GetMapping("/homePage")
    public String showhomePage() {
        return "homePage";
    }
    
    // 顯示商城搜尋頁面 (http://localhost:8080/itemSearch)
    @GetMapping("/itemSearch")
    public String showitemSearch() {
        return "itemSearch";
    }
    
    // 顯示商城商品頁面 (http://localhost:8080/itemDisplay)
    @GetMapping("/itemDisplay")
    public String showitemDisplay() {
        return "itemDisplay";
    }
    
    @GetMapping("/itemDisplay/{id}")
    public String showItemDisplay(@PathVariable("id") int itemId, Model model) {
        // 使用 Repository 獲取商品資料
        Item item = itemRepository.findById(itemId).orElse(null);

        if (item == null) {
            return "redirect:/homePage"; // 如果商品不存在，返回首頁
        }

        model.addAttribute("item", item); // 將商品資訊傳遞到前端
        return "itemDisplay";
    }
    
    
    // 取得最新 15 項商品，回傳 JSON 格式
    @GetMapping("/items/latest15")
    @ResponseBody
    public List<Item> getLatestItems() {
        return itemRepository.findTop15ByOrderByItemDateDesc();
    }

}
