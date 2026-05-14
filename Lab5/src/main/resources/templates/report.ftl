<!DOCTYPE html>
<html>
<head>
    <title>Catalog Report</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .item { border: 1px solid #ccc; padding: 15px; margin-bottom: 10px; border-radius: 5px; }
        .title { font-size: 1.2em; font-weight: bold; color: #2c3e50; }
    </style>
</head>
<body>
    <h1>Bibliography Management System - Report</h1>

    <#list items as item>
        <div class="item">
            <div class="title">${item.title}</div>
            <div><b>Author:</b> ${item.author}</div>
            <div><b>Year:</b> ${item.year}</div>
            <div><b>Location:</b> <a href="${item.location}">${item.location}</a></div>
        </div>
    <#else>
        <p>Catalogul este gol.</p>
    </#list>
</body>
</html>