package com.all.light.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.all.light.dto.DiaryDTO;
import com.all.light.dto.FreeBoardDTO;
import com.all.light.service.FreeBoardService;
import com.all.light.util.FileUtil;
import com.all.light.util.PageUtil;

@Controller
public class FreeBoardController {
	@Autowired
	private FreeBoardService freSVC;
	
	//유저단
	@RequestMapping(value="/freeboard/write", method= RequestMethod.GET)
	public ModelAndView freeBoardWriteGet(
			HttpServletRequest request, ModelAndView mv){
		System.out.println("FreeBoardController.freeBoardWrite" + request.getMethod() + "method");
		mv.setViewName("diary/user/freeboard/boardWrite");
		return mv;
	}
	
	@RequestMapping(value="/freeboard/write", method= RequestMethod.POST)
	public ModelAndView freeBoardWritePost(
			HttpServletRequest request, ModelAndView mv, RedirectView rv, FreeBoardDTO fdto){
		System.out.println("FreeBoardController.freeBoardWrite" + request.getMethod() + "method");
		String id = (String)request.getSession().getAttribute("MID");
		String nick = (String)request.getSession().getAttribute("MNICK");
		//파라미터 전달
		fdto.setFid(id);
		fdto.setFnick(nick);
		
		// - 첨부파일 처리
		String path="D:\\upload";
		ArrayList list= new ArrayList();
		for(int i=0; i< fdto.getFiles().length ;i++ ) { //파일이 여러 개이니 각각의 파일마다 반복
			//한 개씩 파일의 실제이름을 알아낸다
			String oriName = 
			  fdto.getFiles()[i].getOriginalFilename();
			//파일이 업로드 되지 않으면 다음 작업을 시도한다
			if( oriName==null||oriName.length()==0 ) {
				continue;
			}
			String saveName = FileUtil.renameTo(path, oriName);
			File file= new File(path, saveName);
			//파일복사 : transferTo()
			try {
				fdto.getFiles()[i].transferTo(file);
			}catch(Exception e) {
				System.out.println("파일복사관련에러="+e);
			}
			//------------ 하나의 파일이 업로드된 상태
			//업로드된 파일의 정보를 Map으로 묶는다
			HashMap map = new HashMap();
			map.put("path",    path);
			map.put("oriName", oriName);
			map.put("saveName",saveName);
			map.put("len", file.length());
			list.add(map);
		}//end for
			
		System.out.println("파라미터 = "+fdto);
		//비즈니스로직
		freSVC.write(fdto,list);
		//뷰지정
		rv.setUrl(request.getContextPath()+"/freeboard/list.com"); 
		mv.setView(rv);
		return mv;
	}
	
