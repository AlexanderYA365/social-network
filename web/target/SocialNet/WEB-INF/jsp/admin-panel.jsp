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
    <title>Все аккаунты</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/datatables.min.css"/>
</head>
<body>
<%@ include file="navbar.jsp" %>
<table border="0" margin="0" padding="0" width="100%"
       class="dataTables_wrapper" id="admin">
    <thead>
    <tr>
        <th>id</th>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Отчество</th>
        <th>Дата рождения</th>
        <th>icq</th>
        <th>удалить</th>
    </tr>
    </thead>
</table>
<%@ include file="footers.jsp" %>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js.map"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datatables.min.js"></script>
<script>
    $(document).ready(function () {
        var url = window.location;
        var string = url.toString();
        var newUrl = string.substring(0, string.lastIndexOf("/"));
        console.log(newUrl)
        const table = $('#admin').DataTable({
            processing: true,
            serverSide: true,
            ajax: {
                url: newUrl + '/getAccounts',
            },
            columns: [
                {data: 'id'},
                {data: 'accountDetails.name'},
                {data: 'accountDetails.surname'},
                {data: 'accountDetails.lastName'},
                {
                    data: 'accountDetails.date',
                    render: DataTable.render.date(),
                },
                {data: 'accountDetails.icq'},
                {defaultContent: '<button class="btn btn-secondary">удалить</button>'},
            ],
        });

        $('#admin tbody').on('click', 'button', function () {
            const data = table.row($(this).parents('tr')).data();
            var id = data.id
            $.ajax({
                type: "DELETE",
                url: newUrl + '/delete/' + id
            });
            table.ajax.reload(function (json) {
                $('#myInput').val(json.lastInput);
            });
        });
    });
</script>
</body>
</html>