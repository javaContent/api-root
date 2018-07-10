package com.wd.cloud.searchserver.repository.elastic.strategy.query;

import static org.elasticsearch.index.query.QueryBuilders.spanNearQuery;
import static org.elasticsearch.index.query.QueryBuilders.spanTermQuery;

import java.util.List;

import org.apache.lucene.queryparser.classic.QueryParserBase;
import org.apache.lucene.queryparser.xml.builders.SpanQueryBuilderFactory;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SpanNearQueryBuilder;
import org.elasticsearch.index.query.SpanTermQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wd.cloud.searchserver.repository.elastic.strategy.QueryBuilderStrategyI;
import com.wd.cloud.searchserver.util.ChineseUtil;
import com.wd.cloud.searchserver.util.SynonymsUtil;


@Component("all")
public class AllFieldQueryBuilderStrategy implements QueryBuilderStrategyI {
	
	@Override
	public QueryBuilder execute(String value, Object otherConstraint) {
		QueryBuilder queryBuilder = null;
		/*value = value.replaceAll("&", "");
		value = value.replaceAll(",", "");
		value = value.replaceAll("[ ]+-[ ]+", " ");
		value = value.replaceAll(":", "");*/
		if (ChineseUtil.isChinese(value)) {
			queryBuilder = chineseQuery(value);
		} else {
			queryBuilder = englishQuery(value);
		}

		return queryBuilder;
	}

	/**
	 * 英文搜索方式
	 * 
	 * @param value
	 * @return
	 */
	private QueryBuilder englishQuery(String value) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.queryStringQuery(value).field("docTitle").field("alais").field("issn").field("eissn").field("subjectLst").field("history.htitle").field("history.hissn").defaultOperator(Operator.OR).minimumShouldMatch("90%").boost(5));
		boolQueryBuilder.should(QueryBuilders.queryStringQuery(value).field("docTitle").analyzer("stop").minimumShouldMatch("90%").boost(5));
		boolQueryBuilder.should(QueryBuilders.termQuery("docTitle.full", value).boost(20));
		boolQueryBuilder.should(QueryBuilders.termQuery("issn", value).boost(20));
		boolQueryBuilder.should(QueryBuilders.termQuery("eissn", value).boost(20));
		boolQueryBuilder.should(QueryBuilders.fuzzyQuery("shouLu.detailList.subjects", value).fuzziness(Fuzziness.AUTO));
		boolQueryBuilder.should(QueryBuilders.boolQuery().should(QueryBuilders.matchPhrasePrefixQuery("docTitle", value).boost(10))
				.should(QueryBuilders.matchPhrasePrefixQuery("alais", value))
				.should(QueryBuilders.matchPhrasePrefixQuery("issn", value))
				.should(QueryBuilders.matchPhrasePrefixQuery("eissn", value))
				.should(QueryBuilders.matchPhrasePrefixQuery("subjectLst", value))
				.should(QueryBuilders.matchPhrasePrefixQuery("history.hissn", value))
				.should(QueryBuilders.matchPhrasePrefixQuery("history.htitle", value))
				.minimumShouldMatch(1)
				);
		return boolQueryBuilder;
	}

	/**
	 * 中文搜索方式
	 * 
	 * @param value
	 * @return
	 */
	private BoolQueryBuilder chineseQuery(String value) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		//使用mmseg分词器将value分词返回分词列表
		List<String> termList = SynonymsUtil.analyzer(value, "mmseg");
		if(termList.size() > 1){
			SpanNearQueryBuilder titleSpanNearSlopZeroQueryBuilder = null;
			for (String term : termList) {
				SpanTermQueryBuilder spanTermQueryBuilder = spanTermQuery("docTitle", QueryParserBase.escape(term));
				if (null == titleSpanNearSlopZeroQueryBuilder){
					titleSpanNearSlopZeroQueryBuilder = spanNearQuery(spanTermQueryBuilder, 0);
				}else{
					titleSpanNearSlopZeroQueryBuilder.addClause(spanTermQueryBuilder);
				}
			}
			boolQueryBuilder.should(titleSpanNearSlopZeroQueryBuilder.boost(10));
		}
		
		if (value.length() > 3 && termList.size()>1) {
			SpanNearQueryBuilder titleSpanNearSlopOneQueryBuilder = null;
			for (String term : termList) {
				SpanTermQueryBuilder spanTermQueryBuilder = spanTermQuery("docTitle", QueryParserBase.escape(term));
				if (null == titleSpanNearSlopOneQueryBuilder){
					titleSpanNearSlopOneQueryBuilder = spanNearQuery(spanTermQueryBuilder, 2);
				}else{
					titleSpanNearSlopOneQueryBuilder.addClause(spanTermQueryBuilder);
				}
			}
			boolQueryBuilder.should(titleSpanNearSlopOneQueryBuilder.boost(10));
		}
		boolQueryBuilder.should(QueryBuilders.fuzzyQuery("shouLu.detailList.subjects",value).fuzziness(Fuzziness.AUTO));
		boolQueryBuilder.should(QueryBuilders.termQuery("docTitle.full", value).boost(20));
		boolQueryBuilder.should(QueryBuilders.termQuery("issn", value));

		boolQueryBuilder.should(QueryBuilders.queryStringQuery(value).field("alais").field("subjectLst").field("history.htitle").defaultOperator(Operator.OR).minimumShouldMatch("90%").boost(5));
		boolQueryBuilder.should(QueryBuilders.boolQuery().should(QueryBuilders.matchPhrasePrefixQuery("docTitle", value))
				.should(QueryBuilders.matchPhrasePrefixQuery("alais", value))
				.should(QueryBuilders.matchPhrasePrefixQuery("issn", value))
				.should(QueryBuilders.matchPhrasePrefixQuery("subjectLst", value))
				.should(QueryBuilders.matchPhrasePrefixQuery("history.htitle", value))
				.minimumShouldMatch(1)
				);
		return boolQueryBuilder;
	}
}
