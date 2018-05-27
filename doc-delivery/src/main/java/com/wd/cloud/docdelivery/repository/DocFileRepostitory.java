package com.wd.cloud.docdelivery.repository;

import com.wd.cloud.docdelivery.domain.DocFile;
import com.wd.cloud.docdelivery.domain.Literature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/27
 * @Description:
 */
public interface DocFileRepostitory extends JpaRepository<DocFile,Long> {

    DocFile findByLiteratureAndReusingIsTrue(Literature literature);

    DocFile findByLiteratureAndFileNameAndFileType(Literature literature,String fileName,String fileType);
}
