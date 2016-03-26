<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />


<spring:url value="/task/add" var="taskAddActionUrl" />


<h1> START THE FORM </h1>

<form:form class="form-horizontal" method="post" modelAttribute="taskForm" action="${taskAddActionUrl}">

		<form:hidden path="id" /> <!-- path refers to the field name of the object -->

		<spring:bind path="name">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Name</label>
				<div class="col-sm-10">
					<form:input path="name" type="text" class="form-control " id="name" placeholder="Name" />
					<form:errors path="name" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="log">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Log</label>
				<div class="col-sm-10">
					<form:input path="log" type="text" class="form-control " id="log" placeholder="log" />
					<form:errors path="log" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="text">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Text</label>
				<div class="col-sm-10">
					<form:input path="text" type="text" class="form-control " id="text" placeholder="text" />
					<form:errors path="text" class="control-label" />
				</div>
			</div>
		</spring:bind>		


		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn-lg btn-primary pull-right">Add</button>
			</div>
		</div>

</form:form>


<br/>



