<!DOCTYPE HTML>
<!--
/*
 * jQuery File Upload Plugin Basic Demo
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2013, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * https://opensource.org/licenses/MIT
 */
-->
<html lang="en">
<head>
<!-- Force latest IE rendering engine or ChromeFrame if installed -->
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
<meta charset="utf-8">
<title>jquery file插件基本上传</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<@link  href="/static/css/normalize.css" />
<@link  href="/webjars/bootstrap/4.2.1/css/bootstrap.min.css" />
<@link  href="/static/libs/jQuery-File-Upload/css/jquery.fileupload.css" />


<@link  href="/static/css/jquery_upload/basic.css" />

</head>
<body>

<div class="container">
    <h1>jQuery File Upload Demo</h1>
    <blockquote>
        	基本文件上传
    </blockquote>
    <div class="demo1">
    	<!-- 单个文件上传 -->
	    <span class="btn btn-success fileinput-button">
	        <i class="glyphicon glyphicon-plus"></i>
	        <span>选择单个文件</span>
	        <input class="fileupload" type="file" name="file" >
	    </span>
	    <div  class="progress">
	        <div class="progress-bar progress-bar-success"></div>
	    </div>
	    <!-- 显示结果 -->
	    <div  class="result">
	    	
	    </div>
    </div>
    
    
    <blockquote>
        	多个文件上传
    </blockquote>
    <!-- 实例2 -->
    <div class="demo2">
    	<!-- 单个文件上传 -->
	    <span class="btn btn-success fileinput-button">
	        <i class="glyphicon glyphicon-plus"></i>
	        <span>选择多个文件</span>
	        <input class="fileupload" type="file" name="files[]"  multiple />
	    </span>
	    <div  class="progress">
	        <div class="progress-bar progress-bar-success"></div>
	    </div>
	    <!-- 显示结果 -->
	    <div  class="result">
	    	
	    </div>
    </div>
    
    
</div>


<@script  src="/webjars/jquery/3.3.1/jquery.min.js" />
<@script  src="/static/libs/jQuery-File-Upload/js/vendor/jquery.ui.widget.js" />
<@script  src="/static/libs/jQuery-File-Upload/js/jquery.iframe-transport.js" />
<@script  src="/static/libs/jQuery-File-Upload/js/jquery.fileupload.js" />

<@script  src="/webjars/bootstrap/4.2.1/js/bootstrap.min.js" />



<script type="text/javascript" >

$(function () {
    'use strict';
    
    //注:  ctx 定义在/fileserver-boot/src/main/resources/templates/common/global.ftl
    $('.demo1 .fileupload').fileupload({
    	// 默认是files[]
    	paramName: 'file', 
        url: ctx + "/file/upload/single/file/temp",
        dataType: 'json',
        done: function (e, data) {
        	
        	$('<p/>').text(JSON.stringify(data.result)).appendTo('.demo1 .result');
            
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('.demo1 .progress-bar').css(
                'width', progress + '%'
            );
        }
    }).prop('disabled', !$.support.fileInput)
   .parent().addClass($.support.fileInput ? undefined : 'disabled');
    
    
    
    // 多个文件上传
    $('.demo2 .fileupload').fileupload({
    	// 默认是files[]
    	paramName: 'files[]', 
        url: ctx + "/file/upload/multiple/files/temp",
        dataType: 'json',
        // 多个文件一次性上传
        singleFileUploads: false,
        done: function (e, data) {
        	console.log(data);
        	$('<p/>').text(JSON.stringify(data.result)).appendTo('.demo2 .result');
            
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('.demo2 .progress-bar').css(
                'width', progress + '%'
            );
        }
    }).prop('disabled', !$.support.fileInput)
   .parent().addClass($.support.fileInput ? undefined : 'disabled');
    
    
});

</script>
</body>
</html>
