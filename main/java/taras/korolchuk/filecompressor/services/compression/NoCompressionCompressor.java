package taras.korolchuk.filecompressor.services.compression;

import org.springframework.stereotype.Service;

@Service
public class NoCompressionCompressor implements Compressor {
    @Override
    public byte[] compressByteArray(byte[] input) {
        return input;
    }

    @Override
    public String getCompressedFileExtension() {
        return "";
    }

    @Override
    public String getShortName() {
        return "No compression";
    }
}

