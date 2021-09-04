<%@page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="./libraries/moment.js/2.29.1/moment.min.js"></script>
<script src="./js/workplanops.js"></script>
<script>
var selectarray = [];
var taskdict = {};
</script>
<acme:form readonly="false">

	<acme:form-textbox code="manager.workplan.list.label.title" path="title"/>
	<acme:form-textbox code="manager.workplan.list.label.description" path="description"/>
	<acme:form-moment code="manager.workplan.list.label.executionStart" path="executionStart"/>
	<acme:form-moment code="manager.workplan.list.label.executionEnd" path="executionEnd"/>	
	<acme:form-double code="manager.workplan.list.label.workload" path="workload" readonly="true"/>
	
	<acme:form-checkbox code="manager.workplan.list.label.isPublic" path="isPublic"/>
	
	
	<table class="table table-striped" id="tasks">
  			<tr>
	  			<th>
	  			<acme:message code="manager.workplan.list.label.tasks"/>
	  			<span style="font-weight: normal;">
		  	(<acme:message code="manager.workplan.list.label.public"/>: <span id=publica>0</span>, 
		  	<acme:message code="manager.workplan.list.label.private"/>: <span id=privada>0</span>)
		  	</span>
	  			</th>
  			</tr>
		  	<c:forEach items="${managerTasks}" var="task">
			  	<c:if test="${fn:contains(tasks, task)}">
		  			<script>selectarray.push(<c:out value="${task.id}"/>)</script>
		  		</c:if>
		  		
			  	<tr>
			  		<td>
			  		<input class="taskselector" id="taskselect" type="checkbox" name="newTasks" autocomplete="off" value=<c:out value="${task.id}"/>>
			    	<a href="./manager/task/show?id=<c:out value="${task.id}"/>" target="_blank">
			    	<c:out value="${task.title}"/></a>
			    	</td>
			    </tr>
			    
			    
			    <script>
				  	var start = moment('<c:out value="${task.executionStart}"/>',"YYYY-MM-DD HH:mm:ss.s");
				  	var end = moment('<c:out value="${task.executionEnd}"/>',"YYYY-MM-DD HH:mm:ss.s");
				  	
				  	taskdict[<c:out value="${task.id}"/>]={executionStart:start,
				  			executionEnd:end,
				  			workload:<c:out value="${task.workload}"/>,
				  			isPublic:<c:out value="${task.isPublic}"/>};
			  	</script>
		 	</c:forEach>
	  	</table>

	<acme:form-submit test="${command == 'create'}" code="manager.workplan.create.label.create" action="/manager/workplan/create"/>
	<acme:form-submit test="${command == 'show' || command == 'update'}" code="manager.workplan.form.button.update" action="/manager/workplan/update"/>
	<acme:form-submit test="${command == 'show' || command == 'update'}" code="manager.workplan.form.button.delete" action="/manager/workplan/delete"/>
	<acme:form-return code="manager.workplan.list.label.return"/>
	
	
</acme:form>


<script>
var language = '<c:out value="${language}"/>';
document.getElementsByTagName('label')[2].innerHTML = document.getElementsByTagName('label')[2].innerHTML + '<span id="startSuggestion" style="font-weight: normal;"> </span>';
document.getElementsByTagName('label')[3].innerHTML = document.getElementsByTagName('label')[3].innerHTML + '<span id="endSuggestion" style="font-weight: normal;"> </span>';

dateSuggestions(taskdict,selectarray,language);
workloadSum(taskdict,selectarray,language);
taskCount(taskdict,selectarray);

var element;
if(document.getElementById("newTasks")!=null)
	element = document.getElementById("newTasks");	
else
	element = document.getElementById("tasks")
	
element.size = element.length;
$('.taskselector').val(selectarray);


$('.taskselector').mousedown(function(e) {
    if(this.checked){
    	selectarray.splice(selectarray.indexOf(parseInt(this.value)), 1); 
    }
    else{
    	selectarray.push(parseInt(this.value));	
    }
    dateSuggestions(taskdict,selectarray,language);
    workloadSum(taskdict,selectarray,language);
    taskCount(taskdict,selectarray);
    return false;
});
</script>
