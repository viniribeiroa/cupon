/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        List<String> messages,
        String path
) {
}

