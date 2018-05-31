-- 创建触发器,注意语句不要格式化，不要换行！
CREATE TRIGGER insert_literature_gmt_create BEFORE INSERT ON literature FOR EACH ROW BEGIN SET new.gmt_create = now(); END;
CREATE TRIGGER update_literature_gmt_modified BEFORE UPDATE ON literature FOR EACH ROW BEGIN SET new.gmt_modified = now(); END;

CREATE TRIGGER insert_help_record_gmt_create BEFORE INSERT ON help_record FOR EACH ROW BEGIN SET new.gmt_create = now(); END;
CREATE TRIGGER update_help_record_gmt_modified BEFORE UPDATE ON help_record FOR EACH ROW BEGIN SET new.gmt_modified = now(); END;

CREATE TRIGGER insert_audit_msg_gmt_create BEFORE INSERT ON audit_msg FOR EACH ROW BEGIN SET new.gmt_create = now(); END;
CREATE TRIGGER update_audit_msg_gmt_modified BEFORE UPDATE ON audit_msg FOR EACH ROW BEGIN SET new.gmt_modified = now(); END;

CREATE TRIGGER insert_give_record_gmt_create BEFORE INSERT ON give_record FOR EACH ROW BEGIN SET new.gmt_create = now(); END;
CREATE TRIGGER update_give_record_gmt_modified BEFORE UPDATE ON give_record FOR EACH ROW BEGIN SET new.gmt_modified = now(); END;

CREATE TRIGGER insert_doc_file_gmt_create BEFORE INSERT ON doc_file FOR EACH ROW BEGIN SET new.gmt_create = now(); END;
CREATE TRIGGER update_doc_file_gmt_modified BEFORE UPDATE ON doc_file FOR EACH ROW BEGIN SET new.gmt_modified = now(); END;

-- 初始化测试数据
insert into audit_msg (msg) values ("文不对题"),("文档无法打开"),("文档错误");
-- insert into literature (doc_title,doc_href) select title,url FROM spischolar.t_delivery GROUP BY title,url,path;
-- INSERT INTO help_record ( literature_id, helper_email, help_channel, helper_scname, helper_id ) SELECT t2.id, t1.email, t1.product_id, t1.org_name, t1.member_id FROM spischolar.t_delivery t1, literature t2 WHERE t1.title = t2.doc_title AND t1.url = t2.doc_href;
-- INSERT INTO give_record ( help_record_id, auditor_id, auditor_name, giver_type ) SELECT t3.id, t1.procesor_id, t1.procesor_name, t1.process_type FROM spischolar.t_delivery t1, literature t2, help_record t3 WHERE t1.title = t2.doc_title AND t1.url = t2.doc_href AND t2.id = t3.literature_id;




drop PROCEDURE IF EXISTS give_timeout;
DROP EVENT IF EXISTS e_give_timeout;
CREATE PROCEDURE give_timeout () BEGIN DECLARE helpRecordId INT DEFAULT 0 ; SELECT help_record_id INTO helpRecordId FROM give_record WHERE doc_file_id IS NULL AND 15 < TIMESTAMPDIFF(MINUTE, gmt_create, now()) ; DELETE FROM give_record WHERE help_record_id = helpRecordId AND doc_file_id IS NULL ; UPDATE help_record SET STATUS = 0 WHERE STATUS = 1 AND id = helpRecordId ; END;
CREATE EVENT e_give_timeout ON SCHEDULE EVERY 60 SECOND STARTS TIMESTAMP '2018-05-31 00:00:00' ON COMPLETION PRESERVE DO CALL give_timeout ();
