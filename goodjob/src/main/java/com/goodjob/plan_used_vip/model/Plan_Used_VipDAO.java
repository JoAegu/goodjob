package com.goodjob.plan_used_vip.model;

import java.util.*;

import com.goodjob.notice.model.NoticeDTO;
public interface Plan_Used_VipDAO {

	public List<NoticeDTO> comUsedVIP(int idx, int cp, int ls);
}