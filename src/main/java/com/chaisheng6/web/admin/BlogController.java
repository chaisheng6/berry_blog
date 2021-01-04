package com.chaisheng6.web.admin;

import com.chaisheng6.pojo.Blog;
import com.chaisheng6.pojo.User;
import com.chaisheng6.services.BlogService;
import com.chaisheng6.services.TagService;
import com.chaisheng6.services.TypeService;
import com.chaisheng6.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(Model model,
                        @PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC)
                        Pageable pageable,
                        BlogQuery blog){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs";
    }
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog,
                         Model model) {
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        setTYpeAndTag(model);
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    //修改博客内容
    private void setTYpeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        setTYpeAndTag(model);
        Blog blog=blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "admin/blogs-input";
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession session){
        blog.setUser((User)session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blog1 = blogService.saveBlog(blog);
        if (blog1 == null) {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }
}
