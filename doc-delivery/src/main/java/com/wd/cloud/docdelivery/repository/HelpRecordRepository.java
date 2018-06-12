package com.wd.cloud.docdelivery.repository;

import com.wd.cloud.docdelivery.domain.HelpRecord;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description:
 */
public interface HelpRecordRepository extends JpaRepository<HelpRecord, Long>, JpaSpecificationExecutor<HelpRecord> {

    /**
     * 查询
     *
     * @param id
     * @param status
     * @return
     */
    HelpRecord findByIdAndStatus(long id, int status);

    HelpRecord findByIdAndStatusNot(long id, int status);

    HelpRecord findByIdAndStatusNotIn(long id, int[] status);

    /**
     * 根据求助用户ID查询
     *
     * @param helperId
     * @param pageable
     * @return
     */
    Page<HelpRecord> findByHelperId(long helperId, Pageable pageable);

    /**
     * 根据求助邮箱查询
     *
     * @param helperEmail
     * @param pageable
     * @return
     */
    Page<HelpRecord> findByHelperEmail(String helperEmail, Pageable pageable);

    /**
     * 根据互助状态查询
     *
     * @param status
     * @param pageable
     * @return
     */
    Page<HelpRecord> findByStatus(int status, Pageable pageable);

    Page<HelpRecord> findByHelpChannelAndStatus(int helpChannel, int status, Pageable pageable);

    Page<HelpRecord> findByHelpChannelAndStatusIn(int helpChannel, int[] status, Pageable pageable);


}
