package com.bcsfxy.boot.controller.back;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bcsfxy.util.AbstractController;
import com.bcsfxy.vo.Admin;

@Controller
public class PageForwardController extends AbstractController {
	@RequestMapping("back/{groupName}/{pageName}")
	public String backForward(@PathVariable("groupName")String groupName,
			@PathVariable("pageName")String pageName) throws Exception{
		Set<String> set =new HashSet<>();
		Admin admin = (Admin)super.getSession().getAttribute("admin");
		if(admin.getLev() == 1) {
			set.add("admin/admin_list");
		}
		if(admin.getLev() == 0 || admin.getLev() ==1) {
			set.add("company/company_add");
			set.add("company/company_list");
			set.add("company/company_show");
			set.add("company/company_update");
			set.add("company/company_inaudit_list");
			set.add("seal/seal_add");
			set.add("seal/seal_list");
			set.add("seal/seal_update");
			set.add("seal/seal_company_add");
			set.add("seal/seal_audit_list");
			set.add("sm/seal_maker_add");
			set.add("sm/seal_maker_list");
			set.add("sm/seal_maker_update");
		}
		if(admin.getLev() == 2) {
			set.add("sm/seal_deliver");
			set.add("sm/seal_undeliver");
		}
		set.add("admin/update_password");
		//System.out.println("跳转页面：" +"pages/"+groupName+"/"+ pageName);
		if(set.contains(groupName+"/"+pageName)) {
			return "back/"+groupName+"/"+ pageName;
		}
		return "errors/404";
	}
	@RequestMapping("/front/{pageName}")
	public String frontForward( @PathVariable("pageName")String pageName) throws Exception{
		Set<String> set =new HashSet<>();
		set.add("company_register");
		set.add("seal_audit");
		set.add("contact");
		set.add("instructions");
		if(set.contains(pageName)) {
			return "front/"+pageName;
		}
		return "errors/404";
	}
	@RequestMapping("/back/index")
	public String backIndex() {
		return "back/index";
	}
	@RequestMapping("/front/index")
	public String frontIndex() {
		return "front/front_index";
	}
	@RequestMapping("/backLogin")
	public String backLogin() {
		return "login";
	}
}
