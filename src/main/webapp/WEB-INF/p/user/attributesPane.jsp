<%@include file="/WEB-INF/p/shared/taglibs.jsp"%>


<div class="info-tab">
<h2><span><tiles2:insertAttribute name="panelName"/></span></h2>
<voms:div cssClass="content" id="generic-attrs-content">
	<tiles2:insertTemplate template="attributes.jsp"/>
</voms:div></div>

