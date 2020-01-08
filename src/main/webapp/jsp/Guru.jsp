<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    $(function () {
        $("#GuruTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/guru/GuruPage",
                datatype : "json",
                height : 300,
                colNames : [ 'ID', 'Name', 'Photo','Status','NickName'],
                colModel : [
                    {name : 'id', },
                    {name : 'name',align:"center",editable:true},
                    {name : 'photo',align:"center",editable:true,edittype:"file",editoptions: {enctype:"multipart/form-data"},formatter:function (data) {

                            return "<img style='width: 100px;height: 60px' src='"+data+"'>";}},
                    {name : 'status',formatter:function (data) {
                            if (data=="1"){
                                return "展示";
                            } else return "冻结";
                        },align:"center",editable:true,editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:冻结"}},
                    {name : 'nickName',align:"center",editable:true}
                ],
                rowNum : 4,
                rowList : [ 10, 20, 30 ],
                pager : '#GuruPage',
                sortname : 'id',
                mtype : "post",
                styleUI: "Bootstrap",
                multiselect : true,
                viewrecords : true,
                sortorder : "desc",
                caption : "上师",
                editurl :"${pageContext.request.contextPath}/guru/GuruEdit"
            });
        jQuery("#GuruTable").jqGrid('navGrid', '#GuruPage', {edit: true, add: true, del: true},{
            closeAfterEdit:true,
            afterSubmit:function (response,postData) {
                var guruId = response.responseJSON.guruId;
                $.ajaxFileUpload({
                    // 指定上传路径
                    url:"${pageContext.request.contextPath}/guru/uploadGuru",
                    type:"post",
                    datatype:"json",
                    // 发送添加图片的id至controller
                    data:{guruId:guruId},
                    // 指定上传的input框id
                    fileElementId:"photo",
                    success:function (data) {
                        $("#GuruTable").trigger("reloadGrid");
                    }
                });
                // 防止页面报错
                return postData;
            }
        },{closeAfterAdd:true,
            afterSubmit:function (response,postData) {
                var guruId = response.responseJSON.guruId;
                $.ajaxFileUpload({
                    // 指定上传路径
                    url:"${pageContext.request.contextPath}/guru/uploadMap",
                    type:"post",
                    datatype:"json",
                    // 发送添加图片的id至controller
                    data:{guruId:guruId},
                    // 指定上传的input框id
                    fileElementId:"photo",
                    success:function (data) {
                        $("#GuruTable").trigger("reloadGrid");
                    }
                });
                // 防止页面报错
                return postData;
            }
        },{closeAfterDel: true} );
    });
</script>
<table id="GuruTable"></table>
<div id="GuruPage" style="height: 50px"></div>