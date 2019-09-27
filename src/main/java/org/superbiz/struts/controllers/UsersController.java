package org.superbiz.struts.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.superbiz.struts.User;
import org.superbiz.struts.UserService;
import org.superbiz.struts.repository.UserRepository;

import java.util.Optional;

@Controller
public class UsersController {

    private UserRepository userRepository;

    private String errorMessage;

    public UsersController(UserRepository repository) {
        this.userRepository = repository;
    }

    @GetMapping("/addUser")
    public String addUserForm() {
        return "addUserForm";
    }

    @PostMapping("/addUser")
    @Transactional
    public ModelAndView add(@RequestParam Integer id, @RequestParam String firstName, @RequestParam String lastName) {
        ModelAndView mav = new ModelAndView();
        try {
            userRepository.save(new User(id, firstName, lastName));
            mav.setViewName("addedUser");
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
            mav.setViewName("addUserForm");
            mav.addObject("errorMessage", errorMessage);
            return mav;
        }

        return mav;
    }

    @GetMapping("/findUser")
    public ModelAndView findUserForm(ModelAndView mav) {
        mav.setViewName("findUserForm");
        return mav;
    }

    @PostMapping("/findUser")
    public String findUser(@RequestParam long id, Model model) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            model.addAttribute("errorMessage", "User not found");
            return "findUserForm";
        }

        return "displayUser";
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "displayUsers";
    }
}
