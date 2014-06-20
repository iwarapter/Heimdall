<%@ page import="com.willis.heimdall.Environment" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'environment.label', default: 'Environment')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-environment" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                  default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-environment" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="name" title="${message(code: 'environment.name.label', default: 'Name')}"/>

            <g:sortableColumn property="description"
                              title="${message(code: 'environment.description.label', default: 'Description')}"/>

            <g:sortableColumn property="system"
                              title="${message(code: 'environment.system.label', default: 'System')}"/>

            <g:sortableColumn property="url" title="${message(code: 'environment.url.label', default: 'Url')}"/>

            <g:sortableColumn property="phaseUsage"
                              title="${message(code: 'environment.phaseUsage.label', default: 'Phase Usage')}"/>

            <g:sortableColumn property="vendor"
                              title="${message(code: 'environment.vendor.label', default: 'Vendor')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${environmentInstanceList}" status="i" var="environmentInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${environmentInstance.id}">${fieldValue(bean: environmentInstance, field: "name")}</g:link></td>

                <td>${fieldValue(bean: environmentInstance, field: "description")}</td>

                <td>${fieldValue(bean: environmentInstance, field: "system")}</td>

                <td>${fieldValue(bean: environmentInstance, field: "url")}</td>

                <td>${fieldValue(bean: environmentInstance, field: "phaseUsage")}</td>

                <td>${fieldValue(bean: environmentInstance, field: "vendor")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${environmentInstanceCount ?: 0}"/>
    </div>
</div>
</body>
</html>
