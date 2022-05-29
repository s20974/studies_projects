<#import "parts/common.ftlh" as common>

<@common.page>
    <span>User Editor</span>

    <form action="/customer" method="post">
        <input type="text" name="username" value="${customer.username}">
        <#list roles as role>
            <div>
                <label><input type="checkbox" name="${role}" ${customer.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <input type="hidden" value="${customer.id}" name="customerId">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Save</button>
    </form>
</@common.page>