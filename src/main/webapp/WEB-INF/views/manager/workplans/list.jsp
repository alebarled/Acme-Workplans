
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="manager.task.list.label.title" path="title"/>
	<acme:list-column code="manager.task.list.label.executionStart" path="executionStart"/>
	<acme:list-column code="manager.task.list.label.executionEnd" path="executionEnd"/>
	<acme:list-column code="manager.task.list.label.workload" path="workload"/>
	<acme:list-column code="manager.task.list.label.description" path="description"/>
	<acme:list-column code="manager.task.list.label.link" path="link"/>
</acme:list>