package com.bcsfxy.vo.enu;

public enum SealStatus {
	INAUDIT(0)//待审核
	,AUDIT_SUCCESS(1)//通过审核&&待交付
	,AUDIT_FAILED(2)//审核失败
	,CANCELLED(3)//已注销
	,CANCELLING(4)//申请注销
	,DELIVERED(5);//已交付
	private final int status;
	private SealStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}

}
