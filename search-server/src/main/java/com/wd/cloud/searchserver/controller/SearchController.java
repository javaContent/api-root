package com.wd.cloud.searchserver.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.searchserver.entity.SearchCondition;
import com.wd.cloud.searchserver.entity.SearchResult;
import com.wd.cloud.searchserver.service.SearchServiceI;
import com.wd.cloud.searchserver.util.PinYinUtil;
import com.wd.cloud.searchserver.util.SystemContext;

import net.sf.json.JSONArray;


/**
 * SearchController class
 *
 * @author hezhigang
 * @date 2018/04/08
 */
@RestController
public class SearchController {

    @Autowired
    private SearchServiceI searchInterfaceService;

    /**
     * 期刊首页
     *
     * @param subjectNameId
     * @param id
     * @param year
     * @param request
     * @return
     */
    @RequestMapping("/subject/{subjectNameId}/{id}/{year}")
    public SearchResult subjectList(
            @PathVariable int subjectNameId,
            @PathVariable int id,
            @PathVariable Integer year,
            @RequestParam(value = "subject", required = false) String subject,
            HttpServletRequest request) {
        SearchCondition condition = new SearchCondition();
        String[] dbs = {"SCI-E", "中科院JCR分区(小类)", "中科院JCR分区(大类)", "CSCD", "Eigenfactor", "CSSCI", "北大核心", "SSCI", "SCOPUS", "ESI", "EI"};
        String[] subjectNames = {"人文社科类", "理学", "工学", "农学", "医学", "综合"};
        String db = dbs[id - 1];
        String subjectName = subjectNames[subjectNameId - 1];
        condition.addQueryCdt("scSName_3_1_" + subjectName);
        condition.addQueryCdt("scDB_3_1_" + db);
        condition.addQueryCdt("scYear_3_1_" + year);
        if (!StringUtils.isEmpty(subject)) {
            condition.addQueryCdt("scDis_3_1_" + subject);
        }
        SearchResult searchResult = searchInterfaceService.searchSubjectSystem(condition);
        return searchResult;
    }


    @RequestMapping("/category/list")
    public SearchResult category(HttpServletRequest request,
                                 @RequestBody SearchCondition condition,
                                 @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                 @RequestParam(value = "size", required = false, defaultValue = "25") Integer size) {

        long start = System.currentTimeMillis();
        int startYear = condition.getDetailYear();
        SystemContext.setOffset(page * size);
        SystemContext.setPageSize(size);
        SearchResult searchResult = searchInterfaceService.search(condition);
        categoryChuli(searchResult, startYear);
        long end = System.currentTimeMillis();
        searchResult.setTime((end - start) / (float) 1000);
        return searchResult;
    }

