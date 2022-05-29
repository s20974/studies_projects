<#include "security.ftlh">
<#import "login.ftlh" as logout>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Sweater</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <#if name != "unknown">
                <li class="nav-item">
                    <a class="nav-link" href="/main">Messages</a>
                </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/customer">User list</a>
                </li>
            </#if>
        </ul>
        <#if name !="unknown">
            <div class="navbar-text mr-3">${name}</div>
            <@logout.logout></@logout.logout>
        <#elseif !springMacroRequestContext.requestUri?contains("/login")>
            <a class="btn btn-primary" href="/login">Log In</a>
        </#if>
    </div>
</nav>