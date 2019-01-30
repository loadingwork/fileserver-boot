<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>普通form表单上传</title>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

<!-- 使用自定义宏与上面等价 -->
<@link  href="/static/css/normalize.css" />
<@link  href="/webjars/bootstrap/4.2.1/css/bootstrap.min.css" />
<@link  href="/static/libs/startbootstrap-simple-sidebar/simple-sidebar.css" />

<@link  href="/static/css/demos.css" />

</head>
<body>

<div id="wrapper">
	
	
	<!-- 侧面菜单 -->
	<@components.menu_aside active="4"  title="普通form表单上传" />
    
    <!-- Page Content -->
    <div id="page-content-wrapper">
        <div class="container-fluid">

            <div class="row">
                <div class="col-lg-12">
                
                		<div  class="form-demo1">
                			<blockquote class="elem-quote">form单个文件上传</blockquote>
                			
               				<form class="form" action="/file/upload/singleFormUpload.do" role="form"  method="post" enctype="multipart/form-data" >
               			
               					<!-- 隐藏业务编码 -->
	                			<input type="hidden"  name="code" value="temp" />
               			
               					<div class="form-group">
               						<label>文件</label>
               						<input class="form-control-file"  type="file"  name="file"  autocomplete="off" />
               					</div>
	                			
	                			<button class="btn btn-primary" type="submit">提交</button>
	                		</form>
                			
                		</div>
                		
                		<div  class="form-demo2">
                			<blockquote class="elem-quote">form多个文件上传</blockquote>
                			
               				<form class="form" action="/file/upload/multipleFormUpload.do" role="form"  method="post" enctype="multipart/form-data" >
               			
               					<!-- 隐藏业务编码 -->
	                			<input type="hidden"  name="code" value="temp" />
               			
               					<div class="form-group">
               						<label>文件1</label>
               						<input class="form-control-file"  type="file"  name="file[]"  autocomplete="off" />
               					</div>
               					
               					<div class="form-group">
               						<label>文件2</label>
               						<input class="form-control-file"  type="file"  name="file[]"  autocomplete="off" />
               					</div>
	                			
	                			<button class="btn btn-primary" type="submit">提交</button>
	                		</form>
                			
                	  </div>
                	  
                	  <div  class="form-demo3">
                			<blockquote class="elem-quote">隐藏表单域上传</blockquote>
                			
               				<form id="form-demo3" class="form" 
               					  action="/file/upload/singleFormUpload.do" 
               					  role="form"  method="post" enctype="multipart/form-data"
               					  style="display: none;"
               					   >
               			
               					<!-- 隐藏业务编码 -->
	                			<input type="hidden"  name="code" value="temp" />
               			
               					<input onchange="form_demo3_submit()" id="form-demo3-file" class="form-control-file"  type="file"  name="file"  autocomplete="off" />
	                		</form>
	                		
	                		
	                		<!-- 其它可以使#form-demo3-file有点击动作的也是可以的 -->
	                		<label for="form-demo3-file" >选择文件</label>
                	  </div>
                	  
                	  
                
                </div>
            </div>
            
        </div>
    </div>
    <!-- /#page-content-wrapper -->
    
</div>

<script type="text/javascript">


function form_demo3_submit() {
	document.getElementById('form-demo3').submit();
}


</script>

</body>
</html>