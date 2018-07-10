package com.wd.cloud.searchserver.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.SystemException;

import com.wd.cloud.searchserver.entity.AuthorityDatabase;
import com.wd.cloud.searchserver.entity.ShouluDetail;
import com.wd.cloud.searchserver.entity.ShouluMap;

public class DetailParserUtil {
	
	public static ShouluDetail parse(String detailStr){
		if(StringUtils.isEmpty(detailStr)){
			return null;
		}
		String[] items=detailStr.split("\\^");
		if(items.length != 5){
			return null;
		}
		ShouluDetail detail=new ShouluDetail();
		detail.setDb(items[0]);
		detail.setYear(items[1]);
		detail.setSubject(items[2]);
		if(items[3].matches("\\d+")){
			detail.setPartition(Integer.parseInt(items[3]));
		}
		if(items[4].matches("[\\d\\.]+")){
			detail.setImfact(Double.parseDouble(items[4]));
		}
		return detail;
	}
	
	public static List<ShouluDetail> parseAll(String str){
		List<ShouluDetail> list=new ArrayList<ShouluDetail>();
		if(StringUtils.isEmpty(str)){
			return list ;
		}
		String[] items=str.split(";");
		ShouluDetail detail=null;
		for(String s : items){
			detail=parse(s);
			if(detail!=null){
				list.add(detail);
			}
		}
		return list;
	}
	
	public static ShouluMap transToMap(List<ShouluDetail> details){
		ShouluMap map=new ShouluMap();
		List<String> years=new ArrayList<String>(),subjects=new ArrayList<String>();
		Map<String,String> yearSubject = new HashMap<String, String>();
		List<Double> impacts = new ArrayList<Double>();
		List<Integer> partitions = new ArrayList<Integer>();
		Map<String, List<String>> partitions2 =new HashMap<String, List<String>>();
		String year=null;
		for(ShouluDetail detail : details){
			year=detail.getYear();
			String subject=detail.getSubject();
			if(yearSubject.get(subject)!=null){
				yearSubject.put(subject, yearSubject.get(subject)+";"+year);
			}else{
				yearSubject.put(subject, year);
			}
			if(!years.contains(year)){
				years.add(detail.getYear());
				if(detail.getImfact() != null){
					impacts.add(detail.getImfact());
				}else{
					//impacts.add(0.00);
					impacts.add(-1.00);
				}
				if(detail.getPartition()!=null && detail.getPartition() != 0){
					partitions.add(detail.getPartition());
				}
			}
			if(!subjects.contains(detail.getSubject())){
				subjects.add(detail.getSubject());
			}
		}
		for(String sb:subjects){
			List<String> par=new ArrayList<String>();
			for(String y:years){
				int size=par.size();
				List<String> partitionYears=new ArrayList<String>();
				for(ShouluDetail det : details){
					if(det.getSubject().equals(sb)&&det.getYear().equals(y)&&!partitionYears.contains(det.getYear())){
						if(det.getPartition()!=null && det.getPartition() != 0){
							partitionYears.add(det.getYear());
							par.add(det.getPartition().toString());
						}
					}
				}
				if(par.size()==size){
					par.add("");
				}
			}
			partitions2.put(sb, par);
		}
		map.setYears(years);
		map.setYearSubject(yearSubject);
		map.setSubjects(subjects);
		map.setPartitions2(partitions2);
		if(impacts.size()!=0){
			double imp = 0.00;
			for(double i:impacts){
				imp+=i;
			}
			/*if(imp>0){   单独只有2015年数据且影响因子为0时无法显示图片
				map.setImpacts(impacts);
			}else{
				map.setHasImpact(false);
			}*/ 
			if(imp>=0){   //单独只有2015年数据且影响因子为0时无法显示图片
				map.setImpacts(impacts);
			}else{
				map.setHasImpact(false);
			}
		}else{
			map.setHasImpact(false);
		}
		if(partitions.size()!=0){
			map.setPartitions(partitions);
		}else{
			map.setHasPartition(false);
		}
		return map;
	}
	
