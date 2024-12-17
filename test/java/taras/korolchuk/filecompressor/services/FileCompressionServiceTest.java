package taras.korolchuk.filecompressor.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import taras.korolchuk.filecompressor.domain.entity.FileEntity;
import taras.korolchuk.filecompressor.domain.repository.FileRepository;
import taras.korolchuk.filecompressor.services.compression.*;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class FileCompressionServiceTest {

    @Mock
    private FileRepository fileRepository;

    private FileCompressionService target;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        CompressorFactory compressorFactory = new CompressorFactory(Set.of(new GZIPCompressor(), new ZipCompressor(),
                new XZCompressor(), new NoCompressionCompressor()));
        target = new FileCompressionService(fileRepository, compressorFactory);
        fileRepository.save(createExampleFile());
    }

    private FileEntity createExampleFile() {
        FileEntity mockFile = new FileEntity();
        mockFile.setFileName("Test");
        mockFile.setId(1L);
        mockFile.setContent(new byte[100]);
        return mockFile;
    }

    @Test
    void testCompressFileWithSupportedAlgorithm() {
        Long fileId = 1L;
        String algorithm = ".gz";
        FileEntity mockFile = createExampleFile();

        when(fileRepository.findById(fileId)).thenReturn(Optional.of(mockFile));

        FileEntity result = target.compressFile(fileId, algorithm);

        assertNotNull(result.getContent());
        assertThat(result.getFileName()).isEqualTo("Test.gz");
    }

    @Test
    void testCompressFileWithUnsupportedAlgorithm() {
        Long fileId = 1L;
        String algorithm = "UnsupportedAlgorithm";

        assertThrows(RuntimeException.class,
                () -> target.compressFile(fileId, algorithm),
                Compressor.FAILED_TO_COMPRESS_DATA_EXCEPTION);
    }

    @Test
    void testCompressNonExistentFile() {
        Long fileId = 2L;
        String algorithm = ".gzip";
        when(fileRepository.findById(fileId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> target.compressFile(fileId, algorithm));
    }
}
