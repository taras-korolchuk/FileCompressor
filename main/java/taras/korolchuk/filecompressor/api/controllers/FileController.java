package taras.korolchuk.filecompressor.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import taras.korolchuk.filecompressor.api.model.FileModel;
import taras.korolchuk.filecompressor.domain.entity.FileEntity;
import taras.korolchuk.filecompressor.services.FileCompressionService;
import taras.korolchuk.filecompressor.services.FileCrudService;

import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileCrudService fileCrudService;

    private final FileCompressionService fileCompressionService;

    private final ObjectMapper objectMapper;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FileModel>> getFilesByUser(@PathVariable Long userId) {
        List<FileEntity> files = fileCrudService.getFilesByUserId(userId);
        List<FileModel> converted = files.stream()
                .map(this::convertToFileModel)
                .toList();
        return ResponseEntity.ok(converted);
    }

    private FileModel convertToFileModel(FileEntity file) {
        return new FileModel(
                file.getId(),
                file.getFileName(),
                file.getCreationDate(),
                file.getContent() != null ? file.getContent().length : 0
        );
    }

    @PostMapping("/compress/{fileId}")
    public ResponseEntity<ByteArrayResource> compressFile(@PathVariable Long fileId, @RequestParam(required = false) String algorithm) {
        FileEntity compressedFile = fileCompressionService.compressFile(fileId, algorithm);
        ByteArrayResource resource = new ByteArrayResource(compressedFile.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + compressedFile.getFileName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        fileCrudService.deleteFile(fileId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<FileModel> updateFileName(@PathVariable Long fileId, @RequestBody String newFileName) {
        FileModel updatedFileModel = objectMapper.convertValue(fileCrudService.updateFileName(fileId, newFileName), FileModel.class);
        return ResponseEntity.ok(updatedFileModel);
    }

    @PostMapping("/upload")
    public ResponseEntity<FileModel> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        try {
            FileModel savedFile = fileCrudService.saveFile(file, userId);
            return ResponseEntity.ok(savedFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading file", e);
        }
    }
}
