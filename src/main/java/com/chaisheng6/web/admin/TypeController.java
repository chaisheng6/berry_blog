package com.chaisheng6.web.admin;

import com.chaisheng6.pojo.Type;
import com.chaisheng6.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //分类
    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable,
                        Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    //编辑分类，通过id先查到要修改的记录
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    @PostMapping("/post-types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        //重复校验
        if (type1 != null) {
            result.rejectValue("name", "nameError", "不能添加重复分类");
        }
        //非空校验
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            redirectAttributes.addFlashAttribute("message", "添加失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "添加成功");
        }
        return "redirect:/admin/types";
    }
    //修改分类
    @PostMapping("/post-types/{id}")
    public String editPost(@Valid Type type,
                           BindingResult result,
                           @PathVariable Long id,
                           RedirectAttributes redirectAttributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        //重复校验
        if (type1 != null) {
            result.rejectValue("name", "nameError", "不能添加重复分类");
        }
        //非空校验
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if (t == null) {
            redirectAttributes.addFlashAttribute("message", "更新失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    //删除分类
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message", "删除成功");
        typeService.deleteType(id);
        return "redirect:/admin/types";
    }
}
