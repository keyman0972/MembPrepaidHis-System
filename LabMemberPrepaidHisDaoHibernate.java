package com.ddsc.km.exam.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;

import com.ddsc.core.dao.hibernate.GenericDaoHibernate;
import com.ddsc.core.entity.UserInfo;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.util.HibernateScalarHelper;
import com.ddsc.core.util.Pager;
import com.ddsc.km.exam.dao.ILabMemberPrepaidHisDao;
import com.ddsc.km.exam.entity.LabMemberPrepaidHis;

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

public class LabMemberPrepaidHisDaoHibernate extends GenericDaoHibernate<LabMemberPrepaidHis,String> implements ILabMemberPrepaidHisDao {

	@Override
	public List<LabMemberPrepaidHis> getList(String id, UserInfo info) throws DdscApplicationException {
		List<Object> values = new ArrayList<Object>();
		StringBuffer sbsql= new StringBuffer();
		sbsql.append("select lmph ");
		sbsql.append("from LabMemberPrepaidHis lmph ");
		
		String keyword = "where ";
		if(StringUtils.isNotEmpty(id)){
			sbsql.append(keyword + "lmph.membId = ? ");
			values.add(id);
			keyword = "and ";
		}
		
		return super.findByHQLString(sbsql.toString(), values, info);
	}

	@Override
	public Pager searchByConditions(Map<String, Object> conditions, Pager pager, UserInfo userInfo) throws DdscApplicationException {
				
		StringBuffer sbsql = new StringBuffer();
		sbsql.append("SELECT LMM.MEMB_ID, LMM.MEMB_NAME, LMPH.PREPAID_DATE, LMPH.PREPAID_AMT, ");
		sbsql.append("	LMPH.PREPAID_OID, LMM.MEMB_PREPAID_VAL, GRADE.OPT_CDE AS OPT_GRADE_CDE ");
		sbsql.append("FROM LAB_MEMBER_MST LMM ");
		sbsql.append("INNER JOIN LAB_MEMBER_PREPAID_HIS LMPH ON LMM.MEMB_ID = LMPH.MEMB_ID ");
		sbsql.append("LEFT JOIN COMM_OPT_CDE GRADE ON LMM.MEMB_GRADE = GRADE.OPT_CDE_OID ");
		
		String keyword = "WHERE ";
		List<Object> value = new ArrayList<Object>();
		if (StringUtils.isNotEmpty((String) conditions.get("membId"))) {
			sbsql.append(keyword + "LMM.MEMB_ID LIKE ? ");
			value.add(conditions.get("membId") + "%");
			keyword = "AND ";
		}
		if (StringUtils.isNotEmpty((String) conditions.get("prepaidDate"))) {
			sbsql.append(keyword +"LMPH.PREPAID_DATE = ? ");
			value.add(conditions.get("prepaidDate"));
			keyword = "AND ";
		}
		
		
		sbsql.append("ORDER BY LMPH.PREPAID_DATE DESC ");
		
		// 建立List<HibernateScalarHelper> scalarList = new ArrayList<HibernateScalarHelper>(); 並add
		List<HibernateScalarHelper> scalarList = new ArrayList<HibernateScalarHelper>();
		scalarList.add(new HibernateScalarHelper("MEMB_ID", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("MEMB_NAME", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("PREPAID_DATE", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("PREPAID_AMT", Hibernate.BIG_DECIMAL));
		scalarList.add(new HibernateScalarHelper("PREPAID_OID", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("MEMB_PREPAID_VAL", Hibernate.BIG_DECIMAL));
		scalarList.add(new HibernateScalarHelper("OPT_GRADE_CDE", Hibernate.STRING));
		
		// 回傳
		return super.findBySQLQueryMapPagination(sbsql.toString(), pager, scalarList, value, userInfo);
	}

}
