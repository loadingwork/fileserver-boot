<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>结构</title>
</head>
<body>
   	<div>
    	<label>上传状态:</label>
    	<span>${result.status}</span>
    </div>
    <#if result.status=="ok">
    	<div>
	    	<label>文件唯一标识符:</label>
	    	<span>${result.code}</span>
	    </div>
	    <div>
	    	<label>文件名称:</label>
	    	<span>${result.name}</span>
	    </div>
	    <div>
	    	<label>文件大小:</label>
	    	<span>${result.size}</span>
	    </div>
	    <div>
	    	<label>访问的url全路径:</label>
	    	<a href="${result.url}"  target="_blank">${result.url}</a>
	    </div>
	    <div>
	    	<label>缩略图访问的url全路径:</label>
	    	<a href="${result.thumbnailUrl}" target="_blank">${result.thumbnailUrl}</a>
	    </div>
	    <div>
	    	<label>删除文件url, 文件生效无法使用:</label>
	    	<span>${result.deleteUrl}</span>
	    </div>
	    <div>
	    	<label>删除文件方式:</label>
	    	<span>${result.deleteType}</span>
	    </div>
	    <#else>
	    <div>
	    	<label>上传错误信息:</label>
	    	<span>${result.errmsg}</span>
	    </div>
    </#if>
    
    
    
</body>
</html>