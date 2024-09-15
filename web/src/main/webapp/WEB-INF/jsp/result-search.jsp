<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 29.01.2022
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>All Accounts</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/result-search.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/datatables.min.css"/>
</head>
<body>
<%@ include file="navbar.jsp" %>
<table border="0" margin="0" padding="0" width="100%"
       class="dataTables_wrapper" id="search-result">
    <thead>
    <tr>
        <th>id</th>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Отчество</th>
        <th>isGroup</th>
    </tr>
    </thead>
</table>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datatables.min.js"></script>
<script>
    var url = window.location;
    var string = url.toString();
    var newUrl = string.substring(0, string.lastIndexOf("/"));
    $(document).ready(function () {
        $('#search-result').dataTable({
            processing: true,
            serverSide: true,
            ajax: {
                url: newUrl + '/getSearch',
                dataSrc: 'searchResults'
            },
            columns: [
                {
                    data: 'id',
                    name: 'id',
                    visible: false,
                },
                {
                    data: 'name',
                    name: 'name',
                    render: function (data, type, row, meta) {
                        if (row.isGroup) {
                            return '<a class=" d-inline-block fw-normal w-100 h-100 pe-auto" href="show-group?groupId='
                                + row.id + '">' + row.name + '</a>';
                        } else {
                            return '<a class=" d-inline-block fw-normal w-100 h-100 pe-auto" href="show-friend?id='
                                + row.id + '">' + row.name + '</a>';
                        }
                    },
                },
                {
                    data: 'surname',
                    name: 'surname',
                    render: function (data, type, row, meta) {
                        if (row.isGroup) {
                            return '<a class=" d-inline-block fw-normal w-100 h-100 pe-auto" href="show-group?groupId='
                                + row.id + '">' + '</a>';
                        } else {
                            return '<a class=" d-inline-block fw-normal w-100 h-100 pe-auto" href="show-friend?id='
                                + row.id + '">' + row.surname + '</a>';
                        }
                    },
                },
                {
                    data: 'lastName',
                    name: 'lastName',
                    render: function (data, type, row, meta) {
                        if (row.isGroup) {
                            return '<a class=" d-inline-block fw-normal w-100 h-100 pe-auto" href="show-group?groupId='
                                + row.id + '">' + '</a>';
                        } else {
                            return '<a class=" d-inline-block fw-normal w-100 h-100 pe-auto" href="show-friend?id='
                                + row.id + '">' + row.lastName + '</a>';
                        }
                    },
                },
                {
                    data: 'isGroup',
                    name: 'isGroup',
                    render: function (data) {
                        return data ? 'Группы' : 'Пользователи';
                    },
                    visible: false,
                },
            ],
            "drawCallback": function (settings) {
                var api = this.api();
                var rows = api.rows({page: 'current'}).nodes();
                var last = null;
                api.column(4, {page: 'current'}).data().each(function (group, i) {
                    if (last !== group) {
                        $(rows).eq(i).before(
                            '<tr class="group"><td colspan="5">' + (group ? 'Группы' : 'Пользователи') + '</td></tr>'
                        );

                        last = group;
                    }
                });
            }
        });
    });
</script>

</body>
</html>