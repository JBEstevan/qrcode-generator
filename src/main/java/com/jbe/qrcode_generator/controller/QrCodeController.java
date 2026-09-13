package com.jbe.qrcode_generator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbe.qrcode_generator.dto.QrCodeGenerateRequest;
import com.jbe.qrcode_generator.dto.QrCodeGenerateResponse;
import com.jbe.qrcode_generator.service.QrCodeGeneratorService;

@RestController
@RequestMapping("/qrcode")

public class QrCodeController {

	private final QrCodeGeneratorService qrCodeGeneratorService;

	public QrCodeController(QrCodeGeneratorService qrCodeService) {
		this.qrCodeGeneratorService = qrCodeService;
	}

	@PostMapping
	public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGenerateRequest request) {
		try {
			QrCodeGenerateResponse response = this.qrCodeGeneratorService.generateAndUploadQrCode(request.text());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}

	}

}
