-- 创建触发器,注意语句不要格式化，不要换行！
CREATE TRIGGER insert_literature_gmt_create BEFORE INSERT ON literature FOR EACH ROW BEGIN SET new.gmt_create = now(); END;
CREATE TRIGGER update_literature_gmt_modified BEFORE UPDATE ON literature FOR EACH ROW BEGIN SET new.gmt_modified = now(); END;

CREATE TRIGGER insert_help_record_gmt_create BEFORE INSERT ON help_record FOR EACH ROW BEGIN SET new.gmt_create = now(); END;
CREATE TRIGGER update_help_record_gmt_modified BEFORE UPDATE ON help_record FOR EACH ROW BEGIN SET new.gmt_modified = now(); END;

CREATE TRIGGER insert_audit_msg_gmt_create BEFORE INSERT ON audit_msg FOR EACH ROW BEGIN SET new.gmt_create = now(); END;
CREATE TRIGGER update_audit_msg_gmt_modified BEFORE UPDATE ON audit_msg FOR EACH ROW BEGIN SET new.gmt_modified = now(); END;

CREATE TRIGGER insert_give_record_gmt_create BEFORE INSERT ON give_record FOR EACH ROW BEGIN SET new.gmt_create = now(); END;
CREATE TRIGGER update_give_record_gmt_modified BEFORE UPDATE ON give_record FOR EACH ROW BEGIN SET new.gmt_modified = now(); END;


-- 初始化测试数据
-- INSERT INTO literature (doc_title, doc_href) VALUES ("ceshi title1", "http://hnlat.test1.com"), ("ceshi title2", "http://hnlat.test2.com");
-- UPDATE literature SET doc_filename = "asdfasdf.pdf" WHERE doc_title = "ceshi title1";
insert into audit_msg (msg) values ("文不对题"),("文档无法打开"),("文档错误");
insert into literature (doc_title,doc_href,doc_filename) select title,url,path FROM spischolar.t_delivery GROUP BY title,url,path;
insert into help_record (helper_email,literature_id) select t1.email,t2.id from spischolar.t_delivery t1, literature t2 where t1.title = t2.doc_title and t1.url = t2.doc_href;