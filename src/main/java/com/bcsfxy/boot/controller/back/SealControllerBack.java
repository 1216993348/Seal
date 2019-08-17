package com.bcsfxy.boot.controller.back;

import java.io.FileNotFoundException;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bcsfxy.boot.service.back.ISealService;
import com.bcsfxy.util.AbstractController;
import com.bcsfxy.util.FileUploadPath;
import com.bcsfxy.util.entity.BaseResponse;
import com.bcsfxy.vo.Admin;
import com.bcsfxy.vo.Seal;
import com.bcsfxy.vo.enu.SealStatus;

@RestController
@RequestMapping("back/actions/seal/")
public class SealControllerBack extends AbstractController {
	@Resource
	private ISealService sealService;
	@RequestMapping("add")
	public ResponseEntity<BaseResponse> add(Seal seal) {
		Admin admin = (Admin) super.getSession().getAttribute("admin");
		seal.setAid(admin.getAid());
		seal.setStatus(SealStatus.AUDIT_SUCCESS.getStatus());
		if (sealService.add(seal)) {
			String oldUrl = super.getClassPath()+"static/temp/seal/" + seal.getPhoto();
			if(oldUrl.contains("!")) {
				oldUrl = FileUploadPath.getFileUploadPath() + "temp/seal/"+ seal.getPhoto();
			}
			String newUrl = super.getClassPath()+"static/upload/seal/" + seal.getPhoto();
			if(newUrl.contains("!")) {
				newUrl = FileUploadPath.getFileUploadPath() + "upload/seal/"+ seal.getPhoto();
			}
			super.moveFile(oldUrl, newUrl);
			return buildSuccessResponse();
		}
		return buildFailResponse();

	}

	@RequestMapping("uploadPhoto")
	public ResponseEntity<BaseResponse> uploadPhoto(MultipartFile file) {
		String fileName = super.createFileName(file);
		String filePath = super.getClassPath()+"static/temp/seal/";
		if(filePath.contains("!")) {
			filePath = FileUploadPath.getFileUploadPath() + "temp/seal/";
		}
		super.saveFile(filePath +fileName, file);
		return super.buildSuccessResponse(fileName);
	}

	@RequestMapping("createPhoto")
	public ResponseEntity<BaseResponse> createPhoto(String mainTitle, String viceTitle, String centerTitle,
			String topTitle,int version) throws FileNotFoundException {
		String fileName = super.createFileName(".png");
		String filePath = super.getClassPath()+"static/temp/seal/";//文件临时保存目录
		if(filePath.contains("!")) {
			filePath = FileUploadPath.getFileUploadPath() + "temp/seal/";
		}
		if(sealService.createFile(mainTitle, viceTitle, centerTitle, topTitle, filePath+fileName, version)){
			return super.buildSuccessResponse(fileName);
		}else {
			return super.buildFailResponse();
		}
	}
	
	@RequestMapping("list")
	public ResponseEntity<BaseResponse> list(String cid, String cname, String sid, String sname) {
		initAjaxSplitParam();
		return super.buildSuccessResponse(sealService.list(cid, cname, sid, sname));
	}
	
	@RequestMapping("update")
	public ResponseEntity<BaseResponse> update(Seal seal) {
		Admin admin =(Admin)super.getSession().getAttribute("admin");
		seal.setAid(admin.getAid());
		if (sealService.update(seal)) {
			String oldUrl = super.getClassPath()+"static/temp/seal/" + seal.getPhoto();
			if(oldUrl.contains("!")) {
				oldUrl = FileUploadPath.getFileUploadPath() + "temp/seal/"+ seal.getPhoto();;
			}
			String newUrl = super.getClassPath()+"static/upload/seal/" + seal.getPhoto();
			if(newUrl.contains("!")) {
				newUrl = FileUploadPath.getFileUploadPath() + "upload/seal/"+ seal.getPhoto();;
			}
			super.moveFile(oldUrl, newUrl);
			return buildSuccessResponse();
		}
		return buildFailResponse();
	}
	@RequestMapping("delete")
	public ResponseEntity<BaseResponse> delete(int sid) {
		return super.buildSuccessResponse(sealService.delete(sid));
	}
	@RequestMapping("show")
	public ResponseEntity<BaseResponse> show(int sid) {
		return super.buildSuccessResponse(sealService.show(sid));
	}
	
	@RequestMapping("listAudit")
	public ResponseEntity<BaseResponse> listAudit(String cid, String cname, String sid, String sname) {
		initAjaxSplitParam();
		return super.buildSuccessResponse(sealService.listAudit(cid, cname, sid, sname));
	}
	@RequestMapping("audit")
	public ResponseEntity<BaseResponse> audit(Seal seal) {
		Admin admin =(Admin)super.getSession().getAttribute("admin");
		seal.setAid(admin.getAid());
		return super.buildSuccessResponse(sealService.audit(seal));
	}
	@RequestMapping("undeliver")
	public ResponseEntity<BaseResponse> undeliver(String cid, String cname, String sid, String sname) {
		Admin admin =(Admin)super.getSession().getAttribute("admin");
		initAjaxSplitParam();
		return super.buildSuccessResponse(sealService.findSplitBySM(cid, cname, sid, sname, admin.getAid()));
	}@RequestMapping("deliver")
	public ResponseEntity<BaseResponse> deliver(String cid, String cname, String sid, String sname) {
		Admin admin =(Admin)super.getSession().getAttribute("admin");
		initAjaxSplitParam();
		return super.buildSuccessResponse(sealService.findSplitBySMDeliver(cid, cname, sid, sname, admin.getAid()));
	}
	@RequestMapping("submitSeal")
	public ResponseEntity<BaseResponse> submitSeal(int sid) {
		return super.buildSuccessResponse(sealService.submitSeal(sid));
	}
	
	
}
