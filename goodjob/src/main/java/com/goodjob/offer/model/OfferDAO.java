package com.goodjob.offer.model;

import java.util.List;
import java.util.Map;

import com.goodjob.notice.model.NoticeDTO;


public interface OfferDAO {

	public List<OfferDTO> ofComList(int idx,int cp,int ls);
	public int offerTotalCnt(int idx);
	public List<NoticeDTO> ofNorList(int member_idx, int cp, int ls, String keyword);
	public int offerNorTotalCnt(int member_idx);
	public int ofNorGetCheck(int member_idx, int offer_idx);
	public int offerSubmit(OfferDTO dto);
	public int offerCount(Map map);
}
