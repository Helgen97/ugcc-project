package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.BroadcastDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.Broadcast;
import com.project.ugcc.repositories.BroadcastRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BroadcastService implements ModelService<Broadcast, BroadcastDTO> {

    private final static Logger LOGGER = LogManager.getLogger(BroadcastService.class.getName());

    private final BroadcastRepository broadcastRepository;

    @Autowired
    public BroadcastService(BroadcastRepository broadcastRepository) {
        this.broadcastRepository = broadcastRepository;
    }

    @Override
    public BroadcastDTO getOneById(Long id) {
        LOGGER.info(String.format("Getting broadcast by id. Broadcast id: %d.", id));
        return BroadcastDTO.of(broadcastRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Broadcast with id %d not found", id))));
    }

    public Page<BroadcastDTO> getPageOfBroadcasts(int page, int size) {
        LOGGER.info(String.format("Get page of broadcasts. Page: %d. Size: %d", page, size));
        Page<Broadcast> broadcastPage = broadcastRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        return broadcastPage.map(BroadcastDTO::of);
    }

    @Override
    public List<BroadcastDTO> getAll() {
        LOGGER.info("Getting all broadcasts.");
        return broadcastRepository.findAll().stream().map(BroadcastDTO::of).collect(Collectors.toList());
    }

    @Override
    public BroadcastDTO create(Broadcast broadcast) {
        LOGGER.info("Creating new broadcast");
        return BroadcastDTO.of(broadcastRepository.save(broadcast));
    }

    @Override
    public BroadcastDTO update(Broadcast broadcast) {
        LOGGER.info(String.format("Updating broadcast. Broadcast id: %d", broadcast.getId()));
        return BroadcastDTO.of(broadcastRepository.save(broadcast));
    }

    @Override
    public void delete(Long id) {
        LOGGER.info(String.format("Deleting broadcast. Broadcast id: %d", id));
        broadcastRepository.deleteById(id);
    }
}
