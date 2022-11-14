package com.project.ugcc.controllers.api;

import com.project.ugcc.DTO.SiteUserDTO;
import com.project.ugcc.models.SiteUser;
import com.project.ugcc.services.modelsService.SiteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class SiteUserController {

    private final SiteUserService siteUserService;

    @Autowired
    public SiteUserController(SiteUserService siteUserService) {
        this.siteUserService = siteUserService;
    }

    @GetMapping
    public List<SiteUserDTO> getAll() {
        return siteUserService.getAll();
    }

    @GetMapping("/{id}")
    public SiteUserDTO getOneById(@PathVariable long id) {
        return siteUserService.getOneById(id);
    }

    @PostMapping
    public SiteUserDTO createUser(@RequestBody SiteUser siteUser) {
        return siteUserService.create(siteUser);
    }

    @PutMapping
    public SiteUserDTO updateUser(@RequestBody SiteUser siteUser) {
        return siteUserService.update(siteUser);
    }

    @PatchMapping
    public void changePassword(@RequestParam Long id, @RequestParam String rawPassword) {
        siteUserService.changePassword(id, rawPassword);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        siteUserService.delete(id);
    }
}
