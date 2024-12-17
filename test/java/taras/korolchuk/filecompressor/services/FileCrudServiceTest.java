package taras.korolchuk.filecompressor.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import taras.korolchuk.filecompressor.api.model.FileModel;
import taras.korolchuk.filecompressor.domain.entity.FileEntity;
import taras.korolchuk.filecompressor.domain.entity.UserEntity;
import taras.korolchuk.filecompressor.domain.repository.FileRepository;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class FileCrudServiceTest {

    @Mock
    private FileRepository fileRepository;
    @Mock
    private UserService userService;
    @Mock
    private ObjectMapper objectMapper;

    private FileCrudService fileCrudService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileCrudService = new FileCrudService(fileRepository, userService, objectMapper);
    }

    // Test if the service correctly retrieves files by a given user ID.
    @Test
    void testGetFilesByUserId() {
        Long userId = 1L;
        List<FileEntity> expectedFiles = List.of(new FileEntity()); // Replace with actual expected files
        when(fileRepository.findByUserId(userId)).thenReturn(expectedFiles);

        List<FileEntity> actualFiles = fileCrudService.getFilesByUserId(userId);

        assertEquals(expectedFiles, actualFiles);
    }

    // Test if the service correctly deletes a file by its ID.
    @Test
    void testDeleteFile() {
        Long fileId = 1L;

        fileCrudService.deleteFile(fileId);

        verify(fileRepository).deleteById(fileId);
    }

    // Test if the service updates the file name correctly.
    @Test
    void testUpdateFileName() {
        Long fileId = 1L;
        String newFileName = "UpdatedFileName";
        FileEntity mockFile = new FileEntity();
        mockFile.setId(fileId);
        mockFile.setFileName("OriginalName");

        when(fileRepository.findById(fileId)).thenReturn(Optional.of(mockFile));
        when(fileRepository.save(any(FileEntity.class))).thenReturn(mockFile);

        FileEntity updatedFile = fileCrudService.updateFileName(fileId, newFileName);

        assertEquals(newFileName, updatedFile.getFileName());
    }

    // Test if the service saves a new file correctly.
    @Test
    void testSaveFile() throws IOException {
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        Long userId = 1L;
        UserEntity mockUser = new UserEntity();
        FileEntity mockFile = new FileEntity();

        when(mockMultipartFile.getOriginalFilename()).thenReturn("test.txt");
        when(mockMultipartFile.getBytes()).thenReturn(new byte[10]);
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));
        when(fileRepository.save(any(FileEntity.class))).thenReturn(mockFile);
        when(objectMapper.convertValue(any(FileEntity.class), eq(FileModel.class))).thenReturn(new FileModel(
                1L, "test.txt", new Date(), 10
        ));

        FileModel savedFileModel = fileCrudService.saveFile(mockMultipartFile, userId);

        assertNotNull(savedFileModel);
    }
}
