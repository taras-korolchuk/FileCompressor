package taras.korolchuk.filecompressor.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import taras.korolchuk.filecompressor.api.model.CompressorModel;
import taras.korolchuk.filecompressor.services.compression.Compressor;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompressorFactory {

    private final Set<Compressor> compressors;

    public List<CompressorModel> getAll() {
        return compressors.stream().map(CompressorModel::new).toList();
    }

    public Compressor getByExtension(final String extension) {
        return compressors.stream()
                .filter(v -> Objects.equals(v.getCompressedFileExtension(), extension))
                .findFirst()
                .orElseThrow();
    }
}
