package com.bcsfxy.boot.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.bcsfxy.util.FileUploadPath;


@Component
public class TempFileClearScheduler {
	@Scheduled(fixedRate=1800000)//30时分钟扫描一次 temp/seal/目录的文件，对超过修改时间30分钟的文件进行删除
	public void tempSeal() {
		Logger log = Logger.getLogger(TempFileClearScheduler.class);
		
		try {
			File file= new File(ResourceUtils.getURL("classpath:").getPath()+"static/temp/seal/");
			if(file.getPath().contains("!")) {
				file =  new File(FileUploadPath.getFileUploadPath() + "temp/seal/");
			}
			System.out.println(file);
			int fileTotalCount = 0;
			if(file.list() != null) {
				fileTotalCount = file.list().length;//总文件数量
			}
			int surplusCount = 0;//剩余文件数量
			int deleteSuccessCount = 0;//删除成功文件数量
			if(file.exists()) {
				File files[] = file.listFiles();
				
				for (int i = 0; i < files.length; i++) {
					
					long interval = (new Date().getTime()-files[i].lastModified());
					if((interval /1000/60)>30) {
						if(files[i].delete()) {
							deleteSuccessCount++;
						}
					}
					
				}
				
			}
			surplusCount = fileTotalCount - deleteSuccessCount;
			log.info("【清除临时文件】"+ " 临时目录文件数量：" + fileTotalCount
					 + " ，本次删除文件数量：" + deleteSuccessCount + " ，剩余文件数量："+surplusCount);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
