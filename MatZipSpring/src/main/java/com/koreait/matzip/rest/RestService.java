package com.koreait.matzip.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestFile;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;

@Service
public class RestService {

	@Autowired
	private RestMapper mapper; // 인터페이스는 객체화 x

	@Autowired
	private CommonMapper cMapper;

	public List<RestDMI> selRestList(RestPARAM param) {
		return mapper.selRestList(param); // 등록
	}

	public List<RestRecMenuVO> selRestRecMenus(RestPARAM param) {
		return mapper.selRestRecMenus(param);
	}

	RestDMI selRest(RestPARAM param) {
		return mapper.selRest(param);
	}

	int insRest(RestPARAM param) {
		return mapper.insRest(param);
	}

	public void addHits(RestPARAM param, HttpServletRequest req) {
		// 현재 로그인한 사람의 pk값을 받아와야 하기 때문에 httpservletrequest 쓰는 것
		
		String myIp = req.getRemoteAddr(); // 현재 접속한 사람의 ip주소값
		ServletContext ctx = req.getServletContext();
		// 개인용 : 페이지 컨텍스트, 세션, 리퀘스트  // 공용 : 애플리케이션
		
		int i_user = SecurityUtils.getLoginUserPk(req);
		
		String currentRestReadIp = (String)ctx.getAttribute(Const.CURRENT_REST_READ_IP + param.getI_rest());
		if(currentRestReadIp == null || !currentRestReadIp.equals(myIp)) {
			//조회수 올림 처리 할거임
			param.setI_user(i_user); // 내가 쓴 글이면 조회수 안 올라가게 쿼리문으로 막는다
			mapper.updAddHits(param);
			ctx.setAttribute(Const.CURRENT_REST_READ_IP + param.getI_rest(), myIp);
		}
		
	}

	public List<CodeVO> selCategoryList() {
		CodeVO p = new CodeVO();
		p.setI_m(1); // 음식적 카테고리 코드 = 1
		return cMapper.selCodeList(p);
	}

	@Transactional
	public void delRestTran(RestPARAM param) {
		mapper.delRestRecMenu(param);
		mapper.delRestMenu(param);
		mapper.delRest(param);
	}

	public List<RestRecMenuVO> selRestMenus(RestPARAM param) {
		return mapper.selRestMenus(param);
	}

	public int insRecMenus(MultipartHttpServletRequest mReq) {
		int i_rest = Integer.parseInt(mReq.getParameter("i_rest"));
		int i_user = SecurityUtils.getLoginUserPk(mReq.getSession());

		if (_authFail(i_rest, i_user)) {
			return Const.FAIL;
		}

		List<MultipartFile> fileList = mReq.getFiles("menu_pic");
		String[] menuNmArr = mReq.getParameterValues("menu_nm");
		String[] menuPriceArr = mReq.getParameterValues("menu_price");

		String path = Const.realPath + "/resources/img/rest/" + i_rest + "/res_menu/";
		System.out.println("경로 : " + path);

		List<RestRecMenuVO> list = new ArrayList();

		for (int i = 0; i < menuNmArr.length; i++) {
			RestRecMenuVO vo = new RestRecMenuVO();
			list.add(vo);

			String menu_nm = menuNmArr[i];
			int menu_price = CommonUtils.parseStringToInt(menuPriceArr[i]);
			vo.setMenu_nm(menu_nm);
			vo.setMenu_price(menu_price);
			vo.setI_rest(i_rest);

			// 파일 각 저장
			MultipartFile mf = fileList.get(i);
			String saveFileNm = FileUtils.saveFile(path, mf);
			vo.setMenu_pic(saveFileNm);
		}

		for (RestRecMenuVO vo : list) {
			mapper.insRestRecMenu(vo);
		}

		return i_rest;
	}

	public int delRestRecMenu(RestPARAM param, String realPath) {
		// 파일 삭제
		List<RestRecMenuVO> list = mapper.selRestRecMenus(param);
		// 리스트 보내는 부분이지만 i_user까지 같이 보내줌
		if (list.size() == 1) { // 사이즈가 1이 맞는지 if 1이 넘어왔다면 내가 쓴 글이 맞음
			RestRecMenuVO item = list.get(0);

			// null.equals()는 되지 않는다 // &&은 앞에서부터 체크함 but &은 다 체크함
			if (!item.getMenu_pic().equals("") && item.getMenu_pic() == null) { // 메뉴값이 빈칸이 아니라면
				File file = new File(realPath + item.getMenu_pic());

				if (file.delete()) {
					return mapper.delRecMenu(param);
				} else {
					return 0;
				}
			}
		}
		return mapper.delRestRecMenu(param);
	}

	public int delRestMenu(RestPARAM param) {
		if (param.getMenu_pic() != null && "".equals(param.getMenu_pic())) {
			String path = Const.realPath + "/resources/img/rest/" + param.getI_rest() + "/menu/";

			if (FileUtils.delFile(path + param.getMenu_pic())) {
				return mapper.delRestMenu(param); // 0 or 1
			} else {
				return Const.FAIL;
			}
		}
		return mapper.delRestMenu(param);
	}

	public int insRestMenus(RestFile param, int i_user) {
		if (_authFail(param.getI_rest(), i_user)) {
			return Const.FAIL;
		}

		String path = Const.realPath + "/resources/img/rest/" + param.getI_rest() + "/menu/";

		System.out.println(path);
		List<RestRecMenuVO> list = new ArrayList();

		for (MultipartFile mf : param.getMenu_pic()) {
			RestRecMenuVO vo = new RestRecMenuVO();
			list.add(vo);

			String saveFileNm = FileUtils.saveFile(path, mf);
			vo.setI_rest(param.getI_rest());
			vo.setMenu_pic(saveFileNm);
		}

		for (RestRecMenuVO vo : list) {
			mapper.insRestMenus(vo);
		}

		return Const.SUCCESS;
	}

	private boolean _authFail(int i_rest, int i_user) {
		RestPARAM param = new RestPARAM();
		param.setI_rest(i_rest);

		RestDMI dbResult = mapper.selRest(param);
		if (dbResult == null || dbResult.getI_user() != i_user) {
			return true;
		}
		return false;
	}

}
