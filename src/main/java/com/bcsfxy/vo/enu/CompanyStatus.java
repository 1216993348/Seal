package com.bcsfxy.vo.enu;

public enum CompanyStatus {
	INAUDIT(0)//审核
	,AUDIT_SUCCESS(1)//通过审核
	,AUDIT_FAILED(2)//审核失败
	,CANCELLED(3);//已注销
	private final int status;
	private CompanyStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}

}
