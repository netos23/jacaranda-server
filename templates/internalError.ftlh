<#assign hasModel = exceptionEntity ??>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <div>Exception occurred</div>
    <#if hasModel>
        <div>${exceptionEntity.getStatus().getCode()}</div>
        <div>${exceptionEntity.getStatus().name()}</div>
        <div>${exceptionEntity.getMessage()}</div>
    </#if>
</body>
</html>