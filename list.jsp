<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/include/include.Taglib.jsp"%>
<html>
<head>
<title></title>
<s:include value="/WEB-INF/pages/include/include.Scripts.jsp" />
<script type="text/javascript" src="<s:url value="/jquery/ui/jquery.ui.datepicker.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/jquery/jquery.alphanumeric.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridList.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.popupWindow.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/ddsc.input.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.date.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.validation.plugin.js"/>"></script>
<script type="text/javascript">
function validate(acttype,t1) {
	$("#frm02002K").validate("clearPrompt");
	<%--
	$("#membId").validateRequired({fieldText:'<s:text name="membId" />'});
	 --%>
    return $("#frm02002K").validate("showPromptWithErrors");
}
function getParameter(actTpye) {
	
	//在list.jsp檢核刪除
	<%--
	if(actTpye.startsWith("delete")){
		$("#frm02002K").validate("clearPrompt");
		var membprepaidval = $("#tblGrid").getSelectedRow().find('td').eq(6).find('#membPrepaidVal').text();
		var optGradeCde =$("#tblGrid").getSelectedRow().find('td').eq(6).find('#optGradeCde').text();
		if(optGradeCde == 1){
			if(membprepaidval<=300){
				alert('<s:text name="exam.e0003"><s:param value="getText(\"300\")"/></s:text>');
				return;
			}
		}else if(optGradeCde == 2){
			if(membprepaidval<=100){
				alert('<s:text name="exam.e0003"><s:param value="getText(\"100\")"/></s:text>');
				return;
			}
		}else if(optGradeCde == 3){
			if(membprepaidval<=0){
				alert('<s:text name="exam.e0003"><s:param value="getText(\"0\")"/></s:text>');
				return;
			}
		}
	}
	 --%>
	var param = "labMemberPrepaidHis.prepaidOid=" + $("#tblGrid").getSelectedRow().find('td').eq(6).find('#preraidOid').text();
	return param;
}

$(document).ready(function() {
	$("#tblGrid").initGrid({lines:3});
	$('#tb').initPopupWindow({dailogWidth:'960', dailogHeight:'640'});
	
	$(".notDel .imgDelAct").hide();
	$(".notDel .imgUpdAct").hide();
	

});
</script>
</head>
<body> 
<s:form id="frm02002K" theme="simple" action="%{progAction}" >
	<div class="progTitle">
  		<s:include value="/WEB-INF/pages/include/include.Title.jsp" />
	</div>
	<div id="tb">
		<fieldset id="listFieldset">
		<table width="100%" border="0" cellpadding="2" cellspacing="0">
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="membId"/>：</td>
				<td width="30%"><s:textfield id="membId" name="labMemberPrepaidHis.membId" cssClass="enKey" maxlength="16" size="16"/></td>
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="prepaidDate"/>：</td>
				<td width="30%"><s:textfield name="labMemberPrepaidHis.prepaidDate" cssClass="inputDate" maxlength="21" size="25"/></td>
			</tr>
		</table>
		<!-- 按鍵組合 --><s:include value="/WEB-INF/pages/include/include.ListButton.jsp" /><!-- 按鍵組合 --> 
		</fieldset>
		<table id="tblGrid" class ="gridList" width="100%" border="0" cellpadding="2" cellspacing="1">
			<thead>
				<tr align="center" bgcolor="#e3e3e3">
					<th width="30"><s:text name="fix.00164" /></th>
					<th width="120"><s:text name="fix.00090" /></th>
					<th width="15%"><s:text name="membId" /></th>
					<th width="25%"><s:text name="membName" /></th>
					<th width="25%"><s:text name="prepaidDate" /></th>
					<th><s:text name="prepaidAmt" /></th>
					<th class ="ctrlHide"></th>
				 </tr>
			 </thead>
			 <tbody>
			 <s:iterator value="labMemberPrepaidHisList" status="status" >
			 	<tr class="<s:if test="PREPAID_DATE != nowadays">notDel</s:if>" >
					<td width="30" id="sn" align="center"><s:property value="#status.index+1" /></td>
					<!-- 表單按鍵 -->
					<td width="120"><s:include value="/WEB-INF/pages/include/include.actionButton.jsp" /></td>
					<!-- 表單按鍵 -->
					<td width="15%"><label><s:property value="MEMB_ID" /></label></td>
					<td width="25%"><label><s:property value="MEMB_NAME" /></label></td>
					<td width="25%" align="center"><label><s:property  value="PREPAID_DATE" /></label></td>
					<td align="right"><label><s:property value="PREPAID_AMT" /></label></td> 
					<td class ="ctrlHide">
						<label id="preraidOid"><s:property value="PREPAID_OID" /></label>
						<label id="membPrepaidVal"><s:property value="MEMB_PREPAID_VAL" /></label>
						<label id="optGradeCde"><s:property value="OPT_GRADE_CDE" /></label>
					</td>
				</tr>
			 </s:iterator>
			 </tbody>
		</table>
	</div>
	<!-- 分頁按鍵列 --><s:include value="/WEB-INF/pages/include/include.PaginationBar.jsp" /><!-- 分頁按鍵列 -->
</s:form>
</body>
</html>