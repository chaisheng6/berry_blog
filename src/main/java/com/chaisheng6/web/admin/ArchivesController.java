package com.chaisheng6.web.admin;

import com.chaisheng6.pojo.Blog;
import com.chaisheng6.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchivesController {

    @Autowired
    BlogService blogService;

    @GetMapping("archives")
    public String archives(Model model){
        model.addAttribute("archives",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.blogCount());
        return "archives";
    }
}
