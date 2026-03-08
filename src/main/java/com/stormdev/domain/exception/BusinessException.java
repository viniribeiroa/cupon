/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.domain.exception;

public class BusinessException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
        super(message);
    }
}

