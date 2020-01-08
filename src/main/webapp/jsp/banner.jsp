<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    $(function () {
        $("#bannerTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/banner/bannerPage",
                datatype : "json",
                height : 300,
                colNames : [ 'ID', 'Title', 'Url','Href','Create_date','Descript','Status'],
                colModel : [
                    {name : 'id', },
                    {name : 'title',align:"center",editable:true},
                    {name : 'url',align:"center",editable:true,edittype:"file",editoptions: {enctype:"multipart/form-data"},formatter:function (cellvalue, options, rowObject) {
                            return "<img src='${pageContext.request.contextPath}/"+cellvalue+"' style='width:100px;height:60px'>";
                        }},
                    {name : 'href',align:"center",editable:true},
                    {name : 'createDate',align:"center",editable:true,editrules:{required:true},edittype: "date"},
                    {name : 'descript',align:"center",editable:true},
                    {name : 'status',formatter:function (data) {
                            if (data=="1"){
                                return "展示";
                            } else return "冻结";
                        },align:"center",editable:true,editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:冻结"}}
                ],
                rowNum : 4,
                rowList : [ 10, 20, 30 ],
                pager : '#bannerPage',
                sortname : 'id',
                mtype : "post",
                styleUI: "Bootstrap",
                viewrecords : true,
                sortorder : "desc",
                caption : "轮播图",
                editurl :"${pageContext.request.contextPath}/banner/banner"
            });
        jQuery("#bannerTable").jqGrid('navGrid', '#bannerPage', {edit: true, add: true, del: true},{
            closeAfterEdit:true,
            afterSubmit:function (response,postData) {
                var bannerId = response.responseJSON.bannerId;
                $.ajaxFileUpload({
                    // 指定上传路径
                    url:"${pageContext.request.contextPath}/banner/uploadMap",
                    type:"post",
                    datatype:"json",
                    // 发送添加图片的id至controller
                    data:{bannerId:bannerId},
                    // 指定上传的input框id
                    fileElementId:"url",
                    success:function (data) {
                        $("#bannerTable").trigger("reloadGrid");
                    }
                });
                // 防止页面报错
                return postData;
            }
        },{closeAfterAdd:true,
            afterSubmit:function (response,postData) {
                var bannerId = response.responseJSON.bannerId;
                $.ajaxFileUpload({
                    // 指定上传路径
                    url:"${pageContext.request.contextPath}/banner/uploadMap",
                    type:"post",
                    datatype:"json",
                    // 发送添加图片的id至controller
                    data:{bannerId:bannerId},
                    // 指定上传的input框id
                    fileElementId:"url",
                    success:function (data) {
                        $("#bannerTable").trigger("reloadGrid");
                    }
                });
                // 防止页面报错
                return postData;
            }
        },{closeAfterDel: true} );
    });
</script>
<table id="bannerTable"></table>
<div id="bannerPage" style="height: 50px"></div>