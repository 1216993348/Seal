package com.bcsfxy.boot.controller.back;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bcsfxy.boot.service.back.ICompanyService;
import com.bcsfxy.util.AbstractController;
import com.bcsfxy.util.entity.BaseResponse;
import com.bcsfxy.vo.Company;

@Controller
@RequestMapping("back/actions/company")
public class CompanyController extends AbstractController{
	@Resource
	private ICompanyService companyService;
	
	@RequestMapping(value="add" ,method=RequestMethod.POST)
	ResponseEntity<BaseResponse> add(Company company){
		return super.buildResponse(companyService.add(company));
	}
	@RequestMapping(value="delete",method=RequestMethod.GET)
	ResponseEntity<BaseResponse> delete(String cid){
		return super.buildResponse(companyService.delete(cid));
	}
	@RequestMapping(value="update",method=RequestMethod.POST)
	ResponseEntity<BaseResponse> update(Company company){
		return super.buildResponse(companyService.update(company));
	}
	@RequestMapping(value="list",method=RequestMethod.GET)
	ResponseEntity<BaseResponse> list(String province,String city,String cid,String cname){
		initAjaxSplitParam();
		return super.buildSuccessResponse(companyService.list(province, city, cid, cname));
	}
	@RequestMapping(value="listInAudit")
	ResponseEntity<BaseResponse> listInAudit(String province,String city,String cid,String cname){
		initAjaxSplitParam();
		return super.buildSuccessResponse(companyService.listInAudit(province, city, cid, cname));
	}
	
	@RequestMapping(value="show")
	ResponseEntity<BaseResponse> show(String cid){
		return super.buildSuccessResponse(companyService.show(cid));
	}
	/**
	 * @param cid 单位编号
	 * @param status 状态码：1=审批通过，2审批失败
	 * @return
	 */
	@RequestMapping(value="audit")
	ResponseEntity<BaseResponse> audit(String cid,int status){
		return super.buildSuccessResponse(companyService.audit(cid,status));
	}
}
