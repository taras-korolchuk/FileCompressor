package taras.korolchuk.filecompressor.services.compression;

import org.springframework.stereotype.Service;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class XZCompressor implements Compressor {

    @Override
    public byte[] compressByteArray(byte[] input) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             XZOutputStream xzOutputStream = new XZOutputStream(byteArrayOutputStream, new LZMA2Options())) {
            xzOutputStream.write(input);
            xzOutputStream.finish();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(FAILED_TO_COMPRESS_DATA_EXCEPTION, e);
        }
    }

    @Override
    public String getCompressedFileExtension() {
        return ".xz";
    }

    @Override
    public String getShortName() {
        return "XZ compressor";
    }
}

