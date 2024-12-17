package taras.korolchuk.filecompressor.services.compression;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class TarGzCompressor implements Compressor {

    @Override
    public byte[] compressByteArray(byte[] input) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             BufferedOutputStream buffOut = new BufferedOutputStream(byteArrayOutputStream);
             GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(buffOut);
             TarArchiveOutputStream tOut = new TarArchiveOutputStream(gzOut)) {

            // Create an entry for the data
            TarArchiveEntry entry = new TarArchiveEntry("compressedData");
            entry.setSize(input.length);

            // Add the entry to the archive
            tOut.putArchiveEntry(entry);

            // Write the data
            tOut.write(input);

            // Close the entry
            tOut.closeArchiveEntry();

            // Finish writing
            tOut.finish();

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(FAILED_TO_COMPRESS_DATA_EXCEPTION, e);
        }
    }

    @Override
    public String getCompressedFileExtension() {
        return ".tar.gz";
    }

    @Override
    public String getShortName() {
        return "TarGz compressor";
    }
}