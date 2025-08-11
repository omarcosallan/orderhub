package com.omarcosallan.orderhub.controller;

import com.omarcosallan.orderhub.dto.ItemDTO;
import com.omarcosallan.orderhub.dto.ItemResponseDTO;
import com.omarcosallan.orderhub.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> list() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ItemResponseDTO> item(@PathVariable String slug) {
        return ResponseEntity.ok(itemService.findBySlug(slug));
    }

    @PostMapping
    public ResponseEntity<ItemResponseDTO> create(@Valid @RequestBody ItemDTO dto) {
        ItemResponseDTO item = itemService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> delete(@PathVariable String slug) {
        itemService.delete(slug);
        return ResponseEntity.ok().build();
    }
}
