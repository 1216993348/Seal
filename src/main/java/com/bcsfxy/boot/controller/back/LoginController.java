package com.bcsfxy.boot.controller.back;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcsfxy.boot.service.back.IAdminService;
import com.bcsfxy.util.AbstractController;
import com.bcsfxy.util.entity.BaseResponse;
@Controller
public class LoginController extends AbstractController{
	@Resource
	private IAdminService adminService;
	@RequestMapping(value="login" ,method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponse> login(String aid,String password,HttpSession session) {
		BaseResponse rb = adminService.login(aid, password);
		if(rb.getState()) {
			session.setAttribute("admin",rb
					.getResult());
		}
		return super.buildResponse(rb);
	}
	@RequestMapping("back/actions/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	@RequestMapping(value="realpath" )
	@ResponseBody
	public String realpath() {
		System.out.println(new  Date(1546946030000L));
		return super.getApplication().getRealPath("/");
	}
}
