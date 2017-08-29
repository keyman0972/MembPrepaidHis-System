package com.ddsc.km.exam.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ddsc.core.entity.UserInfo;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.util.BeanUtilsHelper;
import com.ddsc.core.util.Pager;
import com.ddsc.km.exam.dao.ILabMemberPrepaidHisDao;
import com.ddsc.km.exam.dao.ILabMemberMstDao;
import com.ddsc.km.exam.entity.LabMemberMst;
import com.ddsc.km.exam.entity.LabMemberPrepaidHis;
import com.ddsc.km.exam.service.ILabMemberPrepaidHisService;

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
 * <td>2017/8/17</td>
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

public class LabMemberPrepaidHisServiceImpl implements ILabMemberPrepaidHisService {
	
	private ILabMemberPrepaidHisDao labMemberPrepaidHisDao;
	
	private ILabMemberMstDao labMemberMstDao;
	
	@Override
	public LabMemberPrepaidHis create(LabMemberPrepaidHis entity, UserInfo info) throws DdscApplicationException {
		try{
			labMemberPrepaidHisDao.save(entity, info);
			
			String membId = entity.getMembId();
			BigDecimal prepaidAmt = entity.getPrepaidAmt();
			//取出該筆會員資料
			LabMemberMst labMemberMstVo =labMemberMstDao.get(membId, info);
			//儲值金額加進會員儲值總金額
			BigDecimal membPrepaidVal = labMemberMstVo.getMembPrepaidVal().add(prepaidAmt);
			
			BeanUtilsHelper.copyProperties(labMemberMstVo, entity, entity.obtainLocaleFieldNames());
			//放回entity
			labMemberMstVo.setMembPrepaidVal(membPrepaidVal);
			labMemberMstDao.update(labMemberMstVo, info);
			
			
			return entity;			
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}

	@Override
	public LabMemberPrepaidHis update(LabMemberPrepaidHis entity, UserInfo info) throws DdscApplicationException {
		try{
			//從資料庫取出該筆紀錄資料
			LabMemberPrepaidHis labMemberPrepaidHisVo = labMemberPrepaidHisDao.get(entity.getPrepaidOid(), info);
			BigDecimal prepaidAmtVo =labMemberPrepaidHisVo.getPrepaidAmt();
			//從jsp取得紀錄資料
			BigDecimal prepaidAmtPo = entity.getPrepaidAmt();
			
			//取出該筆會員資料
			LabMemberMst labMemberMstVo =labMemberMstDao.get(entity.getMembId(), info);
			BigDecimal membPrepaidVal = labMemberMstVo.getMembPrepaidVal();
			
			BigDecimal newMembPrepaidVal = null;
			if(prepaidAmtPo.compareTo(prepaidAmtVo)==1 || prepaidAmtPo.compareTo(prepaidAmtVo)== -1){
				//比原本資料大 或 比原本資料小時，有異動時
				//總金額減去原本金額在加上新輸入金額
				newMembPrepaidVal = (membPrepaidVal.subtract(prepaidAmtVo)).add(prepaidAmtPo);
			}else if(prepaidAmtPo.compareTo(prepaidAmtVo)==0){
				//等於
				newMembPrepaidVal = membPrepaidVal;
			}
			
			BeanUtilsHelper.copyProperties(labMemberMstVo, entity, entity.obtainLocaleFieldNames());
			labMemberMstVo.setMembPrepaidVal(newMembPrepaidVal);
			labMemberMstDao.update(labMemberMstVo, info);
			
			BeanUtilsHelper.copyProperties(labMemberPrepaidHisVo, entity, entity.obtainLocaleFieldNames());
			labMemberPrepaidHisVo.setPrepaidAmt(entity.getPrepaidAmt());
			return labMemberPrepaidHisDao.update(labMemberPrepaidHisVo, info);
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}

	@Override
	public LabMemberPrepaidHis delete(LabMemberPrepaidHis entity, UserInfo info) throws DdscApplicationException {
		try{
			//取出該筆會員資料
			LabMemberMst labMemberMstVo =labMemberMstDao.get(entity.getMembId(), info);
			BigDecimal membPrepaidVal = labMemberMstVo.getMembPrepaidVal().subtract(entity.getPrepaidAmt());
			
			BeanUtilsHelper.copyProperties(labMemberMstVo, entity, entity.obtainLocaleFieldNames());
			labMemberMstVo.setMembPrepaidVal(membPrepaidVal);
			labMemberMstDao.update(labMemberMstVo, info);
			
			labMemberPrepaidHisDao.delete(entity, info);
			return entity;
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
		
	}
	
	@Override
	public LabMemberPrepaidHis get(String id, UserInfo info) throws DdscApplicationException {
		try{
			LabMemberPrepaidHis labMemberPrepaidHis = this.labMemberPrepaidHisDao.get(id, info);
			
			LabMemberMst labMemberMst = labMemberMstDao.get(labMemberPrepaidHis.getMembId(), info);
			
//			List<Map<String,Object>> aListMap =labMemberPrepaidHisDao.getMembName(id, info);
//			LabMemberMst labMemberMst = new LabMemberMst();
//			labMemberMst.setMembName((String) aListMap.get(0).get("MEMB_NAME"));
			labMemberPrepaidHis.setLabMemberMst(labMemberMst);
			return labMemberPrepaidHis;
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}
	
	@Override
	public Pager searchByConditions(Map<String, Object> conditions, Pager pager, UserInfo userInfo) throws DdscApplicationException {
		try{
			return labMemberPrepaidHisDao.searchByConditions(conditions, pager, userInfo);
		}catch (DdscApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new DdscApplicationException(e, userInfo);
		}
	}
	
	@Deprecated
	@Override
	public List<LabMemberPrepaidHis> searchAll(UserInfo info) throws DdscApplicationException {
		return null;
	}
	
	@Deprecated
	@Override
	public List<LabMemberPrepaidHis> searchByConditions(Map<String, Object> conditions, UserInfo userInfo) throws DdscApplicationException {
		return null;
	}
	
	@Deprecated
	@Override
	public List<Object> queryDataByParamsByService(Map<String, Object> conditions, UserInfo userInfo) throws DdscApplicationException {
		return null;
	}

	@Override
	public int getDataRowCountByConditions(Map<String, Object> conditions, UserInfo info) throws DdscApplicationException {
		try{
			return 0;
		}catch (DdscApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}

	public ILabMemberPrepaidHisDao getLabMemberPrepaidHisDao() {
		return labMemberPrepaidHisDao;
	}

	public void setLabMemberPrepaidHisDao(ILabMemberPrepaidHisDao labMemberPrepaidHisDao) {
		this.labMemberPrepaidHisDao = labMemberPrepaidHisDao;
	}

	public ILabMemberMstDao getLabMemberMstDao() {
		return labMemberMstDao;
	}

	public void setLabMemberMstDao(ILabMemberMstDao labMemberMstDao) {
		this.labMemberMstDao = labMemberMstDao;
	}
}
