package com.all.light.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/*컨트롤러에서 jsp가 아닌 컨트롤러를 호출해서 파일로드를 진행하고자 한다면
 * 1. bean설정
 * 2. AbstractView를 상속받는 클래스 제작
 * 3. 컨트롤러 함수에서의 처리
 */

// 이 클래스는 스트림 방식으로 서버의 파일의 클라이언트에게 전달할 목적으로 사용
// 스프링에서 다운로드를 위한 클래스를 만들때는 반드시 AbstractView 클래스를 상속 받아야 함
// 반드시       renderMergedOutputModel()함수를 오버라이딩해야 한다
public class DownloadUtil  extends AbstractView {

	public DownloadUtil() {
		this.setContentType("application/download;charset=UTF-8");
	}
	
	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest req, 
			HttpServletResponse resp) throws Exception {
		//첫번째 파라미터 Map model은  컨트롤러가 알려주는 다운로드파일에 대한 정보가 기억됨
		
		//응답방식을 스트림 방식으로 처리
		File	file = (File) model.get("downloadFile");
		
		//응답파일의 크기
		resp.setContentLength((int)file.length()); 
		
		//다운로드파일명이 한글인 경우 깨지지않도록 설정
		String Encoding = new String(file.getName().getBytes("UTF-8"), "8859_1");
		
		FileInputStream		fin = null;
		OutputStream		fout = null;
		try {
			fin = new FileInputStream(file);
			fout = resp.getOutputStream();
			FileCopyUtils.copy(fin, fout);
		}
		catch(Exception e) {
			System.out.println("다운로드 처리 에러 = " + e);
		}
		finally {
			fout.flush();
			try {
				fin.close();
				fout.close();
			}
			catch(Exception e) {}
		}
	}
}