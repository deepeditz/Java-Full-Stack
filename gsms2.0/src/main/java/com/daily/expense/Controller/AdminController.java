package com.daily.expense.Controller;

import com.daily.expense.Utility.ServletUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String showAdminPage(HttpServletRequest request) {
        return "admin"; // Adjust the view name as per your configuration
    }
}
