package com.ruoyi.escort.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医院管理对象 escort_hospital
 *
 * @author HuLang
 * @date 2023-03-09
 */
public class EscortHospital extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 医院编号
	 */
	private Long hospitalId;
	
	/**
	 * 医院名称
	 */
	@Excel(name = "医院名称")
	private String hospitalName;
	
	/**
	 * 医院类型
	 */
	@Excel(name = "医院类型")
	private String hospitalType;
	
	/**
	 * 服务时间
	 */
	@Excel(name = "服务时间")
	private String hospitalServerTime;
	
	/**
	 * 地区
	 */
	@Excel(name = "地区")
	private String hospitalArea;
	
	/**
	 * 地址
	 */
	@Excel(name = "地址")
	private String hospitalAddress;
	
	/**
	 * 简介
	 */
	@Excel(name = "简介")
	private String hospitalIntroduce;
	
	/**
	 * 状态
	 */
	@Excel(name = "状态")
	private String hospitalStatus;
	
	/**
	 * 删除标志（0代表存在 2代表删除）
	 */
	private String delFlag;
	
	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	public Long getHospitalId() {
		return hospitalId;
	}
	
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	
	public String getHospitalName() {
		return hospitalName;
	}
	
	public void setHospitalType(String hospitalType) {
		this.hospitalType = hospitalType;
	}
	
	public String getHospitalType() {
		return hospitalType;
	}
	
	public void setHospitalServerTime(String hospitalServerTime) {
		this.hospitalServerTime = hospitalServerTime;
	}
	
	public String getHospitalServerTime() {
		return hospitalServerTime;
	}
	
	public void setHospitalArea(String hospitalArea) {
		this.hospitalArea = hospitalArea;
	}
	
	public String getHospitalArea() {
		return hospitalArea;
	}
	
	public void setHospitalAddress(String hospitalAddress) {
		this.hospitalAddress = hospitalAddress;
	}
	
	public String getHospitalAddress() {
		return hospitalAddress;
	}
	
	public void setHospitalIntroduce(String hospitalIntroduce) {
		this.hospitalIntroduce = hospitalIntroduce;
	}
	
	public String getHospitalIntroduce() {
		return hospitalIntroduce;
	}
	
	public void setHospitalStatus(String hospitalStatus) {
		this.hospitalStatus = hospitalStatus;
	}
	
	public String getHospitalStatus() {
		return hospitalStatus;
	}
	
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public String getDelFlag() {
		return delFlag;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("hospitalId", getHospitalId())
				.append("hospitalName", getHospitalName())
				.append("hospitalType", getHospitalType())
				.append("hospitalServerTime", getHospitalServerTime())
				.append("hospitalArea", getHospitalArea())
				.append("hospitalAddress", getHospitalAddress())
				.append("hospitalIntroduce", getHospitalIntroduce())
				.append("hospitalStatus", getHospitalStatus())
				.append("delFlag", getDelFlag())
				.append("createBy", getCreateBy())
				.append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy())
				.append("updateTime", getUpdateTime())
				.append("remark", getRemark())
				.toString();
	}
}
