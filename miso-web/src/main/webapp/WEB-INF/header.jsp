<%--
  ~ Copyright (c) 2012. The Genome Analysis Centre, Norwich, UK
  ~ MISO project contacts: Robert Davey @ TGAC
  ~ **********************************************************************
  ~
  ~ This file is part of MISO.
  ~
  ~ MISO is free software: you can redistribute it and/or modify
  ~ it under the terms of the GNU General Public License as published by
  ~ the Free Software Foundation, either version 3 of the License, or
  ~ (at your option) any later version.
  ~
  ~ MISO is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~ GNU General Public License for more details.
  ~
  ~ You should have received a copy of the GNU General Public License
  ~ along with MISO.  If not, see <http://www.gnu.org/licenses/>.
  ~
  ~ **********************************************************************
  --%>

<%@ page import="uk.ac.bbsrc.tgac.miso.webapp.context.ApplicationContextProvider" %>
<%@ page import="uk.ac.bbsrc.tgac.miso.Version" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="miso" uri="http://miso.tgac.bbsrc.ac.uk/tags/form" %>

<%@taglib prefix="sessionConversation" uri="/WEB-INF/tld/sessionConversation.tld" %>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en-gb">
<head>

  <%-- timestamp to force browser to reload javascript --%>
  <jsp:useBean id="timestamp" class="java.util.Date" scope="request"/>

  <title><c:if test="${not empty title}">${title} &mdash; </c:if>MISO LIMS ${misoInstanceName}</title>
          
  <!-- jQuery -->
  <script type="text/javascript" src="<c:url value='/scripts/jquery/js/jquery-1.8.3.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/jquery/js/jquery-ui-1.9.2.custom.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/jquery/js/jquery.validate.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/jquery/colorbox/jquery.colorbox-min-1.4.16.js'/>"></script>
  <link rel="stylesheet" href="<c:url value='/scripts/jquery/css/smoothness/jquery-ui-1.9.2.custom.min.css'/>"
    type="text/css">  
  <link rel="stylesheet" href="<c:url value='/scripts/jquery/colorbox/colorbox-1.4.16.css'/>"
    type="text/css">
  <!-- give $ back to prototype -->
  <script type="text/javascript">jQuery.noConflict();</script>
  
  <script type="text/javascript" src="<c:url value='/scripts/jquery/js/jquery.simplyCountable.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/jquery/js/jquery.tinysort.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/jquery/js/jquery.typewatch.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/jquery/js/jquery.uitablefilter.js'/>"></script>
 
  <script type="text/javascript" src="<c:url value='/scripts/jquery/js/jquery.tablesorter.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/jquery/js/jquery.metadata.js'/>"></script>
  
  <!-- unicode regexs for il8n validation -->
  <script type="text/javascript" src="<c:url value='/scripts/xregexp-all-min.js'/>"></script>
  
  <!-- D3.js for Graphics -->
  <script type="text/javascript" src="<c:url value='/scripts/d3v2/d3.v2.min.js'/>"></script>
  
  <!-- high charts -->
  <script type="text/javascript" src="<c:url value='/scripts/highcharts/highcharts.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/highcharts/highcharts-more.js'/>"></script>

  <!-- Parsley for form validation -->
  <script type="text/javascript" src="<c:url value='/scripts/parsley/parsley.min.js'/>"></script>

  <!-- Download support -->
  <script src="<c:url value='/scripts/download/download.js'/>" type="text/javascript"></script>

  <!-- Third-party stylesheets imported first, so we are able to override them -->
  <link href="<c:url value='/scripts/handsontable/dist/pikaday/pikaday.css'/>" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" media="screen" href="<c:url value='/scripts/handsontable/dist/handsontable.full.css'/>">
  <link rel="stylesheet" href="<c:url value='/scripts/jquery/datatables/css/jquery.dataTables.css'/>" type="text/css">
  <link rel="stylesheet" href="<c:url value='/scripts/jquery/datatables/css/jquery.dataTables_themeroller.css'/>">

  <!-- concatenated MISO stylesheets and scripts -->
  <link rel="stylesheet" href="<c:url value='/styles/style.css'/>" type="text/css">
  <script type="text/javascript" src="<c:url value='/miso/constants.js'/>?ts=<fmt:formatNumber value="${timestamp.time / 60000}" maxFractionDigits="0" groupingUsed="false" />"></script>
  <script type="text/javascript" src="<c:url value='/scripts/header_script.js'/>?version=<%=Version.VERSION%>"></script>


  <link rel="shortcut icon" href="<c:url value='/styles/images/favicon.ico'/>" type="image/x-icon"/>

    <!--IE check-->
    <script type="text/javascript">
        jQuery(document).ready(function () {
            if (jQuery.browser.msie) {
                alert("Internet Explorer is not supported by MISO. Please use Google Chrome, Safari or Mozilla Firefox");
            }
        });
    </script>
</head>

<body>
<div id="pageHeader">
  <div class="cell">
    <div id="logoContainer">
      <a href='<c:url value='/'/>'><img src="<c:url value='/styles/images/miso_logo.png'/>" alt="MISO Logo" name="logo" border="0" id="misologo"/></a>
    </div>
    <c:if test="${not empty misoInstanceName}"><span id="instanceName">${misoInstanceName}</span></c:if>
  </div>
  <div class="cell">
    <sec:authorize access="isAuthenticated()">
      <div id="loggedInBanner">
        <a href="http://miso-lims.github.io/miso-lims/usr/user-manual-table-of-contents.html">Help</a> |
        <c:if test="${misoBugUrl != null}">
          <a href="${misoBugUrl}" target="_blank">Report a problem</a> |
        </c:if>
        Logged in as:
        <a href="/miso/myAccount"><b id="currentUser"><sec:authentication property="principal.username"/></b></a>
        | <a href="<c:url value="/logout"/>">Logout</a>
      </div>
    </sec:authorize>
  </div>
</div>

<div id="content">

<sec:authorize access="isAuthenticated()">
  <script type="text/javascript">
    jQuery(function () {
      //all hover and click logic for buttons
      jQuery(".fg-button:not(.ui-state-disabled)")
        .hover(
        function () {
          jQuery(this).addClass("ui-state-hover");
        },
        function () {
          jQuery(this).removeClass("ui-state-hover");
        }
      )
    });

    jQuery(document).ready(function () {
      jQuery('.misoicon').hover(
        function () {
          jQuery(this).addClass('misoicon-hover');
        },
        function () {
          jQuery(this).removeClass('misoicon-hover');
        }
      );
    });
  </script>
</sec:authorize>
<div id="dialog"></div>
