package com.all.light.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.all.light.dao.FreeBoardDAO;
import com.all.light.dto.DiaryDTO;
import com.all.light.dto.FreeBoardDTO;
import com.all.light.dto.ReviewDTO;
import com.all.light.util.PageUtil;

@Service
public class FreeBoardService {

	@Autowired
	private FreeBoardDAO freDAO;

	public void write(FreeBoardDTO fdto, ArrayList list) {
		freDAO.write(fdto,"fboard");
		for(int i=0; i<list.size() ;i++) {
			//하나의 첨부파일의 정보는 Map으로 저장되어 있다
			HashMap map = (HashMap)list.get(i);
			/*map.put("path",    path);
			map.put("oriName", oriName);
			map.put("saveName",saveName);
			map.put("len", file.length()); 넣었으니..*/

			//DAO에서는  정보는 FileBoardDTO로 받기로 했으므로
			//--> DTO의  setter()를 이용하여 세팅한다
			fdto.setFipath((String)map.get("path"));
			fdto.setFioriname((String)map.get("oriName"));
			fdto.setFiimg((String)map.get("saveName"));
			fdto.setFilength((Long)map.get("len"));
			freDAO.write(fdto,"fInfo");
			}
	}

	public PageUtil getPageInfo(int nowPage, String searchWord, String searchType, String ftype) {
		PageUtil pInfo = new PageUtil(searchWord, searchType);
		pInfo.setFtype(ftype);
		int Cnt = freDAO.getPageInfo(pInfo);
		pInfo = new PageUtil(nowPage, Cnt);
		return pInfo;
	}
	public PageUtil getPageInfoMyPage(int nowPage, String searchWord, String searchType, String ftype, String fid) {
		PageUtil pInfo = new PageUtil(searchWord, searchType);
		pInfo.setFtype(ftype);
		pInfo.setFid(fid);
		int Cnt = freDAO.getPageInfoMyPage(pInfo);
		pInfo = new PageUtil(nowPage, Cnt);
		return pInfo;
	}

	public ArrayList<FreeBoardDTO> searchList(PageUtil pInfo, String searchWord, String searchType, String ftype) {
		pInfo.setSearchWord(searchWord);
		pInfo.setSearchType(searchType);
		pInfo.setFtype(ftype);
		return freDAO.searchList(pInfo);
	}
	
	public ArrayList<FreeBoardDTO> searchListMyPage(PageUtil pInfo, String searchWord, String searchType, String ftype, String fid) {
		pInfo.setSearchWord(searchWord);
		pInfo.setSearchType(searchType);
		pInfo.setFtype(ftype);
		pInfo.setFid(fid);
		return freDAO.searchListMyPage(pInfo);
	}		
	public FreeBoardDTO detail(int fno) {
		return freDAO.getDetail(fno);
	}

	public ArrayList<FreeBoardDTO> getFile(int fno) {
		return freDAO.getFile(fno);
	}

	public PageUtil getCommPageInfo(int fno, int commPage) {
		int Cnt = freDAO.getTotalCntOfComm(fno);
		PageUtil pInfo = new PageUtil(commPage, Cnt, 5, 5);
		pInfo.setFno(fno);
		return pInfo;
	}

	public ArrayList<FreeBoardDTO> getCommDetail(PageUtil pInfo, HttpSession session) {
		ArrayList<FreeBoardDTO> list = freDAO.getCommDetail(pInfo);
		for(int i=0; i<list.size();i++) {
			if(this.isLike(list.get(i).getFcno(), (String)session.getAttribute("MID"))==1){
				list.get(i).setIsLiked(Boolean.TRUE);
			}
		}
		return list;
	}
	
	// 상품 후기 좋아요 여부 체크
	public int isLike(int fcno, String mid) {
		//isLike 이 아이디 사람이 이미 좋아요 했으면 1, 안했으면 0
		int isLike = freDAO.getIsLike(fcno,mid);
		
		return isLike;
	}
		
	// 상품 후기 추가/삭제
	public void like(int fcno, String mid) {
		//isLike 이 아이디 사람이 이미 좋아요 했으면 1, 안했으면 0
		int isLike = this.isLike(fcno,mid);
		
		switch (isLike) {
		case 0: //좋아요 추가
			freDAO.LikeIns(fcno,mid);
			break;
		case 1: //좋아요 삭제
			freDAO.LikeDel(fcno,mid);
		}
	}

	//댓글
		public void insertComm(FreeBoardDTO freDTO) {
			freDAO.insertComm(freDTO);
		}

		public void deleteComm(int fcno) {
			freDAO.deleteComm(fcno);
		}
		/*수정*/
		//글만 수정
		public void update(FreeBoardDTO fdto) {
			freDAO.update(fdto,"fboard");
		}
		//파일까지 수정
		public void update(FreeBoardDTO fdto, ArrayList list) {
			freDAO.update(fdto,"fboard");
			freDAO.deleteFile(fdto);
			for(int i=0; i<list.size() ;i++) {
				//하나의 첨부파일의 정보는 Map으로 저장되어 있다
				HashMap map = (HashMap)list.get(i);
				fdto.setFipath((String)map.get("path"));
				fdto.setFioriname((String)map.get("oriName"));
				fdto.setFiimg((String)map.get("saveName"));
				fdto.setFilength((Long)map.get("len"));
				freDAO.updateFile(fdto,"fInfo");
				}
		}

		public void delete(FreeBoardDTO freDTO) {
			freDAO.delete(freDTO);
		}

		public void increaseHit(int fno, HttpSession session) {
			if (increaseHitKey(fno, session) == true) {
				freDAO.increaseHit(fno);
			}			
		}

		private boolean increaseHitKey(int fno, HttpSession session) {
		// 세션 arrayList map => 글번호 조회 이력
		ArrayList map = (ArrayList) session.getAttribute("FREEBOARDHITCHECK");
		System.out.println("map = "+map);
		// 기록이 없을 경우
		if (map == null) {
			map = new ArrayList();
			map.add(fno);
			session.setAttribute("FREEBOARDHITCHECK", map);
			return true;
		} // 기록이 있을 경우 -> 내 기록이 있는경우
		else if (map.contains(fno)) {
			return false;
		} else {// 기록이 있는 경우 -> 내 기록이 없는경우
			map.add(fno);
			session.setAttribute("FREEBOARDHITCHECK", map);
			return true;
		}
	}

	//해당 다이어리의 이미지,원래이름 가져오기
	public DiaryDTO getByDno(int dno) {
		DiaryDTO dto = freDAO.getByDno(dno);
		return dto;
	}


}
