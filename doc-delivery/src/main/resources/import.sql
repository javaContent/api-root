-- 创建触发器
CREATE TRIGGER insert_literature_gmt_create
  BEFORE INSERT
  ON literature
  FOR EACH ROW
  BEGIN SET new.gmt_create = now();
  END;
CREATE TRIGGER update_literature_gmt_modified
  BEFORE UPDATE
  ON literature
  FOR EACH ROW
  BEGIN SET new.gmt_modified = now();
  END;

CREATE TRIGGER insert_help_record_gmt_create
  BEFORE INSERT
  ON help_record
  FOR EACH ROW
  BEGIN SET new.gmt_create = now();
  END;
CREATE TRIGGER update_help_record_gmt_modified
  BEFORE UPDATE
  ON help_record
  FOR EACH ROW
  BEGIN SET new.gmt_modified = now();
  END;

-- 初始化测试数据
INSERT INTO literature (doc_title, doc_href)
VALUES ("ceshi title1", "http://hnlat.test1.com"), ("ceshi title2", "http://hnlat.test2.com");

UPDATE literature
SET doc_filename = "asdfasdf.pdf"
WHERE doc_title = "ceshi title1";
