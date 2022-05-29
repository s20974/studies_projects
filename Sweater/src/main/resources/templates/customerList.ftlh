<#import "parts/common.ftlh" as common>

<@common.page>
    <span>List of Users !</span>

    <table>
        <thead>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </thead>
        <tbody>
            <#list customers as customer>
                <tr>
                    <td>${customer.username!"null or missing"}</td>
                    <td>
                        <#list customer.roles as role>
                            ${role}<#sep>,
                        </#list>
                    </td>
                    <td>
                        <a href="/customer/${customer.id}">edit</a>
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
</@common.page>