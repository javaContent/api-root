package com.wd.cloud.docdelivery.repository;

import com.wd.cloud.docdelivery.entity.Literature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description:
 */
public interface LiteratureRepository extends JpaRepository<Literature, Long>, JpaSpecificationExecutor<Literature> {

    /**
     * 根据文献标题查询文献元数据
     *
     * @param docTitle
     * @return
     */
    Literature findByDocTitle(String docTitle);


    /**
     * 根据文献标题和文献连接查询元数据
     *
     * @param docTitle
     * @param docHref
     * @return
     */
    Literature findByDocTitleAndDocHref(String docTitle, String docHref);

}
