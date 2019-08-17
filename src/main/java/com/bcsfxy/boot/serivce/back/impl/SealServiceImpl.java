package com.bcsfxy.boot.serivce.back.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bcsfxy.boot.dao.ICompanyDAO;
import com.bcsfxy.boot.dao.ISealDAO;
import com.bcsfxy.boot.service.back.ISealService;
import com.bcsfxy.util.PasswordUtil;
import com.bcsfxy.util.seal.SealCreate;
import com.bcsfxy.util.string.ValidateUtil;
import com.bcsfxy.vo.Company;
import com.bcsfxy.vo.Seal;
import com.bcsfxy.vo.enu.SealStatus;
import com.github.pagehelper.PageInfo;

@Service
public class SealServiceImpl implements ISealService {

	@Resource
	private ISealDAO sealDAO;
	@Resource
	private ICompanyDAO companyDAO;

	@Override
	public boolean add(Seal seal) {
		Company company = companyDAO.findById(seal.getCid());
		if(company != null && company.getStatus() == 2) {
			return false;
		}else {
			return sealDAO.doCreate(seal);

		}
	}

	@Override
	public boolean createFile(String mainTitle, String viceTitle, String centerTitle, String topTitle, String filePath,
			int version) {
		try {
			switch (version) {
			case 0:
				SealCreate.seal_zero(mainTitle, filePath);
				break;
			case 1:
				SealCreate.seal_one(mainTitle, viceTitle, filePath);
				break;
			case 2:
				SealCreate.seal_two(mainTitle, topTitle, filePath);
				break;
			case 3:
				SealCreate.seal_three(mainTitle, centerTitle, filePath);
				break;
			case 4:
				SealCreate.seal_four(mainTitle, centerTitle, topTitle, filePath);
				break;
			case 5:
				SealCreate.seal_five(mainTitle, viceTitle, centerTitle, filePath);
				break;
			case 6:
				SealCreate.seal_sex(mainTitle, viceTitle, centerTitle, filePath);
				break;
			default:
				break;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public PageInfo<Seal> list(String cid, String cname, String sid, String sname) {
		Map<String, Object> map = new HashMap<>();
		map.put("s.cid", "%" + cid + "%");
		map.put("c.cname", "%" + cname + "%");
		map.put("sid", "%" + sid + "%");
		map.put("sname", "%" + sname + "%");
		Map<String, Object> param = new HashMap<>();
		param.put("map", map);
		return new PageInfo<>(sealDAO.findSplit(param));
	}

	@Override
	public boolean delete(int sid) {
		return sealDAO.doRemoveById(sid);
	}

	@Override
	public boolean update(Seal seal) {
		seal.setSdate(new Date());
		return sealDAO.doUpdate(seal);
	}

	@Override
	public Seal show(int sid) {
		return sealDAO.findById(sid);
	}

	@Override
	public PageInfo<Seal> listAudit(String cid, String cname, String sid, String sname) {
		Map<String, Object> map = new HashMap<>();
		if (ValidateUtil.validateString(cid)) {
			map.put("s.cid", "%" + cid + "%");
		}
		if (ValidateUtil.validateString(cname)) {
			map.put("c.cname", "%" + cname + "%");
		}
		if (ValidateUtil.validateString(sid)) {
			map.put("sid", "%" + sid + "%");
		}
		if (ValidateUtil.validateString(sname)) {
			map.put("sname", "%" + sname + "%");
		}
		Map<String, Object> param = new HashMap<>();
		param.put("map", map);
		return new PageInfo<>(sealDAO.findSplitAudit(param));
	}

	@Override
	public boolean audit(Seal seal) {
		return sealDAO.doUpdate(seal);
	}

	@Override
	public List<Seal> findAllByCompany(String cid,String password) {
		Company company = companyDAO.findById(cid);
		if(PasswordUtil.getPassword(password).equals(company.getPassword())) {
			return sealDAO.findAllByCompany(cid);
		}else {
			return null;
		}
	}

	@Override
	public Map<String, Object> reauditSealPre(int sid) {
		Map<String, Object>  map =new HashMap<>();
		Seal seal = sealDAO.findById(sid);
		map.put("seal",seal);
		map.put("company",companyDAO.findById(seal.getCid()));
		return map;
	}

	@Override
	public boolean applyCancell(int sid) {
		Seal seal = new Seal();
		seal.setSid(sid);
		seal.setStatus(4);
		return sealDAO.doUpdate(seal);
	}

	@Override
	public PageInfo<Seal> findSplitBySM(String cid, String cname, String sid, String sname, String smid) {
		Map<String, Object> map = new HashMap<>();
		if (ValidateUtil.validateString(cid)) {
			map.put("s.cid", "%" + cid + "%");
		}
		if (ValidateUtil.validateString(cname)) {
			map.put("c.cname", "%" + cname + "%");
		}
		if (ValidateUtil.validateString(sid)) {
			map.put("sid", "%" + sid + "%");
		}
		if (ValidateUtil.validateString(sname)) {
			map.put("sname", "%" + sname + "%");
		}
		Map<String, Object> param = new HashMap<>();
		param.put("map", map);
		param.put("smid", smid);
		return new PageInfo<>(sealDAO.findSplitBySM(param));
	}
	@Override
	public PageInfo<Seal> findSplitBySMDeliver(String cid, String cname, String sid, String sname, String smid) {
		Map<String, Object> map = new HashMap<>();
		if (ValidateUtil.validateString(cid)) {
			map.put("s.cid", "%" + cid + "%");
		}
		if (ValidateUtil.validateString(cname)) {
			map.put("c.cname", "%" + cname + "%");
		}
		if (ValidateUtil.validateString(sid)) {
			map.put("sid", "%" + sid + "%");
		}
		if (ValidateUtil.validateString(sname)) {
			map.put("sname", "%" + sname + "%");
		}
		Map<String, Object> param = new HashMap<>();
		param.put("map", map);
		param.put("smid", smid);
		return new PageInfo<>(sealDAO.findSplitBySMDeliver(param));
	}

	@Override
	public boolean submitSeal(int sid) {
		Seal seal = new Seal();
		seal.setSid(sid);
		seal.setStatus(SealStatus.DELIVERED.getStatus());//将状态设置为已交付状态
		seal.setSmdate(new Date());
		return sealDAO.doUpdate(seal);
	}
}
