package taras.korolchuk.filecompressor.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taras.korolchuk.filecompressor.domain.entity.FileEntity;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    // Custom methods can also be added here, for example:
     List<FileEntity> findByUserId(Long userId);
}
