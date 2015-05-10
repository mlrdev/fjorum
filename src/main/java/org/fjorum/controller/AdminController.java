package org.fjorum.controller;

import org.fjorum.controller.form.UserCreateForm;
import org.fjorum.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getUsersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("form", new UserCreateForm());
        return "admin";
    }

    @RequestMapping(value = "/userCreate", method = RequestMethod.POST)
    public String handleUserCreateForm(@Valid @ModelAttribute("form") UserCreateForm form,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            FlashMessage.ERROR.put(redirectAttributes, "user.create.failure");
        } else {
            try {
                userService.create(form);
                FlashMessage.SUCCESS.put(redirectAttributes, "user.create.success");
            } catch (DataIntegrityViolationException e) {
                bindingResult.reject("email.exists", "Email already exists");
                FlashMessage.ERROR.put(redirectAttributes, "user.create.failure");
            }
        }
        return "redirect:/admin";
    }
}