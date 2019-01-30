<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>jquery form组件上传</title>

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
	<@components.menu_aside active="6"  title="jquery form组件上传" />
    
    <!-- Page Content -->
    <div id="page-content-wrapper">
        <div class="container-fluid">

            <div class="row">
                <div class="col-lg-12">
                
                	  <ul class="list-group">
                	  
						  <li class="list-group-item">
						  	<a  href="/file/demos/jquery/basic.do">基本上传</a>
						  </li>
						  
						  <li class="list-group-item">
						  	<a  href="/file/demos/jquery/basic_plus.do">高级上传</a>
						  </li>
						  
					  </ul>
                
                </div>
            </div>
            
        </div>
    </div>
    <!-- /#page-content-wrapper -->
    
</div>



<@script  src="/webjars/jquery/3.3.1/jquery.min.js" />
<script type="text/javascript">
	
	
	// ajax初步上传
	$("#ajax-demo1-file").change(function(){
		
		var formData = new FormData();
		// formData.append("file", document.getElementById("ajax-demo1-file").files[0]); // 文件
		// formData.append("file", $("#ajax-demo1-file")[0].files[0]); // 文件
		formData.append("file", $(this)[0].files[0]); // 文件
		
		// ctx 是全局的, 在constant.ftl
		$.ajax({
			  url: ctx + "/file/upload/single/file/temp",
			  type: "POST",
			  data: formData,
			  processData: false,
			  contentType: false,
			  dataType: 'json',
			  success: function (data) {
				  // debugger;
				  // console.log(data);
				  $('.ajax-demo1 > .result').html(JSON.stringify(data));
			  },
			  error: function(error) {
				  
			  }
			});
	});
	
</script>

</body>
</html>