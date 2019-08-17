package com.bcsfxy.boot.controller.front;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcsfxy.boot.service.front.ICompanyServiceFront;
import com.bcsfxy.util.AbstractController;
import com.bcsfxy.util.entity.BaseResponse;
import com.bcsfxy.vo.Company;

@RestController
@RequestMapping("front/actions/company/")
public class CompanyControllerFront extends AbstractController {
	@Resource
	private ICompanyServiceFront companyServiceFront;

	@RequestMapping("add")
	public ResponseEntity<BaseResponse> add(Company vo) {
		BaseResponse br = new BaseResponse();
		br.setState(companyServiceFront.add(vo));
		return buildResponse(br);

	}
	@RequestMapping("checkCid")
	public boolean checkCid(String cid) {
		return companyServiceFront.checkCid(cid);

	}
	@RequestMapping("get")
	public ResponseEntity<BaseResponse> get(String cid) {
		return buildSuccessResponse(companyServiceFront.get(cid));

	}
	
	@RequestMapping("findAllByCidAndCname")
	public ResponseEntity<BaseResponse> findAllByCidAndCname(String cid,String cname) {
		return buildSuccessResponse(companyServiceFront.findAllByCidAndCname(cid, cname));

	}
	
	@RequestMapping("checkPwd")
	public boolean checkPwd(String cid,String password) {
		return companyServiceFront.checkPwd(cid, password);
	}
	
	@RequestMapping("findAll")
	public List<Company> findAll() {
		super.setPS(100);
		super.initAjaxSplitParam();
		return companyServiceFront.findCompanyIndexList();
	}
}
