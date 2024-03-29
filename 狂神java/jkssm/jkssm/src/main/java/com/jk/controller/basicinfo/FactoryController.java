package com.jk.controller.basicinfo;

import com.jk.domain.Factory;
import com.jk.pagination.Page;
import com.jk.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/basicinfo/factory")
public class FactoryController {

    @Autowired
    private FactoryService factoryService;

    //http://localhost:8080/jkssm/basicinfo/factory/list.action
    @RequestMapping("/list.action")
    public String list(Page page,Model model){
        if( page==null && page.getPageNo()<=0 && page.getPageSize()<=0) {
            page = new Page();
            page.setPageNo(1);
            page.setPageSize(10);
        }
        page=factoryService.findPage(page);
        model.addAttribute("page",page);
        return "/basicinfo/factory/jFactoryList.jsp";
    }
    @RequestMapping("/tocreate.action")
    public String tocreate(){
        return "/basicinfo/factory/jFactoryCreate.jsp";
    }

    @RequestMapping("/insert.action")
    public String insert(Factory factory, Model model){
        if(factory==null){
            model.addAttribute("error","保存数据不能为空");
            return "/basicinfo/factory/jFactoryCreate.jsp";
        }
        factoryService.add(factory);
        return "redirect:/basicinfo/factory/list.action";
    }
    @RequestMapping("/delete.action")
    public String delete(String factoryId, Model model){
        if(factoryId==null){
            model.addAttribute("error","删除的ID不能为空");

        }else {
            factoryService.delete(factoryId);
        }
        return "redirect:/basicinfo/factory/list.action";
    }

    @RequestMapping("/deleteIds.action")
    public String deleteIds(String ids, Model model){
        if(ids==null||"".equals(ids)){
            model.addAttribute("error","删除的ID不能为空");
        }else {
            String[] idlist=ids.split(",");
            factoryService.deleteIds(idlist);
            model.addAttribute("success",true);
        }
        return "redirect:/basicinfo/factory/list.action";
    }
    @RequestMapping("/toModify.action")
    ///basicinfo/factory/toModify.action
    public String toModify(String factoryId, Model model){
        if(factoryId==null||"".equals(factoryId)){
            model.addAttribute("error","修改的ID不能为空");
            return "redirect:/basicinfo/factory/list.action";
        }else {
            Factory factory=factoryService.findById(factoryId);
            model.addAttribute("factory", factory);
            return "/basicinfo/factory/jFactoryModify.jsp";
        }
    }
    @RequestMapping("/save.action")
    public String save(Factory factory, Model model){
        if(factory==null||factory.getFactoryId()==null||"".equals(factory.getFactoryId())){
            model.addAttribute("error","保存数据不能为空");
            return "/basicinfo/factory/jFactoryModify.jsp";
        }else {
            factoryService.save(factory);
            return "redirect:/basicinfo/factory/list.action";
        }
    }
}

