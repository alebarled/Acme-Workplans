<%@page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>
var selectarray = [];
</script>
<acme:form readonly="false">
	<acme:form-textbox code="manager.workplan.list.label.id" path="id" readonly="true"/>
	<acme:form-moment code="manager.workplan.list.label.executionStart" path="executionStart"/>
	<acme:form-moment code="manager.workplan.list.label.executionEnd" path="executionEnd"/>	
	<acme:form-double code="manager.workplan.list.label.workload" path="workload"/>
	<acme:form-checkbox code="manager.workplan.list.label.isPublic" path="isPublic"/>
	
	<label for="tasks">Tasks:</label>
  	<select class="table table-striped" name="tasks" id="tasks" multiple>
  	<c:forEach items="${managerTasks}" var="task">
  	<option value=<c:out value="${task.id}"/>>
    	<c:out value="${task.title}"/>
    	</option>
    	
  
  	<c:if test="${fn:contains(tasks, task)}">
  	<script>selectarray.push(<c:out value="${task.id}"/>)</script>
  	</c:if>
  	
  	
 	 </c:forEach>
  	</select>
  
	<acme:form-submit test="${command == 'create'}" code="manager.workplan.create.label.create" action="/manager/workplan/create"/>
	<acme:form-submit test="${command == 'show' || command == 'update'}" code="manager.workplan.form.button.update" action="/manager/workplan/update"/>
	<acme:form-submit test="${command == 'show' || command == 'update'}" code="manager.workplan.form.button.delete" action="/manager/workplan/delete"/>
	<acme:form-return code="manager.workplan.list.label.return"/>
	
	
</acme:form>


<script>
document.getElementById("tasks").size = document.getElementById("tasks").length;
$("select").val(selectarray);


$('option').mousedown(function(e) {
    e.preventDefault();
    $(this).prop('selected', !$(this).prop('selected'));
    return false;
});
</script>
