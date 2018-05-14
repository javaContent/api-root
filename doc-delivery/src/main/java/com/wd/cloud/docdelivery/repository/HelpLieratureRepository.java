package com.wd.cloud.docdelivery.repository;

import com.wd.cloud.docdelivery.domain.HelpLierature;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author He Zhigang
 * @date 2018/5/11
 * @Description:
 */
public interface HelpLieratureRepository extends MongoRepository<HelpLierature,ObjectId>{


}
