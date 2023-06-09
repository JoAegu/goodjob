package com.goodjob.member.model;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.goodjob.companymember.model.CompanyMemberDTO;
import com.goodjob.normalmember.model.NormalMemberDTO;

public class MemberDAOImple implements MemberDAO {

	private SqlSessionTemplate sqlMap;

	public MemberDAOImple() {
		// TODO Auto-generated constructor stub
	}

	public SqlSessionTemplate getSqlMap() {
		return sqlMap;
	}



	public void setSqlMap(SqlSessionTemplate sqlMap) {
		this.sqlMap = sqlMap;
	}



	public MemberDAOImple(SqlSessionTemplate sqlMap) {
		super();
		this.sqlMap = sqlMap;
	}

	@Override
	synchronized public int memberJoin(MemberDTO dto) {
		// TODO Auto-generated method stub
		int idCheck = idCheck(dto.getId());
		int emailCheck=emailCheck(dto.getEmail());
		
		if (idCheck == 0) {
			if(emailCheck==0) {
				
			int count = sqlMap.insert("memberJoin", dto);
			if (count > 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", dto.getId());
				map.put("pwd", dto.getPwd());
				MemberDTO memDto = sqlMap.selectOne("memberSelect", map);
				return memDto.getIdx();
			} else {// 가입실패
				return 0;
			}
			}else {
				return-2;
			}
		} else {// 아이디중복
			return -1;
		}
	}

	@Override
	public MemberDTO login(String id, String pwd, String user_category) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("pwd", pwd);
		map.put("user_category", user_category);
		MemberDTO memDto = sqlMap.selectOne("login", map);
		if (memDto == null && user_category.equals("개인")) {
			map.put("user_category", "관리자");
			memDto = sqlMap.selectOne("login", map);
		}

		return memDto;
	}

	@Override
	public int memberUpdate(MemberDTO dto) {
		// TODO Auto-generated method stub
		return sqlMap.update("memberUpdate", dto);
	}@Override
	public int emailCheck(String email) {
		return sqlMap.selectOne("emailCheck",email);
	}@Override
	public int idCheck(String id) {
		return sqlMap.selectOne("idCheck",id);
	}

	
	@Override
	public int updateStatus(MemberDTO dto) {
		// TODO Auto-generated method stub
		int count =sqlMap.update("updateStatus",dto);
		return count;
	}

	@Override
	public MemberDTO selectIdx(int idx) {
		
		MemberDTO dto=sqlMap.selectOne("selectIdx", idx);
		
		return dto;
	}

	
	@Override
	public String findId(String email) {
		String id=sqlMap.selectOne("findId", email);
		return id;
	}
	@Override
	public int findPwd(String id,String email) {
		int idx=0;
		Map<String, String>map=new HashMap<String, String>();
		map.put("id", id);
		map.put("email", email);
		Integer idxs=sqlMap.selectOne("findPwd", map);
		if(idxs!=null) {
			idx=idxs;
		}else {
			idx=0;
		}
		return idx;
	}
	@Override
	public int updatePwd(String pwd, String idx) {
		Map<String, String>map=new HashMap<String, String>();
		map.put("pwd",pwd);
		map.put("idx", idx);
		int count=sqlMap.update("updatePwd", map);
		return count;
	}

}
