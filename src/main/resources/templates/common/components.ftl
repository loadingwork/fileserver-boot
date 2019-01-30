
<!-- 侧面菜单 -->
<#macro menu_aside  active="1"  title="小文件系统" >

	<!-- Sidebar -->
    <div id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <li class="sidebar-brand">
                <a href="#">
                  	 ${title}
                </a>
            </li>
            <li>
                <a href="/home.do"  <#if active?string=="1">class="active"</#if> >首页</a>
            </li>
            
            <!-- 
            <li>
                <a href="/"  <#if active?string=="2">class="active"</#if> >文件列表</a>
            </li>
            <li>
                <a href="/file/toUpload.do" <#if active?string=="3">class="active"</#if> >上传演示</a>
            </li>
             -->
            
            <li>
                <a href="/file/demos/toCommonForm.do" <#if active?string=="4">class="active"</#if> >普通form表单上传</a>
            </li>
            
            <li>
                <a href="/file/demos/toAjaxIndex.do" <#if active?string=="5">class="active"</#if> >普通formdata异步上传</a>
            </li>
            
            <li>
                <a href="/file/demos/toJqueryFormIndex.do" <#if active?string=="6">class="active"</#if> >jquery-form插件表单上传</a>
            </li>
            
            
        </ul>
    </div>
    <!-- /#sidebar-wrapper -->
	
</#macro>

