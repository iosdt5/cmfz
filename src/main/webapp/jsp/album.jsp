<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>

<script type="text/javascript">
    $(function () {
        $("#ftable").jqGrid(
            {
                url : '${pageContext.request.contextPath}/album/AlbumPage',
                datatype : "json",
                height : 300,
                colNames : [ 'ID','标题','作者','播音','描述','评分','数量','状态','专辑图片','创建日期'],
                colModel : [
                    {name : 'id',align:"center"},
                    {name : 'title',align:"center",editable:true},
                    {name : 'author',align:"center",editable:true},
                    {name : 'broadcast',align:"center",editable:true},
                    {name : 'descript',align:"center",editable:true},
                    {name : 'score',align:"center",editable:true},
                    {name : 'count',align:"center"},
                    {name :'status',formatter:function (data) {
                            if (data=="1"){
                                return "展示";
                            } else return "冻结";
                        },align:"center",editable:true,editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:冻结"}},
                    {name : 'cover',align:"center",editable: true,edittype:"file",editoptions: {enctype:"multipart/form-data"},formatter:function (data) {

                            return "<img style='width: 100px;height: 60px' src='"+data+"'>";}
                    },
                    {name : 'createDate',align:"center",editable:true,editrules:{required:true},edittype: "date"},

                ],
                rowNum : 4,
                rowList : [ 8, 10, 20, 30 ],
                pager : '#fpage',
                sortname : 'id',
                viewrecords : true,
                sortorder : "desc",
                mtype:"get",
                multiselect : true,
                autowidth:true,
                styleUI:"Bootstrap",
                // 开启子表格支持
                subGrid : true,
                editurl: "${pageContext.request.contextPath}/album/albumEdit",
                caption : "专辑管理",
                // subgrid_id:父级行的Id  row_id:当前的数据Id
                subGridRowExpanded : function(subgrid_id, row_id) {
                    // 调用生产子表格的方法
                    // 生成表格 | 生产子表格工具栏
                    addSubgrid(subgrid_id,row_id);
                },
                // 删除表格的方法
                subGridRowColapsed : function(subgrid_id, row_id) {
                }
            });
        $("#ftable").jqGrid('navGrid', '#fpage', {add : true, edit : true,del : true},
            {
                closeAfterEdit:true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                //                   status 完成后 返回值信息
                afterSubmit:function (response,postData) {
                    var albumId = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        //上传到哪
                        url: "${pageContext.request.contextPath}/album/uploadAlbum",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{albumId:albumId},
                        // 指定上传的input框id
                        fileElementId:"cover",
                        success:function (data) {
                            $("#ftable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            },
            {
                closeAfterAdd:true,
                afterSubmit:function (response,postData) {
                    var albumId = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        //上传到哪
                        url: "${pageContext.request.contextPath}/album/uploadAlbum",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{albumId:albumId},
                        // 指定上传的input框id
                        fileElementId:"cover",
                        success:function (data) {
                            $("#ftable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            },
            {
                closeAfterDel: true,
            }
        );
    });
    // subgrid_id 父行级id
    function addSubgrid(subgrid_id,row_id) {
        // 声明子表格Id
        var sid = subgrid_id + "table";
        // 声明子表格工具栏id
        var spage = subgrid_id + "page";
        $("#"+subgrid_id).html("<table id='" + sid + "' class='scroll'></table><div id='"+ spage +"' style='height: 50px'></div>")
        $("#" + sid).jqGrid(
            {
                // 指定的json文件
                // 指定查询的url 根据专辑id 查询对应章节 row_id: 专辑id
                url : "${pageContext.request.contextPath}/chapter/chapterPage?albumId="+row_id,
                datatype : "json",
                colNames : [ 'id', '标题','路径','大小','时间','发布日期','操作'],
                colModel : [
                    {name : "id",hidden:true,align:"center"},
                    {name : "title",align:"center"},
                    {name : "url",align:"center"},
                    {name : "size",align:"center"},
                    {name : "time",align:"center"},
                    {name : "createTime",align:"center"},
                    {name : "url",formatter:function (cellvalue, options, rowObject) {
                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"download('"+cellvalue+"')\">下载</button>&nbsp;&nbsp;";
                            //                                                                声明一个onPlay方法 --> 显示模态框 ---> 为audio标签添加src  需要url路径作为参数传递
                            //                                                              'onPlay(参数)' ---> \"onPlay('"+cellvalue+"')\"
                            button+= "<button type=\"button\" class=\"btn btn-danger\" onclick=\"onPlay('"+cellvalue+"')\">在线播放</button>";
                            return button;
                        },editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}}
                ],
                rowNum : 2,
                pager : spage,
                sortname : 'num',
                sortorder : "asc",
                height : '100%',
                autowidth: true,
                editurl: "${pageContext.request.contextPath}/chapter/chapterEdit?albumId="+row_id,
                styleUI:"Bootstrap",
            });
        $("#" + sid).jqGrid('navGrid',"#" + spage,
            {edit : true, add : true,del : true},
            {
                closeAfterEdit:true,
                afterSubmit:function (response,postData) {
                    var chapterId = response.responseJSON.chapterId;
                    $.ajaxFileUpload({
                        //上传到哪
                        url: "${pageContext.request.contextPath}/chapter/uploadChapter",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{chapterId:chapterId},
                        // 指定上传的input框id
                        fileElementId:"url",
                        success:function (data) {
                            $("#ftable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return postData;
                }

            },
            {closeAfterAdd:true,
                afterSubmit:function (response,postData) {
                    var chapterId = response.responseJSON.chapterId;
                    $.ajaxFileUpload({
                        //上传到哪
                        url: "${pageContext.request.contextPath}/chapter/uploadChapter",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{chapterId:chapterId},
                        // 指定上传的input框id
                        fileElementId:"url",
                        success:function (data) {
                            $("#ftable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            },
            {
                closeAfterDel:true,
            });
    };
    function onPlay(cellValue) {
        $("#music").attr("src",cellValue);
        $("#myModal").modal("show");
    }
    function download(cellValue) {
        location.href = "${pageContext.request.contextPath}/chapter/downloadChapter?url="+cellValue;
    }
</script>
<body>
<table id="ftable"></table>
<div id="fpage" style="height: 50px"></div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <audio id="music" src="" controls="controls">
        </audio>
    </div><!-- /.modal -->
</div>
</body>
