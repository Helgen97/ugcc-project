package com.project.ugcc.controllers.api;

import com.project.ugcc.DTO.BroadcastDTO;
import com.project.ugcc.models.Broadcast;
import com.project.ugcc.services.modelsService.BroadcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/broadcasts")
public class BroadcastController {

    private final BroadcastService broadcastService;

    @Autowired
    public BroadcastController(BroadcastService broadcastService) {
        this.broadcastService = broadcastService;
    }

    @GetMapping("/{id}")
    public BroadcastDTO getOneById(@PathVariable Long id) {
        return broadcastService.getOneById(id);
    }

    @GetMapping
    public List<BroadcastDTO> getAll() {
        return broadcastService.getAll();
    }

    @GetMapping("/page")
    public Page<BroadcastDTO> getPageOfBroadcasts(@RequestParam int page, @RequestParam int size) {
        return broadcastService.getPageOfBroadcasts(page, size);
    }

    @PostMapping
    public BroadcastDTO create(@RequestBody Broadcast broadcast) {
        return broadcastService.create(broadcast);
    }

    @PutMapping
    public BroadcastDTO update(@RequestBody Broadcast broadcast) {
        return broadcastService.update(broadcast);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        broadcastService.delete(id);
    }
}
