package taras.korolchuk.filecompressor.services.compression;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

@Service
public class GZIPCompressor implements Compressor {
    @Override
    public byte[] compressByteArray(byte[] input) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(input);
            gzipOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(FAILED_TO_COMPRESS_DATA_EXCEPTION, e);
        }
    }

    @Override
    public String getCompressedFileExtension() {
        return ".gz";
    }

    @Override
    public String getShortName() {
        return "GZIP compressor";
    }
}

