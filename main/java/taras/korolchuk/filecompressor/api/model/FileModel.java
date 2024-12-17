package taras.korolchuk.filecompressor.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class FileModel {
    private Long id;
    private String fileName;
    private Date creationDate;
    private int contentSize;
}
