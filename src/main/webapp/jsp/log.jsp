<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<script type="text/javascript">
    $(function () {
        $("#bannerTable").jqGrid(
            {
                url : "${app}/log/page",
                datatype : "json",
                colNames : [ 'ID', '操作', '名字','日期','状态'],
                colModel : [
                    {name : 'id'},
                    {name : 'thing'},
                    {name : 'name'},
                    {name : 'date'},
                    {name : 'result'}

                ],
                rowNum : 2,
                rowList : [ 2, 3, 4 ],
                pager : '#LogPage',
                sortname : 'id',
                mtype : "post",
                viewrecords : true,
                sortorder : "desc",
                styleUI: "Bootstrap",
                height:"280px",
                autowidth: true,
                multiselect:true,
                caption : "日志信息"
            });

    });
</script>
<table id="LogTable"></table>
<div id="LogPage" style="height: 50px"></div>