package com.example.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.LoginBean;
import com.example.demo.model.PaymentInfo;
import com.example.demo.model.PaymentInfoRepository;
import com.example.demo.model.UserInfo;
import com.example.demo.model.UserInfoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberCenterController {
	

    @Autowired
    private UserInfoRepository userInfoRepository;
    
    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    

    // 顯示個人資料頁面，從資料庫讀取 userId 對應的資料
    @GetMapping("/memberCenter/profile")
    public String showMemberProfile(Model model, HttpSession session) {
        LoginBean user = (LoginBean) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("errorMessage", "請先登入後再訪問個人資料頁面");
            return "redirect:/login";
        }

        UserInfo userInfo = userInfoRepository.findById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user.getUserId()));

        model.addAttribute("userInfo", userInfo);
        return "memberCenter/memberProfile";
    }

    @PutMapping("/memberCenter/profile/update")
    public ResponseEntity<UserInfo> updateProfile(@RequestBody UserInfo updatedUserInfo, HttpSession session) {
        LoginBean user = (LoginBean) session.getAttribute("user");

        if (user == null) {
            System.out.println("未找到用戶 Session，請確認用戶是否已登入");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Integer sessionUserId = user.getUserId();

        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(sessionUserId);
        if (!userInfoOptional.isPresent()) {
            System.out.println("未找到對應的用戶資料，userId: " + sessionUserId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        UserInfo existingUserInfo = userInfoOptional.get();

        // 日誌輸出
        System.out.println("更新前的用戶資料: " + existingUserInfo);

        // 防止前端傳遞 "null" 字串
        if ("null".equals(updatedUserInfo.getUserName())) {
            updatedUserInfo.setUserName(null);
        }
        if ("null".equals(updatedUserInfo.getUserPassword())) {
            updatedUserInfo.setUserPassword(null);
        }
        if ("null".equals(updatedUserInfo.getUserTel())) {
            updatedUserInfo.setUserTel(null);
        }
        if ("null".equals(updatedUserInfo.getUserBirthday())) {
            updatedUserInfo.setUserBirthday(null);
        }

        // 更新有新值的欄位
        if (updatedUserInfo.getUserName() != null && !updatedUserInfo.getUserName().isEmpty()) {
            System.out.println("更新用戶名稱: " + updatedUserInfo.getUserName());
            existingUserInfo.setUserName(updatedUserInfo.getUserName());
        }
        if (updatedUserInfo.getUserPassword() != null && !updatedUserInfo.getUserPassword().isEmpty()) {
            System.out.println("更新用戶密碼: " + updatedUserInfo.getUserPassword());
            existingUserInfo.setUserPassword(updatedUserInfo.getUserPassword());
        }
        if (updatedUserInfo.getUserTel() != null && !updatedUserInfo.getUserTel().isEmpty()) {
            System.out.println("更新用戶電話: " + updatedUserInfo.getUserTel());
            existingUserInfo.setUserTel(updatedUserInfo.getUserTel());
        }
        if (updatedUserInfo.getUserBirthday() != null && !updatedUserInfo.getUserBirthday().isEmpty()) {
            System.out.println("更新用戶生日: " + updatedUserInfo.getUserBirthday());
            existingUserInfo.setUserBirthday(updatedUserInfo.getUserBirthday());
        }

        // 更新到資料庫
        userInfoRepository.save(existingUserInfo);

        // 同步更新 Session 中的資料
        LoginBean updatedLoginBean = new LoginBean();
        updatedLoginBean.setUserId(existingUserInfo.getUserId());
        updatedLoginBean.setUserName(existingUserInfo.getUserName());
        updatedLoginBean.setUserEmail(existingUserInfo.getUserEmail());
        updatedLoginBean.setUserPassword(existingUserInfo.getUserPassword());
        updatedLoginBean.setUserTel(existingUserInfo.getUserTel());
        updatedLoginBean.setUserBirthday(existingUserInfo.getUserBirthday());
        session.setAttribute("user", updatedLoginBean);

        System.out.println("更新後的用戶資料: " + existingUserInfo);

        return ResponseEntity.ok(existingUserInfo);
    }
    
    @PutMapping("/memberCenter/password/verify")
    public ResponseEntity<?> verifyOldPassword(HttpSession session, @RequestBody Map<String, String> request) {
        // Debug 確認收到的請求數據
        System.out.println("收到的請求數據: " + request);

        LoginBean user = (LoginBean) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用戶未登入");
        }

        String oldPassword = request.get("oldPassword");

        // Debug 輸入的明文密碼和加密密碼
        System.out.println("輸入的舊密碼: " + oldPassword);
        System.out.println("Session 中的加密密碼: " + user.getUserPassword());

        // 驗證舊密碼是否正確
        if (!BCrypt.checkpw(oldPassword, user.getUserPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("舊密碼錯誤");
        }

        return ResponseEntity.ok("密碼驗證成功");
    }

    @PutMapping("/memberCenter/password/update")
    public ResponseEntity<?> updatePassword(HttpSession session, @RequestBody Map<String, String> request) {
        System.out.println("接收到的請求數據: " + request);

        LoginBean user = (LoginBean) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用戶未登入");
        }

        String newPassword = request.get("newPassword");
        if (newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("新密碼不能為空");
        }

        // 驗證密碼規則
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,20}$";
        if (!newPassword.matches(passwordPattern)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密碼必須包含至少一個大寫字母、一個小寫字母和一個數字，長度為8到20個字元");
        }

        // 加密新密碼
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // 更新密碼到 LoginBean
        user.setUserPassword(hashedPassword);

        // 更新到資料庫
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(user.getUserId());
        if (!userInfoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用戶資料不存在，請聯絡管理員");
        }

        UserInfo userInfo = userInfoOptional.get();
        userInfo.setUserPassword(hashedPassword); // 將新密碼設置到資料庫模型
        userInfoRepository.save(userInfo); // 保存到資料庫

        // 更新 Session
        session.setAttribute("user", user);

        return ResponseEntity.ok("密碼更新成功");
    }
    
    @PostMapping("/memberCenter/payment/add")
    public ResponseEntity<?> addPayment(@RequestBody PaymentInfo payment, HttpSession session) {
        LoginBean user = (LoginBean) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "請先登入"));
        }

        try {
            // 設置 userId
            payment.setUserId(user.getUserId());

            // 設置預設狀態
            payment.setPaymentStatus(1);

            // 保存到資料庫
            paymentInfoRepository.save(payment);

            return ResponseEntity.ok(Map.of("message", "新增付款方式成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("message", "新增付款方式失敗"));
        }
    }
    
    @PutMapping("/memberCenter/payment/update/{id}")
    public ResponseEntity<?> updatePayment(@PathVariable Integer id, 
                                           @RequestBody Map<String, Object> updatedFields, 
                                           HttpSession session) {
        // 從 Session 中獲取用戶資訊
        LoginBean user = (LoginBean) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("請先登入");
        }

        // 從資料庫查找付款方式
        PaymentInfo payment = paymentInfoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("找不到該付款方式"));

        // 檢查付款方式是否屬於當前用戶
        if (!payment.getUserId().equals(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("無權編輯此付款方式");
        }

        // 部分更新：僅修改傳遞的欄位
        if (updatedFields.containsKey("paymentCategory")) {
            payment.setPaymentCategory((Integer) updatedFields.get("paymentCategory"));
        }
        if (updatedFields.containsKey("paymentBank")) {
            payment.setPaymentBank((String) updatedFields.get("paymentBank"));
        }
        if (updatedFields.containsKey("paymentAccount")) {
            payment.setPaymentAccount((String) updatedFields.get("paymentAccount"));
        }

        // 儲存修改後的付款方式
        paymentInfoRepository.save(payment);

        return ResponseEntity.ok(payment);
    }

    @GetMapping("/memberCenter/purchase")
    public String showMemberPurchase() {
        return "memberCenter/memberPurchase";
    }

    @GetMapping("/memberCenter/order")
    public String showMemberOrder() {
        return "memberCenter/memberOrder";
    }
}