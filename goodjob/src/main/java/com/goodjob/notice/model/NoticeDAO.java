package com.goodjob.notice.model;
import java.util.*;
public interface NoticeDAO {
	public int noticeWrite(NoticeDTO dto);
	public List<NoticeDTO> noticeComList(int idx,int cp,int ls);
	public int noticeTotalCnt(int idx);
	public NoticeDTO noticeContent(int idx);
	public int noticeDel(int idx);
	public List<NoticeDTO> manNoticeStatusList(int cp, int ls);
	public List<NoticeDTO> manNoticeAcceptList(int cp, int ls);
	public int manNoticeStatusCnt();
	public NoticeDTO manNoticeAcceptContent(int idx);
	public int manNoticeAcceptContent_Ok(int idx);
	public int manNoticeAcceptContent_No(int idx);
	public int manNoticeDel(int idx);
	public List<NoticeDTO> manNoticeDelList(int cp, int ls);
	public int manNoticeCnt();
}
