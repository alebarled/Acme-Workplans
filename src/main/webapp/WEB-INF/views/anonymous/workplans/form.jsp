<%@page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<acme:form readonly="true">

	<acme:form-textbox code="manager.workplan.list.label.title" path="title"/>
	<acme:form-textbox code="manager.workplan.list.label.description" path="description"/>
	<acme:form-moment code="manager.workplan.list.label.executionStart" path="executionStart"/>
	<acme:form-moment code="manager.workplan.list.label.executionEnd" path="executionEnd"/>	
	<acme:form-double code="manager.workplan.list.label.workload" path="workload"/>
	<acme:form-checkbox code="manager.workplan.list.label.isPublic" path="isPublic"/>
	
	
  	
  		<table class="table table-striped" id="tasks">
  			<tr>
	  			<th>
	  			<acme:message code="manager.workplan.list.label.tasks"/>
	  			</th>
  			</tr>
		  	<c:forEach items="${tasks}" var="task">
			  	<tr>
			  		<td>
			  		<c:if test="${task.finished == false}">
			  		<a href="./anonymous/task/show?id=<c:out value="${task.id}"/>">
			    	<c:out value="${task.title}"/>
			    	</a></c:if>
			    	
			    	<c:if test="${task.finished}">
			    	<c:out value="${task.title}"/>
			    	</c:if>
			    	</td>
			    </tr>
		 	</c:forEach>
	  	</table>

	<acme:form-return code="manager.workplan.list.label.return"/>
	
</acme:form>
