package com.bcsfxy.boot.controller.back;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bcsfxy.boot.service.back.IAdminService;
import com.bcsfxy.util.AbstractController;
import com.bcsfxy.util.entity.BaseResponse;
import com.bcsfxy.vo.Admin;
@RestController
@RequestMapping("back/actions/admin")
public class AdminController extends AbstractController {
	@Autowired
	private IAdminService adminService;
	@RequestMapping("/list")
	public ResponseEntity<BaseResponse> adminList(){
		super.initAjaxSplitParam();
		Admin admin = (Admin)super.getSession().getAttribute("admin");
		if(admin.getLev() == 1) {
			return super.buildSuccessResponse(adminService.adminList(super.getKW()));
		}else {
			return super.buildFailResponse();
		}
	}
	
	@RequestMapping("/checkAid")
	public ResponseEntity<BaseResponse> checkAid(String aid){
		return super.buildSuccessResponse(adminService.checkAid(aid));
	}
	@RequestMapping(value="/addAdmin" ,method=RequestMethod.POST)
	public ResponseEntity<BaseResponse> addAdmin(Admin admin){
		return super.buildResponse(adminService.addAdmin(admin));
	}
	@RequestMapping("/updateStatus")
	public ResponseEntity<BaseResponse> updateStatus(String aid){
		return super.buildResponse(adminService.updateStatus(aid));
	}
	
	@RequestMapping(value="/updatePW" ,method=RequestMethod.POST)
	public ResponseEntity<BaseResponse> updatePW(String aid,String password){
		return super.buildSuccessResponse(adminService.updatePassword(aid,password));
	}
	@RequestMapping("/delete")
	public ResponseEntity<BaseResponse> delete(@RequestParam(value = "ids[]")String ids[]){
		return super.buildSuccessResponse(adminService.delete(ids));
	}
	@RequestMapping(value="/updateCurrentPW" ,method=RequestMethod.POST)
	public ResponseEntity<BaseResponse> updateCurrentPW(String password){
		Admin admin = (Admin) super.getSession().getAttribute("admin");
		return super.buildSuccessResponse(adminService.updatePassword(admin.getAid(),password));
	}
	@RequestMapping(value="/addSealMaker" ,method=RequestMethod.POST)
	public ResponseEntity<BaseResponse> addSealMaker(Admin vo){
		return super.buildSuccessResponse(adminService.addSealMaker(vo));
	}
	
	@RequestMapping(value="/findSMSplitPage" )
	public ResponseEntity<BaseResponse> findSMSplitPage(String aid,String smname){
		super.initAjaxSplitParam();
		return super.buildSuccessResponse(adminService.findSMSplitPage(aid, smname));
	}

	@RequestMapping(value="/show" )
	public ResponseEntity<BaseResponse> show(String aid){
		return super.buildSuccessResponse(adminService.show(aid));
	}
	@RequestMapping(value="/update" )
	public ResponseEntity<BaseResponse> update(Admin admin){
		return super.buildSuccessResponse(adminService.update(admin));
	}
	@RequestMapping(value="/deleteSM" ) 
	public ResponseEntity<BaseResponse> deleteSM(String aid){
		return super.buildSuccessResponse(adminService.delete(new String[] {aid}));
	}
	
}
