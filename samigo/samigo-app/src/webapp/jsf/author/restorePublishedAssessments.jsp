<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://www.sakaiproject.org/samigo" prefix="samigo" %>
<%@ taglib uri="http://sakaiproject.org/jsf2/sakai" prefix="sakai" %>

<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/tags/samigo-2.9.0/samigo-app/src/webapp/jsf/author/configByPaperId_GET.jsp $
 * $Id: configByPaperId_GET.jsp $
 ***********************************************************************************
 *
 * Copyright (c) 2006, 2007, 2008 Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/
-->
<f:view>
	<html xmlns="http://www.w3.org/1999/xhtml">
		<head><%= request.getAttribute("html.head") %>
			<title></title>
			<script type="text/javascript" src="/library/js/jquery-latest.min.js"></script>
			<link type="text/css" href="/samigo-app/css/ui-lightness/jquery-ui-1.7.2.custom.css" rel="stylesheet" media="all"/>

		</head>
        <!--Toggle using Javascript-->
		<script type="text/javascript" language="javascript">
			function checkAll(formname, checktoggle)
			{
				var checkboxes = new Array(); 
				checkboxes = document.forms[0].getElementsByTagName('input');
				
				for (var i=0; i<checkboxes.length; i++)  {
				if (checkboxes[i].type == 'checkbox')   {
					checkboxes[i].checked = checktoggle;
				}
				}
			}
		</script>

		<body onload="<%= request.getAttribute("html.body.onload") %>">

			<f:verbatim><div class="portletBody"></f:verbatim>
			<!-- content... -->
			<h:form id="restorePublishedAssessmentsForm">
			
				<!-- HEADINGS -->
				<%@ include file="/jsf/author/allHeadings.jsp" %>	
				
				<h:panelGroup styleClass="searchNav">
					<h:outputText value="#{evaluationMessages.search}"/>
					<h:outputText value="#{evaluationMessages.column}"/>
					<h:inputText
						id="searchString"
						value="#{restorePublishedAssessmentsBean.searchString}"
						size="25"
						onfocus="clearIfDefaultString(this, '#{evaluationMessages.search_default_string_restore_assessments}')"
						onkeypress="return submitOnEnter(event, 'restorePublishedAssessmentsForm:searchSubmitButton');"/>
					<f:verbatim> </f:verbatim>
					<h:commandButton class="" actionListener="#{restorePublishedAssessmentsBean.search}" value="#{evaluationMessages.search_find}" id="searchSubmitButton" />
					<f:verbatim> </f:verbatim>
					<h:commandButton actionListener="#{restorePublishedAssessmentsBean.clear}" value="#{evaluationMessages.search_clear}"/>
				</h:panelGroup>
				
				<f:verbatim><br/></f:verbatim>
				
				<h:panelGroup>
					<sakai:pager id="pager2" totalItems="#{restorePublishedAssessmentsBean.dataRows}" firstItem="#{restorePublishedAssessmentsBean.firstRow}" 
						pageSize="#{restorePublishedAssessmentsBean.maxDisplayedRows}" textStatus="#{evaluationMessages.paging_status}" />
				</h:panelGroup>
			
				<f:verbatim><br/></f:verbatim>
				
				<h:commandButton value="#{evaluationMessages.restore_published_assessments_apply}" type="submit" action="#{restorePublishedAssessmentsBean.restorePublishedAssessments}"/>
				<f:verbatim><br/></f:verbatim>
				<f:verbatim><br/></f:verbatim>
				<f:verbatim><br/></f:verbatim>
				<a onclick="javascript:checkAll('restorePublishedAssessmentsForm', true);" href="javascript:void();"><h:outputText value="#{evaluationMessages.check_all}" /></a>
				<a onclick="javascript:checkAll('restorePublishedAssessmentsForm', false);" href="javascript:void();"><h:outputText value="#{evaluationMessages.uncheck_all}" /></a>

				<f:verbatim><br/></f:verbatim>
													
				<t:dataTable cellpadding="0" cellspacing="0" id="archivedPublishedAssessmentsTable" value="#{restorePublishedAssessmentsBean.archivedPublishedAssessmentsList}"
					var="archivedPublishedAssessment" styleClass="table table-striped table-bordered" columnClasses="textTable">
										
					<t:column>
						<f:facet name="header">						
						  <h:outputText value="#{evaluationMessages.restore_published_assessments_restore}" />						    
						</f:facet>
						<h:selectBooleanCheckbox value="#{archivedPublishedAssessment.checkAssessment}"/>
					</t:column>
					
					<t:column sortable="true">
					  <f:facet name="header">						
						  <h:outputText value="#{evaluationMessages.restore_published_assessments_title}" />						  						  
					  </f:facet>					 
					  <h:outputText value="#{archivedPublishedAssessment.title}" />						 
					</t:column>
					
					<t:column sortable="true">
					  <f:facet name="header">						
						  <h:outputText value="#{evaluationMessages.restore_published_assessments_startDate}" />							  
					  </f:facet>					 
					  <h:outputText value="#{archivedPublishedAssessment.startDate}" />						 
					</t:column>
						 
					<t:column sortable="true">
						<f:facet name="header">						
						  <h:outputText value="#{evaluationMessages.restore_published_assessments_submissions}" />							  
						</f:facet>
						<h:outputText value="#{archivedPublishedAssessment.submissions}" />						
					</t:column>
					
				</t:dataTable>

				<f:verbatim><br/></f:verbatim>
				
				<h:commandButton value="#{evaluationMessages.restore_published_assessments_apply}" type="submit" action="#{restorePublishedAssessmentsBean.restorePublishedAssessments}"/>
									
								
			</h:form>
			<!-- end content -->
			<f:verbatim></div></f:verbatim>

		</body>
	</html>
</f:view>

<script>
	$(document).ready(function () {
	$('#archivedPublishedAssessmentsTable').DataTable();
	$('.table.table-striped.table-bordered').addClass('bs-select');
	});
</script>