    public void categoryChuli(SearchResult searchResult, int startYear) {
        List<Map<String, Object>> listDatas = searchResult.getDatas();
        List<Map<String, Object>> datasList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> mapDatas : listDatas) {
            String jGuid = (String) mapDatas.get("_id");
            String mainLink = null;
            if (mainLink != null) {
                mapDatas.put("mainLink", mainLink);
            } else {
                mapDatas.put("mainLink", "");
            }
            ArrayList shouLuList = (ArrayList) mapDatas.get("shouLu");
            for (int i = 0; i < shouLuList.size(); i++) {
                Map<String, Object> shouLuMap = (Map<String, Object>) shouLuList.get(i);
                if ("CSCD".equals(shouLuMap.get("authorityDatabase")) || "CSSCI".equals(shouLuMap.get("authorityDatabase"))) {
                    ArrayList detailList = (ArrayList) shouLuMap.get("detailList");
                    int year = 0;
                    String core = null;
                    for (int j = 0; j < detailList.size(); j++) {
                        Map<String, Object> map = (Map<String, Object>) detailList.get(j);
                        String cor = (String) map.get("core");
                        int detailYear = (int) map.get("detailYear");
                        if (detailYear > year && detailYear <= startYear) {
                            year = detailYear;
                            core = cor;
                        }
                    }
                    mapDatas.put("core", core);
                    mapDatas.put("coreInfo", core);
                }
            }
            datasList.add(mapDatas);
        }
        searchResult.setDatas(datasList);
    }


    @RequestMapping("/search/list")
    public SearchResult search(HttpServletRequest request,
                               @RequestBody SearchCondition condition,
                               @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                               @RequestParam(value = "size", required = false, defaultValue = "25") Integer size) {
        long start = System.currentTimeMillis();
        SystemContext.setOffset(page * size);
        SystemContext.setPageSize(size);

        SearchResult searchResult = searchInterfaceService.search(condition);
        searchChuli(searchResult, condition.getAuthorityDb());
        Map<String, Map<String, String>> facetMap = searchInterfaceService.searchDisciplineSystem(condition);
        searchResult.setFacetDatas(facetMap);
        long end = System.currentTimeMillis();
        searchResult.setTime((end - start) / (float) 1000);
        return searchResult;
    }

    public void searchChuli(SearchResult searchResult, String authorityDb) {
        List<Map<String, Object>> listDatas = searchResult.getDatas();
        List<Map<String, Object>> datasList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> mapDatas : listDatas) {
            ArrayList shouLuList = (ArrayList) mapDatas.get("shouLu");
            for (int i = 0; i < shouLuList.size(); i++) {
                Map<String, Object> shouLuMap = (Map<String, Object>) shouLuList.get(i);
                if (shouLuMap.get("authorityDatabase").equals(authorityDb) && ("CSCD".equals(shouLuMap.get("authorityDatabase")) || "CSSCI".equals(shouLuMap.get("authorityDatabase")))) {
                    ArrayList detailList = (ArrayList) shouLuMap.get("detailList");
                    int year = 0;
                    String core = null;
                    for (int j = 0; j < detailList.size(); j++) {
                        Map<String, Object> map = (Map<String, Object>) detailList.get(j);
                        String cor = (String) map.get("core");
                        int detailYear = (int) map.get("detailYear");
                        if (detailYear > year) {
                            year = detailYear;
                            core = cor;
                        }
                    }
                    String info = shouLuMap.get("authorityDatabase") + " " + year + " " + core + "版";
                    if ("CSSCI".equals(shouLuMap.get("authorityDatabase"))) {
                        info = shouLuMap.get("authorityDatabase") + " " + (year - 1) + "-" + year + " " + core + "版";
                    }
                    String coreInfo = (String) mapDatas.get("coreInfo");
                    if (coreInfo != null && "扩展".equals(core)) {
                        coreInfo += "&#10;" + info;
                        mapDatas.put("coreInfo", coreInfo);
                    } else {
                        if ("扩展".equals(core)) {
                            mapDatas.put("core", core);
                            mapDatas.put("coreInfo", info);
                        }
                    }
                }
            }
            datasList.add(mapDatas);
        }
        searchResult.setDatas(datasList);
    }




    @RequestMapping("/detail/{id}")
    public Map<String, Object> detail(HttpServletRequest request,
                                      @PathVariable String id) {
        Map<String, Object> doc = searchInterfaceService.getDoc(id);
        return doc;
    }

    @RequestMapping("/detail/ids")
    public List<Map<String, Object>> detailByIds(HttpServletRequest request,
                                                 String[] ids) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < ids.length; i++) {
            Map<String, Object> doc = searchInterfaceService.getDoc(ids[i]);
            list.add(doc);
        }
        return list;
    }


    @RequestMapping("/subjectJSON")
    public ResponseModel subjectJSON(HttpServletRequest request,
                                     String db, String year) {
        if (StringUtils.isBlank(db) || StringUtils.isBlank(year)) {
            return ResponseModel.ok(JSONArray.fromObject(new String[]{}));
        }
        SearchCondition condition = new SearchCondition();
        condition.setSearchComponentFlag("subject_system_search");
        condition.addQueryCdt("scDB_3_1_" + db);
        condition.addQueryCdt("scYear_3_1_" + year);
        SearchResult result = searchInterfaceService.searchSubjectSystem(condition);
        List<Map<String, Object>> datas = result.getDatas();
        Collections.sort(datas, new Comparator<Map<String, Object>>() {

            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String name1 = (String) o1.get("discipline"), name2 = (String) o2.get("discipline"), p1, p2;
                p1 = PinYinUtil.getPingYin(name1);
                p2 = PinYinUtil.getPingYin(name2);
                return p1.compareTo(p2);
            }
        });
        return ResponseModel.ok(JSONArray.fromObject(datas));
    }


}
