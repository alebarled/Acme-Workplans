<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<script src="./js/chart.js"></script>


<h3><acme:message code="administrator.taskstatistics.show.title"/></h3>
<acme:form>
	<acme:form-textbox code="administrator.taskstatistics.show.label.numberOfPublicTasks" path="numberOfPublicTasks" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.numberOfPrivateTasks" path="numberOfPrivateTasks" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.numberOfFinishedTasks" path="numberOfFinishedTasks" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.numberOfNonFinishedTasks" path="numberOfNonFinishedTasks" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.avarageWorkloads" path="avarageWorkloads" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.minimumWorkloads" path="minimumWorkloads" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.maximumWorkloads" path="maximumWorkloads" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.deviationWorkload" path="deviationWorkload" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.avarageExecPeriod" path="avarageExecPeriod" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.minimumExecPeriod" path="minimumExecPeriod" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.maximumExecPeriod" path="maximumExecPeriod" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.deviationExecPeriod" path="deviationExecPeriod" readonly="true"/>
</acme:form>
<br>
<h3><acme:message code="administrator.taskstatistics.show.title.workplan"/></h3>
<acme:form>
	<acme:form-textbox code="administrator.taskstatistics.show.label.numberOfPublicWorkplans" path="numberOfPublicWorkplans" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.numberOfPrivateWorkplans" path="numberOfPrivateWorkplans" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.numberOfFinishedWorkplans" path="numberOfFinishedWorkplans" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.numberOfNonFinishedWorkplans" path="numberOfNonFinishedWorkplans" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.avarageWorkloads" path="avarageWorkloadsWorkplan" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.minimumWorkloads" path="minimumWorkloadsWorkplan" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.maximumWorkloads" path="maximumWorkloadsWorkplan" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.deviationWorkload" path="deviationWorkloadWorkplan" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.avarageExecPeriod" path="avarageExecPeriodWorkplan" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.minimumExecPeriod" path="minimumExecPeriodWorkplan" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.maximumExecPeriod" path="maximumExecPeriodWorkplan" readonly="true"/>
	<acme:form-textbox code="administrator.taskstatistics.show.label.deviationExecPeriod" path="deviationExecPeriodWorkplan" readonly="true"/>
</acme:form>

<div class="container">
	<canvas id="chart"></canvas>
</div>
    
<acme:form>
	<acme:form-return code="administrator.taskstatistics.form.button.return"/>
</acme:form>

<script>
renderChart([${totalworkplans},${publicworkplans},${privateworkplans}],
		'<acme:message code="administrator.taskstatistics.show.workplan"/>',
		['Total','<acme:message code="administrator.taskstatistics.show.label.numberOfPublicWorkplans"/>','<acme:message code="administrator.taskstatistics.show.label.numberOfPrivateWorkplans"/>']);
</script>