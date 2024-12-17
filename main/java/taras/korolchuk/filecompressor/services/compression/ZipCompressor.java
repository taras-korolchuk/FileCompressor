package taras.korolchuk.filecompressor.services.compression;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipCompressor implements Compressor {

    @Override
    public byte[] compressByteArray(byte[] input) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {

            ZipEntry zipEntry = new ZipEntry("file"); // Name of the entry in the ZIP file
            zipOutputStream.putNextEntry(zipEntry);

            zipOutputStream.write(input);
            zipOutputStream.closeEntry(); // Close the current ZIP entry

            zipOutputStream.finish();

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(FAILED_TO_COMPRESS_DATA_EXCEPTION, e);
        }
    }

    @Override
    public String getCompressedFileExtension() {
        return ".zip";
    }

    @Override
    public String getShortName() {
        return "Zip compressor";
    }
}