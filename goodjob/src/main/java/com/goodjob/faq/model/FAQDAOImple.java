package com.goodjob.faq.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

public class FAQDAOImple implements FAQDAO {

	public SqlSessionTemplate sqlMap;
	
	public SqlSessionTemplate getSqlMap() {
		return sqlMap;
	}

	public void setSqlMap(SqlSessionTemplate sqlMap) {
		this.sqlMap = sqlMap;
	}

	@Override
	public int manFAQAdd(FAQDTO dto) {
		
		int count=sqlMap.insert("manFAQAdd", dto);
		
		return count;
	}
	
	@Override
	public int manFAQAllCnt() {
		
		int count=sqlMap.selectOne("manFAQAllCnt");
		
		count=count==0?1:count;
	
		return count;
	}
	
	@Override
	public List<FAQDTO> manFAQList(int cp, int ls) {
		Map map=new HashMap();
		int start=(cp-1)*ls+1;
		int end=cp*ls;
		map.put("start", start);
		map.put("end", end);
		List<FAQDTO> lists=sqlMap.selectList("manFAQList", map);
		
		return lists;
	}
	
	@Override
	public FAQDTO manFAQContent(int idx) {
		
		FAQDTO dto=sqlMap.selectOne("manFAQContent", idx);
		
		return dto;
	}
	
	@Override
	public int manFAQUpdate(FAQDTO dto) {
		
		int count=sqlMap.update("manFAQUpdate", dto);
		
		return count;
	}
	
	@Override
	public int manFAQDel(int idx) {
		
		int count=sqlMap.delete("manFAQDel", idx);
		
		return count;
	}
}