<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests" />
    <title>${file.name}压缩包预览</title>
   <script src="proxyfile-preview/js/jquery-3.6.1.min.js"></script>
     <#include "*/commonHeader.ftl">
   <script src="proxyfile-preview/js/base64.min.js" type="text/javascript"></script>
   <link href="proxyfile-preview/css/zTreeStyle.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="proxyfile-preview/js/jquery.ztree.core.js"></script>
        <style type="text/css">
        body {
            background-color: #404040;
        }
        h1 {font-size: 24px;line-height: 34px;text-align: center;}
        a {color:#3C6E31;text-decoration: underline;}
        a:hover {background-color:#3C6E31;color:white;}
        code {color: #2f332a;}
       div.zTreeDemoBackground {
           max-width: 880px;
           text-align:center;
            margin:0 auto;
            border-radius:3px;
            box-shadow:rgba(0,0,0,0.15) 0 0 8px;
            background:#FBFBFB;
            border:1px solid #ddd;
            margin:1px auto;
            padding:5px;
       }
       
    </style>
</head>
<body>
<div class="zTreeDemoBackground left">
<h1>mimiFileView</h1>
    <ul id="treeDemo" class="ztree"></ul>
</div>
<script>
    var settings = {
        data: {
            simpleData: {
                enable: true,  //true 、 false 分别表示 使用 、 不使用 简单数据模式
                idKey: "id",   //节点数据中保存唯一标识的属性名称
                pIdKey: "pid", //节点数据中保存其父节点唯一标识的属性名称
                rootPId: ""
            }
        },
        callback: {
            onClick: chooseNode,
        }
    };

function isNotEmpty(value) {
  return value !== null && value !== undefined && value !== '' && value !== 0 && !(value instanceof Array && value.length === 0) && !isNaN(value);
}
function getQueryParam(url, param) {
  var urlObj = new URL(url);
  return urlObj.searchParams.get(param);
}
var currentUrl = window.location.href;
var keyword = getQueryParam(currentUrl, 'watermarkTxt');
    function chooseNode(event, treeId, treeNode) {
        if (!treeNode.isParent) {
            var path = '${baseUrl}'+treeNode.id+"?kkCompressfileKey="+'${fileTree}'+"&kkCompressfilepath="+encodeURIComponent(treeNode.id)+"&fullfilename="+encodeURIComponent(treeNode.name);
           if (isNotEmpty(keyword)){
             location.href = "${baseUrl}onlinePreview?url=" + encodeURIComponent(Base64.encode(path))+"&watermarkTxt="+keyword;
           }else{
             location.href = "${baseUrl}onlinePreview?url=" + encodeURIComponent(Base64.encode(path));}
         
        }
    }
    $(document).ready(function () {
    var url = "http://"+'${fileTree}';  //添加http协议方法
       $.ajax({
            type: "get",
            url: "${baseUrl}directory?urls="+encodeURIComponent(Base64.encode(url)),
            success: function (res) {
                zTreeObj = $.fn.zTree.init($("#treeDemo"), settings, res); //初始化树
                zTreeObj.expandAll(true);   //true 节点全部展开、false节点收缩
            }
        });
    });
        window.onload = function () {
        initWaterMark();
    }
</script>
</body>
</html>