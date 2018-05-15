package com.wd.cloud.docdelivery.repository;

import com.wd.cloud.docdelivery.domain.Literature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description:
 */
public interface LiteratureRepository extends JpaRepository<Literature, Integer> {

    Literature findByDocTitle(String docTitle);

    Literature findByDocTitleAndDocHref(String docTitle,String docHref);

}
