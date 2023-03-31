package com.goodjob.blacklist.model;

import java.util.*;

import com.goodjob.member.model.MemberDTO;

public interface BlackListDAO {

	public List<MemberDTO> manBlackListGet(Map map);
	public int manBlackListTotalCnt(String category);
	public List<BlackListDTO> manBlackListContent(Map map);
	public String manBlackListGetName(int idx);
	public int manBlackListContentTotalCnt(int idx);
	public int manBlackListDel(int idx);
	public int manBlackListSingoDel(int idx);
}
