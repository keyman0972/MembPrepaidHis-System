<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/include.Taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<base target="_self" />
<s:include value="/WEB-INF/pages/include/include.Scripts.jsp" />
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridList.plugin.js"/>"></script>
<script language="javascript">
var oTable;
$(document).ready(function() {
	oTable =$("#tblGrid").initGrid({height:'480'});

});
</script>
</head>
<body>
<s:form id="frmExam02001K" method="post" theme="simple" action="%{progAction}">
	<s:hidden name="labMemberPrepaidHis.ver" />
	<div class="progTitle"> 
       <!-- 程式標題 --> <s:include value="/WEB-INF/pages/include/include.ConfirmTitle.jsp" /> <!-- 程式標題 -->
    </div>
    <div id="tb">
    <table width="100%" border="0" cellpadding="4" cellspacing="0" >
		<tr class="trBgOdd">
			<td width="20%" class="colNameAlign required">*<s:text name="prepaidDate" />：</td>
			<td width="30%">
				<s:property value="labMemberPrepaidHis.prepaidDate" />
				<s:hidden name="labMemberPrepaidHis.prepaidDate" />
			</td>
		</tr>
		<tr class="trBgEven">
			<td width="20%" class="colNameAlign required">*<s:text name="membId" />：</td>
			<td width="30%">
				<s:property value="labMemberPrepaidHis.membId" />&nbsp;-&nbsp;
				<s:property value="labMemberPrepaidHis.labMemberMst.membName"/>
				<s:hidden name="labMemberPrepaidHis.membId"/>
				<s:hidden name="labMemberPrepaidHis.labMemberMst.membName"/>
				<s:hidden name="labMemberPrepaidHis.prepaidOid" />
			</td>
			<td width="20%" class="colNameAlign required">*<s:text name="prepaidAmt" />：</td>
			<td width="30%">
				<s:property  value="labMemberPrepaidHis.prepaidAmt" />
				<s:hidden name="labMemberPrepaidHis.prepaidAmt" />
			</td>
			</tr>
	</table>
    </div>
    <!-- 按鍵組合 -->
        <s:include value="/WEB-INF/pages/include/include.ConfirmButton.jsp" /> 
    <!-- 按鍵組合 -->
</s:form>
</body>
</html>