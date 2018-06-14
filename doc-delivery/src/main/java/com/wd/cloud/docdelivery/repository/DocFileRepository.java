package com.wd.cloud.docdelivery.repository;

import com.wd.cloud.docdelivery.entity.DocFile;
import com.wd.cloud.docdelivery.entity.Literature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/27
 * @Description:
 */
public interface DocFileRepository extends JpaRepository<DocFile, Long> {

    DocFile findByFileName(String fileName);

    DocFile findByLiteratureAndReusingIsTrue(Literature literature);

    DocFile findByLiteratureAndFileNameAndFileType(Literature literature, String fileName, String fileType);

    List<DocFile> findByLiterature(Literature literature);

}
