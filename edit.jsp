<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/include.Taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<base target="_self" />
<s:include value="/WEB-INF/pages/include/include.Scripts.jsp" />
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridEditList.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.validation.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/jquery/jquery.alphanumeric.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/ddsc.input.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.date.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/jquery/ui/jquery.ui.datepicker.min.js"/>"></script>
<script language="javascript">
var oTable;
//畫面欄位檢核
function validate() {
	$("#frm02001K").validate("clearPrompt");
	
	$("#membId").validateRequired({fieldText:'<s:text name="membId" />'});
	$("#prepaidAmt").validateRequired({fieldText:'<s:text name="prepaidAmt" />'});
	
	if($("#prepaidAmt").val()>0 && $("#prepaidAmt").val()%100 !=0){
		$("#prepaidAmt").validate("sendPrompt",{message:'<s:text name="exam.e0002" />'});
	}
	
	var membGrade = $("#membGrade").val().toNumber();
	var membPrepaidVal = $("#membPrepaidVal").val().toNumber();
	if(membGrade == 1){
		if(membPrepaidVal<=300){
			$("#membPrepaidVal").validate("sendPrompt",{message:'<s:text name="exam.e0003"><s:param value="getText(\"300\")"/></s:text>'});
		}
	}else if(membGrade == 2){
		if(membPrepaidVal<=100){
			$("#membPrepaidVal").validate("sendPrompt",{message:'<s:text name="exam.e0003"><s:param value="getText(\"100\")"/></s:text>'});
		}
	}else if(membGrade == 3){
		if(membPrepaidVal<=0){
			$("#membPrepaidVal").validate("sendPrompt",{message:'<s:text name="exam.e0003"><s:param value="getText(\"0\")"/></s:text>'});
		}
	}
	<%-- --%>
    return $("#frm02001K").validate("showPromptWithErrors");
}
function getMembName(){
	var membId =$("#membId").val();
	if(membId !=null && membId !=""){
		$.ajax({
			type: 'post',
			url:'<s:url value="/ajax/ajaxQuery/queryDataByParams.action" />',
			async:false,
			data:{queryName: 'findLabMemberMst', params: '{membId: "' + membId + '"}'},
			success: function (rtn_data) {
				if(rtn_data.results.length == 1 && rtn_data.results[0] != "" && rtn_data.results[0] != null){
					$("#membName").html(rtn_data.results[0][1]);
					$("#hiddmembName").val(rtn_data.results[0][1]);
				}else{
					$("#membName").html("<s:text name="eC.0037"/>");
				}

			}
		});
	}else{
		$("#membName").html("");
	}
}
$(document).ready(function() {
	oTable = $('#tblGrid').initEditGrid({height:'480'});
	
	$("#membId").bind("change", getMembName);
	
});
</script>
</head>
<body>
<s:form id="frm02001K" method="post" theme="simple" action="%{progAction}" target="ifrConfirm">
<s:hidden name="labMemberPrepaidHis.ver" />
 	<div class="progTitle"> 
		<!-- 程式標題 --> <s:include value="/WEB-INF/pages/include/include.EditTitle.jsp" /> <!-- 程式標題 -->
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
				<s:textfield id="membId" name="labMemberPrepaidHis.membId" maxlength="20" size="20" cssClass="enKey" />
				<input type="image" id="imgCustId" class="imgPopUp" src="<s:url value="/image_icons/search.png"/>" />
				<s:label id="membName" name="labMemberPrepaidHis.labMemberMst.membName" />
				<s:hidden id="hiddmembName" name="labMemberPrepaidHis.labMemberMst.membName" />
				<s:hidden name="labMemberPrepaidHis.prepaidOid" />
			</td>
			<td width="20%" class="colNameAlign required">*<s:text name="prepaidAmt" />：</td>
			<td width="30%">
				<s:textfield id="prepaidAmt" name="labMemberPrepaidHis.prepaidAmt" maxlength="10" size="16" cssClass="numKey" />
				<s:hidden id="hiddPrepaidAmt" disabled="true" />
			</td>
			<td class="ctrlHide">
				<s:hidden id="membGrade" name="labMemberPrepaidHis.labMemberMst.membGrade.optCde" />
				<s:hidden id="membPrepaidVal" name="labMemberPrepaidHis.labMemberMst.membPrepaidVal" />
			</td>
		</tr>
	</table>
    </div>
	<!-- 按鍵組合 --> 
	<s:include value="/WEB-INF/pages/include/include.EditButton.jsp" />
	<!-- 按鍵組合 -->
</s:form>
<iframe id="ifrConfirm" name="ifrConfirm" width="100%" height="768" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="display:none; border: 0px none"></iframe>
</body>
</html>