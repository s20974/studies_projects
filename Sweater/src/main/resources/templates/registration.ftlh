<#import "parts/common.ftlh" as common>
<#import "parts/login.ftlh" as login>


<@common.page>
    <div class="mb-1"><b>Add new user</b></div>
    ${message?ifExists}
    <@login.login "/registration" true />
</@common.page>