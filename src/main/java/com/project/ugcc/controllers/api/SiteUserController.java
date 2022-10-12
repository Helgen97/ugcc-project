package com.project.ugcc.controllers.api;

import com.project.ugcc.DTO.SiteUserDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.SiteUser;
import com.project.ugcc.services.modelsService.SiteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        return siteUserService.getAll().stream().map(SiteUserDTO::of).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SiteUserDTO getOneById(@PathVariable long id) {
        return SiteUserDTO.of(siteUserService.getOneById(id).orElseThrow(() -> new NotFoundException("user not found!")));
    }

    @PostMapping
    public SiteUserDTO createUser(@RequestBody SiteUser siteUser) {
        return SiteUserDTO.of(siteUserService.create(siteUser));
    }

    @PutMapping
    public SiteUserDTO updateUser(@RequestBody SiteUser siteUser) {
        return SiteUserDTO.of(siteUserService.update(siteUser));
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
