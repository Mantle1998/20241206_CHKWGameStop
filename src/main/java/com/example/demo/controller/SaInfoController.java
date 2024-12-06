// (Mantle)
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.SaInfo;
import com.example.demo.model.SaInfoRepository;
import java.util.List;

@Controller
public class SaInfoController {

    @Autowired
    private SaInfoRepository saInfoRepository;

    // 顯示管理員資料總覽頁面 (http://localhost:8080/saInfo)
    @GetMapping("/saInfo")
    public String showSaInfo() {
        return "saInfo/saInfo";
    }

    // 取得所有管理員資料，回傳 JSON 格式 (http://localhost:8080/saInfo/json)
    @GetMapping("/saInfo/json")
    @ResponseBody
    public List<SaInfo> getSaInfoJson() {
        return saInfoRepository.findAll();
    }

    // 新增管理員資料 (http://localhost:8080/saInfo/add)
    @PostMapping("/saInfo/add")
    @ResponseBody
    public ResponseEntity<String> addSaInfo(@RequestBody SaInfo newSa) {
        try {
            // 儲存新的管理員資料到資料庫
            saInfoRepository.save(newSa);
            return ResponseEntity.ok("新增成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("新增失敗：" + e.getMessage());
        }
    }

    // 更新管理員資料 (http://localhost:8080/saInfo/update)
    @PostMapping("/saInfo/update")
    @ResponseBody
    public ResponseEntity<String> updateSaInfo(@RequestBody SaInfo updatedSa) {
        try {
            // 根據 ID 查找現有的管理員資料
            SaInfo sa = saInfoRepository.findById(updatedSa.getSaId())
                    .orElseThrow(() -> new RuntimeException("管理員資料不存在"));

            // 更新管理員資料欄位
            sa.setSaName(updatedSa.getSaName());
            sa.setSaEmail(updatedSa.getSaEmail());
            sa.setSaPassword(updatedSa.getSaPassword());
            sa.setSaTel(updatedSa.getSaTel());
            sa.setSaStatus(updatedSa.getSaStatus());

            // 儲存更新後的管理員資料
            saInfoRepository.save(sa);
            return ResponseEntity.ok("更新成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("更新失敗：" + e.getMessage());
        }
    }
}