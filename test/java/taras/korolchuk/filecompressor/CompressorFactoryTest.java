package taras.korolchuk.filecompressor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import taras.korolchuk.filecompressor.services.CompressorFactory;
import taras.korolchuk.filecompressor.services.compression.Compressor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CompressorFactoryTest {

    @Autowired
    private CompressorFactory target;

    @ParameterizedTest
    @CsvSource({
            ".gz, taras.korolchuk.filecompressor.services.compression.GZIPCompressor",
            ".tar.gz, taras.korolchuk.filecompressor.services.compression.TarGzCompressor",
            ".xz, taras.korolchuk.filecompressor.services.compression.XZCompressor",
            ".zip, taras.korolchuk.filecompressor.services.compression.ZipCompressor"
    })
    void getByExtension(final String extension, final Class<? extends Compressor> clazz) {
        assertThat(target.getByExtension(extension)).isInstanceOf(clazz);
    }

    @Test
    void getAll() {
        assertThat(target.getAll()).hasSize(5);
    }
}
