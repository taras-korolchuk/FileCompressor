package taras.korolchuk.filecompressor.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import taras.korolchuk.filecompressor.api.model.FileModel;
import taras.korolchuk.filecompressor.domain.entity.FileEntity;
import taras.korolchuk.filecompressor.domain.repository.FileRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Gson gson;

    @MockBean
    private FileRepository fileRepository;

    @BeforeEach
    void setup() {
        final FileEntity file = new FileEntity();
        file.setId(1L);
        file.setFileName("File1");

        Mockito.doReturn(List.of(file)).when(fileRepository).findByUserId(1L);
        Mockito.doReturn(Optional.of(file)).when(fileRepository).findById(1L);
    }

    @Test
    void getFileByUser() {
        Type listType = new TypeToken<List<FileModel>>() {
        }.getType();
        final String jsonResult = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/files/user/1",
                String.class).getBody();
        final List<FileModel> result = gson.fromJson(jsonResult, listType);
        assertThat(result).hasSize(1);
        assertThat(result)
                .extracting(FileModel::getFileName, FileModel::getId)
                .containsExactly(tuple("File1", 1L));
    }

    @Test
    void updateFileName() {
        final String newName = "File2";
        this.restTemplate.put("http://localhost:" + port + "/files/1", newName);
        final ArgumentCaptor<FileEntity> captor = ArgumentCaptor.forClass(FileEntity.class);
        Mockito.verify(fileRepository, Mockito.times(1)).save(captor.capture());
        assertThat(captor.getValue().getFileName()).isEqualTo(newName);
    }
}

