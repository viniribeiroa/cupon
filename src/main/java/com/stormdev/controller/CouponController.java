package com.stormdev.controller;

import com.stormdev.dto.request.CouponCreateRequest;
import com.stormdev.dto.response.CouponResponse;
import com.stormdev.service.CouponService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
@Tag(name = "coupon", description = "Endpoint coupon")
public class CouponController {

    private final CouponService service;

    @PostMapping
    public ResponseEntity<CouponResponse> create(@Valid @RequestBody CouponCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}