	@RequestMapping(value="/freeboard/list")
	public ModelAndView freeBoardList(
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "type", required = false, defaultValue = "fall") String searchType,
			@RequestParam(value = "ftype", required = false, defaultValue = "") String ftype,
			HttpServletRequest request, ModelAndView mv){
		System.out.println("FreeBoardController.freeBoardList" + request.getMethod() + "method");
		
		//비즈니스로직
		PageUtil pInfo = freSVC.getPageInfo(nowPage, searchWord, searchType, ftype);
		ArrayList<FreeBoardDTO> map = freSVC.searchList(pInfo, searchWord, searchType, ftype);

		System.out.println("list = " + map.toString());
		System.out.println("pinfo = " + pInfo.toString());
		
		//모델
		mv.addObject("LIST", map); // 공지 리스트
		mv.addObject("PINFO", pInfo); // 페이징 정보
		
		//뷰 지정
		mv.setViewName("diary/user/freeboard/boardList");
		return mv;
	}
	
	@RequestMapping(value="/freeboard/detail")
	public ModelAndView freeBoardDetail(
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "type", required = false, defaultValue = "fall") String searchType,
			@RequestParam(value = "ftype", required = false, defaultValue = "") String ftype,
			@RequestParam(value = "no") int fno,
			@RequestParam(value = "commPage", required = false, defaultValue = "1") int commPage,
			HttpServletRequest request,HttpSession session, ModelAndView mv){
		System.out.println("FreeBoardController.freeBoardDetail" + request.getMethod() + "method");
		
		//비즈니스로직
		FreeBoardDTO fdto=freSVC.detail(fno);//게시글
		ArrayList<FreeBoardDTO> files = freSVC.getFile(fno); //첨부파일목록조회
		PageUtil pInfo = freSVC.getCommPageInfo(fno, commPage);//댓글 페이징
		ArrayList<FreeBoardDTO> decomm=freSVC.getCommDetail(pInfo,session);//댓글
		freSVC.increaseHit(fno, request.getSession());
		
		fdto.setFccount(pInfo.getTotalCount());
		System.out.println("fdto = "+fdto);
		System.out.println("files= "+files);
		System.out.println("decomm="+decomm);
		mv.addObject("DETAIL",fdto);//게시글
		mv.addObject("FILE",files);//이미지파일
		mv.addObject("PINFO", pInfo); //페이징 정보
		mv.addObject("COMM",decomm);//댓글
		
		//뷰 지정
		mv.setViewName("diary/user/freeboard/boardDetail");
		return mv;
	}
	
	//댓글쓰기
	@RequestMapping("/freeboard/wcomment")
	@ResponseBody
	public String writeComm(FreeBoardDTO freDTO,HttpSession session) {
		System.out.println("FreeBoardController.freeBoardCommentWrite");
		System.out.println(freDTO);
		freSVC.insertComm(freDTO);
		return "ajax";
	}
	//댓글삭제
	@RequestMapping("/freeboard/dcomment")
	@ResponseBody
	public String deleteComm(FreeBoardDTO freDTO) {
		System.out.println("FreeBoardController.freeBoardCommentDelete");
		System.out.println(freDTO);
		freSVC.deleteComm(freDTO.getFcno());
		return "ok";
	}
	
	@RequestMapping(value="/freeboard/update", method= RequestMethod.GET)
	public ModelAndView freeBoardUpdateGet(
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "type", required = false, defaultValue = "fall") String searchType,
			@RequestParam(value = "ftype", required = false, defaultValue = "") String ftype,
			@RequestParam(value = "no") int fno,
			HttpServletRequest request, ModelAndView mv){
		System.out.println("FreeBoardController.freeBoardUpdate" + request.getMethod() + "method");
		
		FreeBoardDTO fdto=freSVC.detail(fno);//게시글
		System.out.println("fdto = "+fdto);
		mv.addObject("DETAIL",fdto);//게시글
		//뷰 지정
		mv.setViewName("diary/user/freeboard/boardUpdate");
		return mv;
	}
	
	@RequestMapping(value="/freeboard/update", method= RequestMethod.POST)
	public ModelAndView freeBoardUpdatePost(
			@RequestParam(value = "no") int fno,
			HttpServletRequest request, ModelAndView mv, RedirectView rv, FreeBoardDTO fdto){
		System.out.println("FreeBoardController.freeBoardUpdate" + request.getMethod() + "method");
		fdto.setFno(fno);
		System.out.println("fdto = "+fdto);
		if(!fdto.getFiles()[0].getOriginalFilename().equals("")) {
			System.out.println("파일 수정o"+fdto.getFiles()[0].getOriginalFilename());
			// - 첨부파일 처리
			String path="D:\\upload";
			ArrayList list= new ArrayList();
			for(int i=0; i< fdto.getFiles().length ;i++ ) { //파일이 여러 개이니 각각의 파일마다 반복
				//한 개씩 파일의 실제이름을 알아낸다
				String oriName = 
				  fdto.getFiles()[i].getOriginalFilename();
				//파일이 업로드 되지 않으면 다음 작업을 시도한다
				if( oriName==null||oriName.length()==0 ) {
					continue;
				}
				String saveName = FileUtil.renameTo(path, oriName);
				File file= new File(path, saveName);
				//파일복사 : transferTo()
				try {
					fdto.getFiles()[i].transferTo(file);
				}catch(Exception e) {
					System.out.println("파일복사관련에러="+e);
				}
				//------------ 하나의 파일이 업로드된 상태
				//업로드된 파일의 정보를 Map으로 묶는다
				HashMap map = new HashMap();
				map.put("path",    path);
				map.put("oriName", oriName);
				map.put("saveName",saveName);
				map.put("len", file.length());
				list.add(map);
			}//end for
			freSVC.update(fdto, list);
		}else {
			System.out.println("파일 수정x"+fdto.getFiles()[0].getOriginalFilename());
			freSVC.update(fdto);
		}
		System.out.println("fdto = "+fdto);
		
		//뷰 지정
		rv.setUrl(request.getContextPath()+"/freeboard/list.com");
		mv.setView(rv);
		return mv;
	}
	
	@RequestMapping(value="/freeboard/delete")
	public ModelAndView freeBoardDelete(
			@RequestParam(value = "no") int fno, HttpServletRequest request, ModelAndView mv, RedirectView rv,
			FreeBoardDTO freDTO){
		System.out.println("FreeBoardController.freeBoardDelete " + request.getMethod() + "method");
		freDTO.setFno(fno);
		System.out.println(freDTO);
		freSVC.delete(freDTO);
		rv.setUrl(request.getContextPath()+"/freeboard/list.com");
		mv.setView(rv);
		return mv;
	}
	
	
	
	/*----마이페이지용 컨트롤러----------------------------------------------------------------*/
	/*----마이페이지용 컨트롤러----------------------------------------------------------------*/
	/*----마이페이지용 컨트롤러----------------------------------------------------------------*/
	
	@RequestMapping(value="/mypage/freeboard/list")
	public ModelAndView freeBoardListMyPage(
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "type", required = false, defaultValue = "fall") String searchType,
			@RequestParam(value = "ftype", required = false, defaultValue = "") String ftype,
			HttpServletRequest request, ModelAndView mv){
		System.out.println("FreeBoardController.freeBoardListMyPage" + request.getMethod() + "method");
		String fid = (String)request.getSession().getAttribute("MID");
		//비즈니스로직
		PageUtil pInfo = freSVC.getPageInfoMyPage(nowPage, searchWord, searchType, ftype, fid);
		ArrayList<FreeBoardDTO> map = freSVC.searchListMyPage(pInfo, searchWord, searchType, ftype, fid);

		System.out.println("list = " + map.toString());
		System.out.println("pinfo = " + pInfo.toString());
		
		//모델
		mv.addObject("LIST", map); // 공지 리스트
		mv.addObject("PINFO", pInfo); // 페이징 정보
		
		//뷰 지정
		mv.setViewName("common/user/mypage/freeboardList");
		return mv;
	}
	
	@RequestMapping(value="/mypage/freeboard/detail")
	public ModelAndView freeBoardDetailMyPage(
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "type", required = false, defaultValue = "fall") String searchType,
			@RequestParam(value = "ftype", required = false, defaultValue = "") String ftype,
			@RequestParam(value = "no") int fno,
			@RequestParam(value = "commPage", required = false, defaultValue = "1") int commPage,
			HttpServletRequest request,HttpSession session, ModelAndView mv){
		System.out.println("FreeBoardController.freeBoardDetailMyPage" + request.getMethod() + "method");
		//비즈니스로직
		FreeBoardDTO fdto=freSVC.detail(fno);//게시글
		ArrayList<FreeBoardDTO> files = freSVC.getFile(fno); //첨부파일목록조회
		PageUtil pInfo = freSVC.getCommPageInfo(fno, commPage);//댓글 페이징
		ArrayList<FreeBoardDTO> decomm=freSVC.getCommDetail(pInfo,session);//댓글
		freSVC.increaseHit(fno, request.getSession());
		
		fdto.setFccount(pInfo.getTotalCount());
		System.out.println("fdto = "+fdto);
		System.out.println("files= "+files);
		System.out.println("decomm="+decomm);
		mv.addObject("DETAIL",fdto);//게시글
		mv.addObject("FILE",files);//이미지파일
		mv.addObject("PINFO", pInfo); //페이징 정보
		mv.addObject("COMM",decomm);//댓글
		
		//뷰 지정
		mv.setViewName("common/user/mypage/FBDetail");
		return mv;
	}
	
		@RequestMapping(value="/mypage/freeboard/write", method= RequestMethod.GET)
		public ModelAndView freeBoardWriteMyPageGet(
				HttpServletRequest request, ModelAndView mv){
			System.out.println("FreeBoardController.freeBoardWriteMyPage" + request.getMethod() + "method");
			mv.setViewName("common/user/mypage/freeboardWrite");
			return mv;
		}
		
		@RequestMapping(value="/mypage/freeboard/write", method= RequestMethod.POST)
		public ModelAndView freeBoardWriteMyPagePost(
				HttpServletRequest request, ModelAndView mv, RedirectView rv, FreeBoardDTO fdto){
			System.out.println("FreeBoardController.freeBoardWriteMyPage" + request.getMethod() + "method");
			String id = (String)request.getSession().getAttribute("MID");
			String nick = (String)request.getSession().getAttribute("MNICK");
			//파라미터 전달
			fdto.setFid(id);
			fdto.setFnick(nick);
			
			// - 첨부파일 처리
			String path="D:\\upload";
			ArrayList list= new ArrayList();
			for(int i=0; i< fdto.getFiles().length ;i++ ) { //파일이 여러 개이니 각각의 파일마다 반복
				//한 개씩 파일의 실제이름을 알아낸다
				String oriName = 
				  fdto.getFiles()[i].getOriginalFilename();
				//파일이 업로드 되지 않으면 다음 작업을 시도한다
				if( oriName==null||oriName.length()==0 ) {
					continue;
				}
				String saveName = FileUtil.renameTo(path, oriName);
				File file= new File(path, saveName);
				//파일복사 : transferTo()
				try {
					fdto.getFiles()[i].transferTo(file);
				}catch(Exception e) {
					System.out.println("파일복사관련에러="+e);
				}
				//------------ 하나의 파일이 업로드된 상태
				//업로드된 파일의 정보를 Map으로 묶는다
				HashMap map = new HashMap();
				map.put("path",    path);
				map.put("oriName", oriName);
				map.put("saveName",saveName);
				map.put("len", file.length());
				list.add(map);
			}//end for
				
			System.out.println("파라미터 = "+fdto);
			//비즈니스로직
			freSVC.write(fdto,list);
			//뷰지정
			rv.setUrl(request.getContextPath() +"/mypage/freeboard/list.com"); 
			mv.setView(rv);
			return mv;
		}
		
	
		//댓글쓰기
		@RequestMapping("/mypage/freeboard/wcomment")
		@ResponseBody
		public String writeCommMypage(FreeBoardDTO freDTO,HttpSession session) {
			System.out.println("FreeBoardController.freeBoardCommentWriteMypage");
			System.out.println(freDTO);
			freSVC.insertComm(freDTO);
			return "ajax";
		}
		//댓글삭제
		@RequestMapping("/mypage/freeboard/dcomment")
		@ResponseBody
		public String deleteCommMypage(FreeBoardDTO freDTO) {
			System.out.println("FreeBoardController.freeBoardCommentDeleteMypage");
			System.out.println(freDTO);
			freSVC.deleteComm(freDTO.getFcno());
			return "ok";
		}
		
		@RequestMapping(value="/mypage/freeboard/update", method= RequestMethod.GET)
		public ModelAndView freeBoardUpdateGetMyPage(
				@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
				@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
				@RequestParam(value = "type", required = false, defaultValue = "fall") String searchType,
				@RequestParam(value = "ftype", required = false, defaultValue = "") String ftype,
				@RequestParam(value = "no") int fno,
				HttpServletRequest request, ModelAndView mv){
			System.out.println("FreeBoardController.freeBoardUpdateMypage" + request.getMethod() + "method");
			
			FreeBoardDTO fdto=freSVC.detail(fno);//게시글
			System.out.println("fdto = "+fdto);
			mv.addObject("DETAIL",fdto);//게시글
			//뷰 지정
			mv.setViewName("common/user/mypage/freeboardUpdate");
			return mv;
		}
		
		@RequestMapping(value="/mypage/freeboard/update", method= RequestMethod.POST)
		public ModelAndView freeBoardUpdatePostMyPage(
				@RequestParam(value = "no") int fno,
				HttpServletRequest request, ModelAndView mv, RedirectView rv, FreeBoardDTO fdto){
			System.out.println("FreeBoardController.freeBoardUpdateMyPage" + request.getMethod() + "method");
			fdto.setFno(fno);
			System.out.println("fdto = "+fdto);
			if(!fdto.getFiles()[0].getOriginalFilename().equals("")) {
				System.out.println("파일 수정o"+fdto.getFiles()[0].getOriginalFilename());
				// - 첨부파일 처리
				String path="D:\\upload";
				ArrayList list= new ArrayList();
				for(int i=0; i< fdto.getFiles().length ;i++ ) { //파일이 여러 개이니 각각의 파일마다 반복
					//한 개씩 파일의 실제이름을 알아낸다
					String oriName = 
					  fdto.getFiles()[i].getOriginalFilename();
					//파일이 업로드 되지 않으면 다음 작업을 시도한다
					if( oriName==null||oriName.length()==0 ) {
						continue;
					}
					String saveName = FileUtil.renameTo(path, oriName);
					File file= new File(path, saveName);
					//파일복사 : transferTo()
					try {
						fdto.getFiles()[i].transferTo(file);
					}catch(Exception e) {
						System.out.println("파일복사관련에러="+e);
					}
					//------------ 하나의 파일이 업로드된 상태
					//업로드된 파일의 정보를 Map으로 묶는다
					HashMap map = new HashMap();
					map.put("path",    path);
					map.put("oriName", oriName);
					map.put("saveName",saveName);
					map.put("len", file.length());
					list.add(map);
				}//end for
				freSVC.update(fdto, list);
			}else {
				System.out.println("파일 수정x");
				freSVC.update(fdto);
			}
			System.out.println("fdto = "+fdto);
			
			//뷰 지정
			rv.setUrl(request.getContextPath()+"/mypage/freeboard/list.com");
			mv.setView(rv);
			return mv;
		}
		
		@RequestMapping(value="/mypage/freeboard/delete")
		public ModelAndView freeBoardDeleteMyPage(
				@RequestParam(value = "no") int fno, HttpServletRequest request, ModelAndView mv, RedirectView rv,
				FreeBoardDTO freDTO){
			System.out.println("FreeBoardController.freeBoardDeleteMyPage " + request.getMethod() + "method");
			freDTO.setFno(fno);
			System.out.println(freDTO);
			freSVC.delete(freDTO);
			rv.setUrl(request.getContextPath()+"/mypage/freeboard/list.com");
			mv.setView(rv);
			return mv;
		}
		
		
		/*----관리자용 컨트롤러----------------------------------------------------------------*/
		/*----관리자용 컨트롤러----------------------------------------------------------------*/
		/*----관리자용 컨트롤러----------------------------------------------------------------*/
		
		@RequestMapping(value="/freeboard/write/admin", method= RequestMethod.GET)
		public ModelAndView freeBoardWriteAdminGet(
				HttpServletRequest request, ModelAndView mv){
			System.out.println("FreeBoardController.freeBoardWriteAdmin" + request.getMethod() + "method");
			mv.setViewName("diary/admin/freeboard/boardWrite");
			return mv;
		}
		
		@RequestMapping(value="/freeboard/write/admin", method= RequestMethod.POST)
		public ModelAndView freeBoardWriteAdminPost(
				HttpServletRequest request, ModelAndView mv, RedirectView rv, FreeBoardDTO fdto){
			System.out.println("FreeBoardController.freeBoardWriteAdmin" + request.getMethod() + "method");
			String id = (String)request.getSession().getAttribute("MID");
			String nick = (String)request.getSession().getAttribute("MNICK");
			//파라미터 전달
			fdto.setFid(id);
			fdto.setFnick(nick);
			
			// - 첨부파일 처리
			String path="D:\\upload";
			ArrayList list= new ArrayList();
			for(int i=0; i< fdto.getFiles().length ;i++ ) { //파일이 여러 개이니 각각의 파일마다 반복
				//한 개씩 파일의 실제이름을 알아낸다
				String oriName = 
				  fdto.getFiles()[i].getOriginalFilename();
				//파일이 업로드 되지 않으면 다음 작업을 시도한다
				if( oriName==null||oriName.length()==0 ) {
					continue;
				}
				String saveName = FileUtil.renameTo(path, oriName);
				File file= new File(path, saveName);
				//파일복사 : transferTo()
				try {
					fdto.getFiles()[i].transferTo(file);
				}catch(Exception e) {
					System.out.println("파일복사관련에러="+e);
				}
				//------------ 하나의 파일이 업로드된 상태
				//업로드된 파일의 정보를 Map으로 묶는다
				HashMap map = new HashMap();
				map.put("path",    path);
				map.put("oriName", oriName);
				map.put("saveName",saveName);
				map.put("len", file.length());
				list.add(map);
			}//end for
				
			System.out.println("파라미터 = "+fdto);
			//비즈니스로직
			freSVC.write(fdto,list);
			//뷰지정
			rv.setUrl(request.getContextPath()+"/freeboard/list/admin.com"); 
			mv.setView(rv);
			return mv;
		}
		
		@RequestMapping(value="/freeboard/list/admin")
		public ModelAndView freeBoardListAdmin(
				@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
				@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
				@RequestParam(value = "type", required = false, defaultValue = "fall") String searchType,
				@RequestParam(value = "ftype", required = false, defaultValue = "") String ftype,
				HttpServletRequest request, ModelAndView mv){
			System.out.println("FreeBoardController.freeBoardListAdmin" + request.getMethod() + "method");
			
			//비즈니스로직
			PageUtil pInfo = freSVC.getPageInfo(nowPage, searchWord, searchType, ftype);
			ArrayList<FreeBoardDTO> map = freSVC.searchList(pInfo, searchWord, searchType, ftype);

			System.out.println("list = " + map.toString());
			System.out.println("pinfo = " + pInfo.toString());
			
			//모델
			mv.addObject("LIST", map); // 공지 리스트
			mv.addObject("PINFO", pInfo); // 페이징 정보
			
			//뷰 지정
			mv.setViewName("diary/admin/freeboard/boardList");
			return mv;
		}
		
		@RequestMapping(value="/freeboard/detail/admin")
		public ModelAndView freeBoardDetailAdmin(
				@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
				@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
				@RequestParam(value = "type", required = false, defaultValue = "fall") String searchType,
				@RequestParam(value = "ftype", required = false, defaultValue = "") String ftype,
				@RequestParam(value = "no") int fno,
				@RequestParam(value = "commPage", required = false, defaultValue = "1") int commPage,
				HttpServletRequest request,HttpSession session, ModelAndView mv){
			System.out.println("FreeBoardController.freeBoardDetailAdmin" + request.getMethod() + "method");
			
			//비즈니스로직
			FreeBoardDTO fdto=freSVC.detail(fno);//게시글
			ArrayList<FreeBoardDTO> files = freSVC.getFile(fno); //첨부파일목록조회
			PageUtil pInfo = freSVC.getCommPageInfo(fno, commPage);//댓글 페이징
			ArrayList<FreeBoardDTO> decomm=freSVC.getCommDetail(pInfo,session);//댓글
			freSVC.increaseHit(fno, request.getSession());
			
			fdto.setFccount(pInfo.getTotalCount());
			System.out.println("fdto = "+fdto);
			System.out.println("files= "+files);
			System.out.println("decomm="+decomm);
			mv.addObject("DETAIL",fdto);//게시글
			mv.addObject("FILE",files);//이미지파일
			mv.addObject("PINFO", pInfo); //페이징 정보
			mv.addObject("COMM",decomm);//댓글
			
			//뷰 지정
			mv.setViewName("diary/admin/freeboard/boardDetail");
			return mv;
		}
		
		//댓글쓰기
		@RequestMapping("/freeboard/wcomment/admin")
		@ResponseBody
		public String writeCommAdmin(FreeBoardDTO freDTO,HttpSession session) {
			System.out.println("FreeBoardController.freeBoardCommentWriteAdmin");
			System.out.println(freDTO);
			freSVC.insertComm(freDTO);
			return "ajax";
		}
		//댓글삭제
		@RequestMapping("/freeboard/dcomment/admin")
		@ResponseBody
		public String deleteCommAdmin(FreeBoardDTO freDTO) {
			System.out.println("FreeBoardController.freeBoardCommentDeleteAdmin");
			System.out.println(freDTO);
			freSVC.deleteComm(freDTO.getFcno());
			return "ok";
		}
		
		@RequestMapping(value="/freeboard/update/admin", method= RequestMethod.GET)
		public ModelAndView freeBoardUpdateAdminGet(
				@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
				@RequestParam(value = "search", required = false, defaultValue = "") String searchWord,
				@RequestParam(value = "type", required = false, defaultValue = "fall") String searchType,
				@RequestParam(value = "ftype", required = false, defaultValue = "") String ftype,
				@RequestParam(value = "no") int fno,
				HttpServletRequest request, ModelAndView mv){
			System.out.println("FreeBoardController.freeBoardUpdateAdmin" + request.getMethod() + "method");
			
			FreeBoardDTO fdto=freSVC.detail(fno);//게시글
			System.out.println("fdto = "+fdto);
			mv.addObject("DETAIL",fdto);//게시글
			//뷰 지정
			mv.setViewName("diary/admin/freeboard/boardUpdate");
			return mv;
		}
		
		@RequestMapping(value="/freeboard/update/admin", method= RequestMethod.POST)
		public ModelAndView freeBoardUpdateAdminPost(
				@RequestParam(value = "no") int fno,
				HttpServletRequest request, ModelAndView mv, RedirectView rv, FreeBoardDTO fdto){
			System.out.println("FreeBoardController.freeBoardUpdateAdmin" + request.getMethod() + "method");
			fdto.setFno(fno);
			System.out.println("fdto = "+fdto);
			if(!fdto.getFiles()[0].getOriginalFilename().equals("")) {
				System.out.println("파일 수정o"+fdto.getFiles()[0].getOriginalFilename());
				// - 첨부파일 처리
				String path="D:\\upload";
				ArrayList list= new ArrayList();
				for(int i=0; i< fdto.getFiles().length ;i++ ) { //파일이 여러 개이니 각각의 파일마다 반복
					//한 개씩 파일의 실제이름을 알아낸다
					String oriName = 
					  fdto.getFiles()[i].getOriginalFilename();
					//파일이 업로드 되지 않으면 다음 작업을 시도한다
					if( oriName==null||oriName.length()==0 ) {
						continue;
					}
					String saveName = FileUtil.renameTo(path, oriName);
					File file= new File(path, saveName);
					//파일복사 : transferTo()
					try {
						fdto.getFiles()[i].transferTo(file);
					}catch(Exception e) {
						System.out.println("파일복사관련에러="+e);
					}
					//------------ 하나의 파일이 업로드된 상태
					//업로드된 파일의 정보를 Map으로 묶는다
					HashMap map = new HashMap();
					map.put("path",    path);
					map.put("oriName", oriName);
					map.put("saveName",saveName);
					map.put("len", file.length());
					list.add(map);
				}//end for
				freSVC.update(fdto, list);
			}else {
				System.out.println("파일 수정x"+fdto.getFiles()[0].getOriginalFilename());
				freSVC.update(fdto);
			}
			System.out.println("fdto = "+fdto);
			
			//뷰 지정
			rv.setUrl(request.getContextPath()+"/freeboard/list/admin.com");
			mv.setView(rv);
			return mv;
		}
		
		@RequestMapping(value="/freeboard/delete/admin")
		public ModelAndView freeBoardDeleteAdmin(
				@RequestParam(value = "no") int fno, HttpServletRequest request, ModelAndView mv, RedirectView rv,
				FreeBoardDTO freDTO){
			System.out.println("FreeBoardController.freeBoardDeleteAdmin " + request.getMethod() + "method");
			freDTO.setFno(fno);
			System.out.println(freDTO);
			freSVC.delete(freDTO);
			rv.setUrl(request.getContextPath()+"/freeboard/list/admin.com");
			mv.setView(rv);
			return mv;
		}
		
		// 좋아요 처리
		@RequestMapping("/freeboard/like")
		public ModelAndView Like(
				ModelAndView mv,@RequestParam(value = "no") int fno,
				@RequestParam(value = "commPage", required = false, defaultValue = "1") int commPage,
				@RequestParam(value = "num", required = false) int fcno,
				HttpSession session
				) {
			System.out.println("ShoppingController-reviewLike()");
			
			String mid = (String)session.getAttribute("MID");
			
			if(mid==null) {
				System.out.println("mid널");
				RedirectView rv = new RedirectView("./detail.com?no="+fno+"&commPage="+commPage+"&num="+fcno);
				mv.setView(rv);
				return mv;
			}
			
			//번호가 없으면 리스트로 돌려보내기
			if(fcno==0) {
				System.out.println("fcno 0");
				RedirectView rv = new RedirectView("./list.com");
				mv.setView(rv);
				return mv;
			}
			
			//해당 리뷰넘버에 이 아이디의 좋아요가 있으면 좋아요 삭제
			//해당 리뷰넘버에 이 아이디의 좋아요가 없으면 좋아요 생성
			freSVC.like(fcno,mid);
			
			// View
			RedirectView rv = new RedirectView("./detail.com?no="+fno+"&commPage="+commPage);
			mv.setView(rv);
			return mv;
		} 
		
		//마이페이지 리뷰 좋아요 처리
		@RequestMapping("/mypage/freeboard/like")
		public ModelAndView MyLike(
				ModelAndView mv,@RequestParam(value = "no") int fno,
				@RequestParam(value = "commPage", required = false, defaultValue = "1") int commPage,
				@RequestParam(value = "num", required = false) int fcno,
				HttpSession session
				) {
			System.out.println("ShoppingController-reviewLike()");
			
			String mid = (String)session.getAttribute("MID");
			
			if(mid==null) {
				System.out.println("mid널");
				RedirectView rv = new RedirectView("./detail.com?no="+fno+"&commPage="+commPage+"&num="+fcno);
				mv.setView(rv);
				return mv;
			}
			
			//번호가 없으면 리스트로 돌려보내기
			if(fcno==0) {
				System.out.println("fcno 0");
				RedirectView rv = new RedirectView("./list.com");
				mv.setView(rv);
				return mv;
			}
			
			//해당 리뷰넘버에 이 아이디의 좋아요가 있으면 좋아요 삭제
			//해당 리뷰넘버에 이 아이디의 좋아요가 없으면 좋아요 생성
			freSVC.like(fcno,mid);
			
			// View
			RedirectView rv = new RedirectView("./detail.com?no="+fno+"&commPage="+commPage);
			mv.setView(rv);
			return mv;
		} 
		
		//마이페이지 리뷰 좋아요 처리
		@RequestMapping("/freeboard/like/admin")
		public ModelAndView AdminLike(
				ModelAndView mv,@RequestParam(value = "no") int fno,
				@RequestParam(value = "commPage", required = false, defaultValue = "1") int commPage,
				@RequestParam(value = "num", required = false) int fcno,
				HttpSession session
				) {
			System.out.println("ShoppingController-reviewLike()");
			
			String mid = (String)session.getAttribute("MID");
			
			if(mid==null) {
				System.out.println("mid널");
				RedirectView rv = new RedirectView("../detail/admin.com?no="+fno+"&commPage="+commPage+"&num="+fcno);
				mv.setView(rv);
				return mv;
			}
			
			//번호가 없으면 리스트로 돌려보내기
			if(fcno==0) {
				System.out.println("fcno 0");
				RedirectView rv = new RedirectView("../list/admin.com");
				mv.setView(rv);
				return mv;
			}
			
			//해당 리뷰넘버에 이 아이디의 좋아요가 있으면 좋아요 삭제
			//해당 리뷰넘버에 이 아이디의 좋아요가 없으면 좋아요 생성
			freSVC.like(fcno,mid);
			
			// View
			RedirectView rv = new RedirectView("../detail/admin.com?no="+fno+"&commPage="+commPage);
			mv.setView(rv);
			return mv;
		} 
		
		// 다이어리에서 글쓰기(지영)
		@RequestMapping(value="/freeboard/diaryWriteFrm")
		public ModelAndView freeBoardDiaryWriteFrm(
				HttpServletRequest request, 
				ModelAndView mv, 
				FreeBoardDTO fdto,
				@RequestParam(value = "src", required = false, defaultValue = "_") String src,
				int num){
			System.out.println("FreeBoardController.freeBoardDiaryWriteFrm");
			System.out.println("dno: " + num);
			System.out.println(fdto);
			
			if(!src.equals("_")) {
				System.out.println("src: " + src);
				mv.addObject("src",src);
			}
			
			mv.addObject("FDTO",fdto);
			mv.addObject("num",num);
			mv.setViewName("diary/user/freeboard/boardDiaryWrite");
			return mv;
		}
		
		@RequestMapping(value="/freeboard/diaryWrite")
		public ModelAndView freeBoardDiaryWrite(
				HttpServletRequest request, 
				ModelAndView mv, 
				RedirectView rv, 
				FreeBoardDTO fdto,
				int num,
				int imgdel){
			System.out.println("FreeBoardController.freeBoardDiaryWrite");
			String id = (String)request.getSession().getAttribute("MID");
			String nick = (String)request.getSession().getAttribute("MNICK");
			//파라미터 전달
			fdto.setFid(id);
			fdto.setFnick(nick);
			
			// - 첨부파일 처리
			String path="D:\\upload";
			ArrayList list= new ArrayList();

			//변화사진을 업로드하려 할때 
			// imgdel  0:변화사진 첨부, 1:변화사진 있는데 없앴음, 2:변화사진 원래 없었음
			if(imgdel==0) {
				DiaryDTO dto = freSVC.getByDno(num); //해당 다이어리의 이미지,원래이름 가져오기
				
				HashMap map = new HashMap();
				map.put("path", path);
				map.put("oriName", dto.getDoriimage());
				map.put("saveName",dto.getDimage().substring(10));
				map.put("len", 0l);
				list.add(map);
			}
			
			for(int i=0; i< fdto.getFiles().length ;i++ ) { //파일이 여러 개이니 각각의 파일마다 반복
				//한 개씩 파일의 실제이름을 알아낸다
				String oriName = 
				  fdto.getFiles()[i].getOriginalFilename();
				//파일이 업로드 되지 않으면 다음 작업을 시도한다
				if( oriName==null||oriName.length()==0 ) {
					continue;
				}
				String saveName = FileUtil.renameTo(path, oriName);
				File file= new File(path, saveName);
				//파일복사 : transferTo()
				try {
					fdto.getFiles()[i].transferTo(file);
				}catch(Exception e) {
					System.out.println("파일복사관련에러="+e);
				}
				//------------ 하나의 파일이 업로드된 상태
				//업로드된 파일의 정보를 Map으로 묶는다
				HashMap map = new HashMap();
				map.put("path",    path);
				map.put("oriName", oriName);
				map.put("saveName",saveName);
				map.put("len", file.length());
				list.add(map);
			}//end for
				
			System.out.println("파라미터 = "+fdto);
			//비즈니스로직
			freSVC.write(fdto,list);
			//뷰지정
			rv.setUrl(request.getContextPath()+"/freeboard/list.com"); 
			mv.setView(rv);
			return mv;
		}
}
