package com.bcsfxy.boot.serivce.front.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bcsfxy.boot.dao.ISealDAO;
import com.bcsfxy.boot.service.front.ISealServiceFront;

@Service
public class SealServiceFrontImpl implements ISealServiceFront {
	@Resource
	private ISealDAO sealDAO;
}
