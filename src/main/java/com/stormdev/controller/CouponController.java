/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.controller;

import com.stormdev.dto.request.CouponCreateRequest;
import com.stormdev.dto.response.CouponResponse;
import com.stormdev.service.CouponService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/coupon")
@RequiredArgsConstructor
@Tag(name = "Coupon", description = "Endpoint coupon")
public class CouponController {

    private final CouponService service;

    /**
     * CREATE
     * @param request
     * @return
     */
    @PostMapping
    @Operation(summary = "Create",
    description = "Endpoint Criação de coupon")
    @ApiResponse(responseCode = "201",
    description = "Created coupon criado")
    public ResponseEntity<CouponResponse> create(@Valid @RequestBody CouponCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    /**
     * BUSCAR COUPON
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar Coupon",
    description = "Busca coupon pelo ID")
    @ApiResponse(responseCode = "200",
    description = "Ok Retonado Coupon pelo Id com Sucesso")
    public ResponseEntity<CouponResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * DELETAR COUPON
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete pelo ID",
    description = "Deletar coupon pelo Id")
    @ApiResponse(responseCode = "204",
    description = "Deletado coupon")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}