<!DOCTYPE HTML>
<!--
/*
 * jQuery File Upload Plugin Basic Plus Demo
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
<title>jQuery File Upload高级版本</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<@link  href="/static/css/normalize.css" />
<@link  href="/webjars/bootstrap/4.2.1/css/bootstrap.min.css" />
<@link  href="/static/libs/jQuery-File-Upload/css/jquery.fileupload.css" />

<style type="text/css">
	
	.result {
		word-break: break-all;
		word-wrap: word-wrap;
		overflow: hidden;
	}
	
	.progress {
	   margin: 10px 0;
	}
	
</style>

</head>
<body>


<div class="container">
    <h1>jQuery File Upload Demo</h1>
    
    <div class="demo1">
    	<blockquote>
	    	高级文件上传
	    </blockquote>
	    
	    <!-- The fileinput-button span is used to style the file input field as button -->
	    <span class="btn btn-success fileinput-button">
	        <i class="glyphicon glyphicon-plus"></i>
	        <span>添加文件</span>
	        
	        <!-- 单个文件接口 -->
	        <input id="fileupload" type="file" name="file" multiple>
	    </span>
    	<!-- The global progress bar -->
	    <div id="progress" class="progress">
	        <div class="progress-bar progress-bar-success"></div>
	    </div>
	    <!-- The container for the uploaded files -->
    	<div id="files" class="files"></div>
    	
    </div>
    
    
</div>



<@script  src="/webjars/jquery/3.3.1/jquery.min.js" />
<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<@script  src="/static/libs/jQuery-File-Upload/js/vendor/jquery.ui.widget.js" />
<!-- 图片预览与图片大小改变 -->
<@script  src="/static/libs/JavaScript-Load-Image/load-image.all.min.js" />
<!-- 图片大小改变 -->
<@script  src="/static/libs/JavaScript-Canvas-to-Blob/canvas-to-blob.min.js" />
<!-- Bootstrap 不是必须的, 只有响应式可能用到 -->
<@script  src="/webjars/bootstrap/4.2.1/js/bootstrap.min.js" />
<!-- 浏览器没有xhr对象时, 使用Iframe上传 -->
<@script  src="/static/libs/jQuery-File-Upload/js/jquery.iframe-transport.js" />
<!-- 基本上传插件 -->
<@script  src="/static/libs/jQuery-File-Upload/js/jquery.fileupload.js" />
<!-- 进度条插件 -->
<@script  src="/static/libs/jQuery-File-Upload/js/jquery.fileupload-process.js" />
<!-- 上传预览 和 大小改变插件 -->
<@script  src="/static/libs/jQuery-File-Upload/js/jquery.fileupload-image.js" />
<!-- 音频预览插件 -->
<@script  src="/static/libs/jQuery-File-Upload/js/jquery.fileupload-audio.js" />
<!-- 视频插件 -->
<@script  src="/static/libs/jQuery-File-Upload/js/jquery.fileupload-video.js" />
<!-- 文件验证插件 -->
<@script  src="/static/libs/jQuery-File-Upload/js/jquery.fileupload-validate.js" />



<script type="text/javascript" >
//注:  ctx 定义在/fileserver-boot/src/main/resources/templates/common/global.ftl

$(function () {
    'use strict';
    // 
    var url = ctx + "/file/upload/single/file/temp";
    var uploadButton = $('<button/>')
            .addClass('btn btn-primary')
            .prop('disabled', true)
            .text('上传中...')
            .on('click', function () {
            	// 获取jq 对象
                var $this = $(this),
                    data = $this.data();
                $this.off('click')
                    .text('取消')
                    .on('click', function () {
                        $this.remove();
                        data.abort();
                    });
                data.submit().always(function () {
                    $this.remove();
                });
            });
    
    
    $('#fileupload').fileupload({
        url: url,
        dataType: 'json',
        autoUpload: false,
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
        maxFileSize: 999000,
        // Enable image resizing, except for Android and Opera,
        // which actually support image resizing, but fail to
        // send Blob objects via XHR requests:
        disableImageResize: /Android(?!.*Chrome)|Opera/
            .test(window.navigator.userAgent),
        previewMaxWidth: 100,
        previewMaxHeight: 100,
        previewCrop: true
    }).on('fileuploadadd', function (e, data) {
    	// 添加新文件时调用
        data.context = $('<div class="item" />').appendTo('#files');
    	
        $.each(data.files, function (index, file) {
            var node = $('<p/>').append($('<span/>').text(file.name));
            if (!index) {
                node.append('<br>').append(uploadButton.clone(true).data(data));
            }
            node.appendTo(data.context);
        });
        
    }).on('fileuploadprocessalways', function (e, data) {
    	// Processing Callback 进度条回调
        var index = data.index,
            file = data.files[index],
            node = $(data.context.children()[index]);
        if (file.preview) {
            node.prepend('<br>').prepend(file.preview);
        }
        if (file.error) {
            node.append('<br>').append($('<span class="text-danger"/>').text(file.error));
        }
        if (index + 1 === data.files.length) {
            data.context.find('button').text('上传').prop('disabled', !!data.files.error);
        }
    }).on('fileuploadprogressall', function (e, data) {
    	// 文件进度条
        var progress = parseInt(data.loaded / data.total * 100, 10);
        $('#progress .progress-bar').css(
            'width', progress + '%'
        );
    }).on('fileuploaddone', function (e, data) {
    	
    	var link = $('<div class="result">').text(JSON.stringify(data.result));
        // $(data.context.children()[0]).wrap(link);
        $(data.context.children()[0]).append(link);
    	
    }).on('fileuploadfail', function (e, data) {
    	// 文件上传失败
        $.each(data.files, function (index) {
            var error = $('<span class="text-danger"/>').text('文件上传失败');
            $(data.context.children()[index]).append('<br>').append(error);
        });
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');
	});
	
</script>
</body>
</html>
