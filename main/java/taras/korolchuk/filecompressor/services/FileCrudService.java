package taras.korolchuk.filecompressor.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import taras.korolchuk.filecompressor.api.model.FileModel;
import taras.korolchuk.filecompressor.domain.entity.FileEntity;
import taras.korolchuk.filecompressor.domain.repository.FileRepository;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileCrudService {

    private final FileRepository fileRepository;

    private final UserService userService;

    private final ObjectMapper objectMapper;

    public List<FileEntity> getFilesByUserId(Long userId) {
        return fileRepository.findByUserId(userId);
    }

    public void deleteFile(Long fileId) {
        fileRepository.deleteById(fileId);
    }

    public FileEntity updateFileName(Long fileId, String newFileName) {
        FileEntity file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
        file.setFileName(newFileName);
        return fileRepository.save(file);
    }

    public FileModel saveFile(MultipartFile multipartFile, Long userId) throws IOException {
        FileEntity file = createNewFileForUser(multipartFile, userId);

        file = fileRepository.save(file);
        return objectMapper.convertValue(file, FileModel.class);
    }

    private FileEntity createNewFileForUser(MultipartFile multipartFile, Long userId) throws IOException {
        FileEntity file = new FileEntity();
        file.setFileName(multipartFile.getOriginalFilename());
        file.setContent(multipartFile.getBytes());
        file.setCreationDate(new Date());
        file.setUser(userService.getUserById(userId).orElseThrow());
        return file;
    }
}

