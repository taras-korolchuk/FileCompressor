package taras.korolchuk.filecompressor.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import taras.korolchuk.filecompressor.services.compression.Compressor;

@Data
@AllArgsConstructor
public class CompressorModel {
    private String shortName;
    private String extension;

    public CompressorModel(Compressor compressor) {
        this.shortName = compressor.getShortName();
        this.extension = compressor.getCompressedFileExtension();
    }
}
