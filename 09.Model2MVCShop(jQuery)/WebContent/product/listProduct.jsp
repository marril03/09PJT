<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 
<%@ page import="java.util.*"  %>
<%@ page import = "com.model2.mvc.service.domain.Product"  %>
<%@ page import="com.model2.mvc.common.*" %>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>
<%
	List<Product> list= (List<Product>)request.getAttribute("list");
	Page resultPage=(Page)request.getAttribute("resultPage");

	Search search = (Search)request.getAttribute("search");
	//==> null �� ""(nullString)���� ����
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
	
	String name = request.getParameter("menu");
	String searchs = "search";
%>
--%>

<html>
<head>
<title>��ǰ �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">

	// �˻� / page �ΰ��� ��� ��� Form ������ ���� JavaScrpt �̿�  
	function fncGetUserList(currentPage) {
		//document.getElementById("currentPage").value = currentPage;
	   //	document.detailForm.submit();
	   $("#currentPage").val(currentPage)
	   $("form").attr("method" , "POST").attr("action" , "/product/listProduct?menu=${param.menu}").submit();
	}
	
	$(function() {	
		
			
			var index = $(this).index();
			
			//alert($('input[name=prodbyNo]').index(this));
			$(".ct_list_pop td:nth-child(3)").on("click" , function() {
				alert($(index));
				//alert( $($('input[name=prodbyNo]')[index]).val() );
				//self.location="/product/getProduct?prodNo=${product.prodNo}&menu=${param.menu}";
			});	
	});
	
	
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					<c:if test="${param.menu eq 'search'}">
						��ǰ �����ȸ
					</c:if>
					<c:if test="${param.menu eq 'manage'}">
						��ǰ ����
					</c:if>
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>��ǰ��ȣ</option>
				<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>��ǰ��</option>
				<option value="2"  ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>��ǰ����</option>
			</select>
			<input 	type="text" name="searchKeyword" value="${! empty search.searchKeyword ? search.searchKeyword : ""}" class="ct_input_g" 
							style="width:200px; height:20px" >
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetUserList('1');">�˻�</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >
			��ü  ${resultPage.totalCount } �Ǽ�, ���� ${resultPage.currentPage} &nbsp;&nbsp;

		</td>
	</tr>
	
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�������</td>	
	</tr>
	
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>

	<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		
		<tr class="ct_list_pop">
			<td align="center">${i}</td>
			<td></td>
			<td align="left">
			
			<input type="hidden" name="prodbyNo" value="${product.prodNo}"/>
			<c:if test="${param.menu eq 'search'}">
			
				<c:if test="${empty product.proTranCode}">
					<!--<a href="/product/getProduct?prodNo=${product.prodNo}&menu=${param.menu}">${product.prodName}</a>-->
						${product.prodName}
				</c:if>
				
				<c:if test="${!(empty product.proTranCode)}">
					<c:if test="${user.role eq 'admin'}">
						<!--<a href="/product/getProduct?prodNo=${product.prodNo}&menu=${param.menu}">${product.prodName}</a>-->
						${product.prodName}
					</c:if>
					<c:if test="${!(user.role eq 'admin')}">
						${product.prodName}			
					</c:if>
				</c:if>
				
			</c:if>
			
			<c:if test="${param.menu eq 'manage'}">
				<c:if test="${empty product.proTranCode}">
					<!--<a href="/product/updateProduct?prodNo=${product.prodNo}&menu=${param.menu}">${product.prodName}</a>-->
					${product.prodName}
				</c:if>
				<c:if test="${!(empty product.proTranCode)}">
					${product.prodName}
				</c:if>
			</c:if>
					
			
			</td>
			
			<td></td>
			<td align="left">${product.price}</td>
			<td></td>
			<td align="left">${product.regDate}</td>
			<td></td>
			<td align="left">
			
	
			<c:if test="${param.menu eq 'manage'}">
				   <c:if test="${empty product.proTranCode}">
				 		 �Ǹ���
					</c:if>
					<c:if test="${product.proTranCode eq '0'}">
						���ſϷ�  <!--<a href="/purchase/updateTranCodeByProd?prodNo=${product.prodNo}&proTranCode=1">����ϱ�</a>-->����ϱ�
					</c:if>		
					<c:if test="${product.proTranCode eq '1'}">
						�����
					</c:if>
					<c:if test="${product.proTranCode eq '2'}">
						��ۿϷ�
					</c:if>
				</c:if>
			
			
			<c:if test="${param.menu eq 'search'}">
				<c:if test="${user.role eq 'admin'}">
					<c:if test="${empty product.proTranCode}">
						�Ǹ���
					</c:if>
					<c:if test="${product.proTranCode eq '0'}">
						���ſϷ�
					</c:if>		
					<c:if test="${product.proTranCode eq '1'}">
						�����
					</c:if>
					<c:if test="${product.proTranCode eq '2'}">
						��ۿϷ�
					</c:if>
				</c:if>
				
				<c:if test="${!(user.role eq 'admin')}">
					<c:if test="${empty product.proTranCode}">�Ǹ���</c:if>
					<c:if test="${!(empty product.proTranCode)}">������</c:if>
				</c:if>
			</c:if>
				
			
			</td>	
		</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>		
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
		   <%-- 
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					�� ����
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getCurrentPage()-1%>')">�� ����</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetProductList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					���� ��
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getEndUnitPage()+1%>')">���� ��</a>
			<% } %>
		--%>
		<jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->

</form>

</div>
</body>
</html>
