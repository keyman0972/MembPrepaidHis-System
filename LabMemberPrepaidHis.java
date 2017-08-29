package com.ddsc.km.exam.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.ddsc.core.entity.BaseEntity;

/**
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2017/8/15</td>
 * <td>新建檔案</td>
 * <td>"keyman"</td>
 * </tr>
 * </table>
 * @author "keyman"
 *
 * 類別說明 :
 *
 *
 * 版權所有 Copyright 2008 © 中菲電腦股份有限公司 本網站內容享有著作權，禁止侵害，違者必究。 <br>
 * (C) Copyright Dimerco Data System Corporation Inc., Ltd. 2009 All Rights
 */

@Entity
@Table(name="LAB_MEMBER_PREPAID_HIS")
public class LabMemberPrepaidHis extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 728000602207903856L;
	
	private String prepaidOid;
	private String membId;
	private String prepaidDate;
	private BigDecimal prepaidAmt;
	
	private LabMemberMst labMemberMst;
	
	@Id
	@Column (name = "PREPAID_OID")
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getPrepaidOid() {
		return prepaidOid;
	}
	
	public void setPrepaidOid(String prepaidOid) {
		this.prepaidOid = prepaidOid;
	}
	
	@Column (name = "MEMB_ID")
	public String getMembId() {
		return membId;
	}
	
	public void setMembId(String membId) {
		this.membId = membId;
	}
	
	@Column (name = "PREPAID_DATE")
	public String getPrepaidDate() {
		return prepaidDate;
	}
	
	public void setPrepaidDate(String prepaidDate) {
		this.prepaidDate = prepaidDate;
	}
	
	@Column (name = "PREPAID_AMT")
	public BigDecimal getPrepaidAmt() {
		return prepaidAmt;
	}
	
	public void setPrepaidAmt(BigDecimal prepaidAmt) {
		this.prepaidAmt = prepaidAmt;
	}
	
	@Transient
	public LabMemberMst getLabMemberMst() {
		return labMemberMst;
	}

	public void setLabMemberMst(LabMemberMst labMemberMst) {
		this.labMemberMst = labMemberMst;
	}
}
