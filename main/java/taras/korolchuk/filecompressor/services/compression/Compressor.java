package taras.korolchuk.filecompressor.services.compression;


public interface Compressor {

    String FAILED_TO_COMPRESS_DATA_EXCEPTION = "Failed to compress data";
    /**
     * Compresses byte array with specified algorithm
     *
     * @param input - uncomressed byte array
     * @return compressed byte array
     */
    byte[] compressByteArray(byte[] input);

    /**
     * Returns extension for the compressed file related to algorithm compression (for example, ".gz" for GZIP).
     *
     * @return extension for the compressed file
     */
    String getCompressedFileExtension();

    String getShortName();
}
