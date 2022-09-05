package itransition.punko.CourseProject.controllers;

import itransition.punko.CourseProject.models.User;
import itransition.punko.CourseProject.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public String adminPanel(Model model,
                             @RequestParam(value = "page", defaultValue = "1") int page) {

        Page<User> usersPage = adminService.findAllWithPagination(page-1);
        model.addAttribute("usersPage", usersPage);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, usersPage.getTotalPages()).boxed().collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        return "admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        adminService.delete(id);
        return "redirect:/admin";
    }


    @PatchMapping("/block/{id}")
    public String block(@PathVariable("id") int id) {
        adminService.block(id);
        return "redirect:/admin";
    }

    @PatchMapping("/unblock/{id}")
    public String unblock(@PathVariable("id") int id) {
        adminService.unblock(id);
        return "redirect:/admin";
    }

    @PatchMapping("/setAdmin/{id}")
    public String setAdmin(@PathVariable("id") int id) {
        adminService.setAdmin(id);
        return "redirect:/admin";
    }

    @PatchMapping("/removeAdmin/{id}")
    public String removeAdmin(@PathVariable("id") int id) {
        adminService.removeAdmin(id);
        return "redirect:/admin";
    }





}
