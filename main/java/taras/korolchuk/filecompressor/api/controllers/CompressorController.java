package taras.korolchuk.filecompressor.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taras.korolchuk.filecompressor.api.model.CompressorModel;
import taras.korolchuk.filecompressor.services.CompressorFactory;

import java.util.List;

@RestController
@RequestMapping("/compressors")
@RequiredArgsConstructor
public class CompressorController {

    private final CompressorFactory compressorFactory;

    @GetMapping("/available")
    public ResponseEntity<List<CompressorModel>> getAvailableCompressors() {
        final List<CompressorModel> converted = compressorFactory.getAll();
        return ResponseEntity.ok(converted);
    }
}
