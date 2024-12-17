package taras.korolchuk.filecompressor.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import taras.korolchuk.filecompressor.domain.entity.FileEntity;
import taras.korolchuk.filecompressor.domain.repository.FileRepository;
import taras.korolchuk.filecompressor.services.compression.Compressor;


@Service
@RequiredArgsConstructor
public class FileCompressionService {

    private final FileRepository fileRepository;

    private final CompressorFactory compressorFactory;

    public FileEntity compressFile(Long fileId, String algorithm) {
        FileEntity file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));

        final Compressor compressor = compressorFactory.getByExtension(algorithm);

        file.setContent(compressor.compressByteArray(file.getContent()));
        file.setFileName(file.getFileName() + compressor.getCompressedFileExtension());

        return file; // Return the compressed file
    }
}