	private static AuthorityDatabase getDB(List<AuthorityDatabase> dbs,String dbName){
		if(dbs==null || dbs.size()==0){
			return null;
		}
		for(AuthorityDatabase db : dbs){
			if(dbName.equals(db.getFlag())){
				return db;
			}
		}
		return null;
	}
	
	public static Map<String,ShouluMap> parseShoulu(List<Map<String, Object>> shoulus) throws SystemException{
		final List<AuthorityDatabase> dbs= new ArrayList<AuthorityDatabase>();
		AuthorityDatabase authorityDatabase = new AuthorityDatabase();
		authorityDatabase.setFlag("SCI-E");
		authorityDatabase.setPriority(100);
		authorityDatabase.setPrefix("Q");
		dbs.add(authorityDatabase);
		authorityDatabase = new AuthorityDatabase();
		authorityDatabase.setFlag("中科院JCR分区(小类)");
		authorityDatabase.setPriority(30);
		authorityDatabase.setSuffix("区");
		dbs.add(authorityDatabase);
		authorityDatabase = new AuthorityDatabase();
		authorityDatabase.setFlag("中科院JCR分区(大类)");
		authorityDatabase.setPriority(40);
		authorityDatabase.setSuffix("区");
		dbs.add(authorityDatabase);
		authorityDatabase = new AuthorityDatabase();
		authorityDatabase.setFlag("CSCD");
		authorityDatabase.setPriority(70);
		dbs.add(authorityDatabase);
		authorityDatabase = new AuthorityDatabase();
		authorityDatabase.setFlag("Eigenfactor");
		authorityDatabase.setPriority(20);
		dbs.add(authorityDatabase);
		authorityDatabase = new AuthorityDatabase();
		authorityDatabase.setFlag("CSSCI");
		authorityDatabase.setPriority(60);
		dbs.add(authorityDatabase);
		authorityDatabase = new AuthorityDatabase();
		authorityDatabase.setFlag("北大核心");
		authorityDatabase.setPriority(50);
		dbs.add(authorityDatabase);
		authorityDatabase = new AuthorityDatabase();
		authorityDatabase.setFlag("SSCI");
		authorityDatabase.setPriority(90);
		authorityDatabase.setPrefix("Q");
		dbs.add(authorityDatabase);
		authorityDatabase = new AuthorityDatabase();
		authorityDatabase.setFlag("SCOPUS");
		authorityDatabase.setPriority(80);
		authorityDatabase.setPrefix("Q");
		dbs.add(authorityDatabase);
		authorityDatabase = new AuthorityDatabase();
		authorityDatabase.setFlag("ESI");
		authorityDatabase.setPriority(85);
		dbs.add(authorityDatabase);
		authorityDatabase = new AuthorityDatabase();
		authorityDatabase.setFlag("EI");
		authorityDatabase.setPriority(82);
		dbs.add(authorityDatabase);
		
		Map<String,ShouluMap> result=new TreeMap<String,ShouluMap>(new Comparator<String>(){

			@Override
			public int compare(String str, String str2) {
				AuthorityDatabase db1=getDB(dbs,str),db2=getDB(dbs,str2);
				return db2.getPriority()-db1.getPriority();
			}
			
		});
		String db = null , detail = null;
		List<Map<String,Object>> detailList = null;
		List<ShouluDetail> shouluList=null;
		
		ShouluMap sm=null;
		AuthorityDatabase adb=null;
		for(Map<String, Object> map : shoulus){
			db=(String)map.get("authorityDatabase");
			detailList=(List<Map<String,Object>>)map.get("detailList");
			shouluList=new ArrayList<ShouluDetail>();
			for(Map<String,Object> m : detailList){
				detail = (String)m.get("detail");
				shouluList.addAll(parseAll(detail));
			}
			Collections.sort(shouluList);
			sm=transToMap(shouluList);
			adb=getDB(dbs,db);
			if(adb!=null){
				sm.setPrefix(adb.getPrefix());
				sm.setStuffix(adb.getSuffix());
			}
			result.put(db, sm);
		}
		return result;
	}

}
