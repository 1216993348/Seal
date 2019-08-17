package com.bcsfxy.boot.controller.front;

import java.io.FileNotFoundException;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bcsfxy.boot.service.back.ISealService;
import com.bcsfxy.util.AbstractController;
import com.bcsfxy.util.FileUploadPath;
import com.bcsfxy.util.entity.BaseResponse;
import com.bcsfxy.vo.Seal;
import com.bcsfxy.vo.enu.SealStatus;

@Controller
@RequestMapping("front/actions/seal/")
public class SealControllerFront extends AbstractController {
	@Resource
	private ISealService sealService;
	@ResponseBody
	@RequestMapping("add")
	public ResponseEntity<BaseResponse> add(Seal seal) {
		seal.setStatus(SealStatus.INAUDIT.getStatus());
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
	@ResponseBody
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
	@ResponseBody
	@RequestMapping("createPhoto")
	public ResponseEntity<BaseResponse> createPhoto(String mainTitle, String viceTitle, String centerTitle,
			String topTitle,int version) throws FileNotFoundException {
		String fileName = super.createFileName(".png");
		String filePath = super.getClassPath()+"static/temp/seal/";
		if(filePath.contains("!")) {
			filePath = FileUploadPath.getFileUploadPath() + "temp/seal/";
		}
		System.out.println("filePath:" + filePath);
		if(sealService.createFile(mainTitle, viceTitle, centerTitle, topTitle, filePath+fileName, version)){
			return super.buildSuccessResponse(fileName);
		}else {
			return super.buildFailResponse();
		}
	}
	@ResponseBody
	@RequestMapping("list")
	public ResponseEntity<BaseResponse> list(String cid, String cname, String sid, String sname) {
		initAjaxSplitParam();
		return super.buildSuccessResponse(sealService.list(cid, cname, sid, sname));
	}
	@ResponseBody
	@RequestMapping("update")
	public ResponseEntity<BaseResponse> update(Seal seal) {
		seal.setStatus(0);
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
	@ResponseBody
	@RequestMapping("delete")
	public ResponseEntity<BaseResponse> delete(int sid) {
		return super.buildSuccessResponse(sealService.delete(sid));
	}
	@ResponseBody
	@RequestMapping("get")
	public ResponseEntity<BaseResponse> show(int sid) {
		return super.buildSuccessResponse(sealService.show(sid));
	}
	@ResponseBody
	@RequestMapping("findAllByCompany")
	public ResponseEntity<BaseResponse> findAllByCompany(String cid,String password) {
		return super.buildSuccessResponse(sealService.findAllByCompany(cid,password));
	}
	
	@RequestMapping(value="reaudit",method=RequestMethod.POST)
	public ModelAndView reaudit(int sid) {
		ModelAndView mav = new ModelAndView("/front/seal_reaudit");
		mav.addObject("info",sealService.reauditSealPre(sid));
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("applyCancell")
	public ResponseEntity<BaseResponse> applyCancell(int sid) {
		return super.buildSuccessResponse(sealService.applyCancell(sid));
	}
	
}
