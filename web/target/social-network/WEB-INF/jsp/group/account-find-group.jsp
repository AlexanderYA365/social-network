<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 09.02.2022
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Поиск групп</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/datatables.min.css"/>
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="card-footer">
    <div class="container d-flex">
        <div class="input-group mb-3">
            <div class="form-floating">
                <input name="group-name" id="group-name" class="form-control"/>
                <label for="group-name">Введите желаемое имя группы:</label>
            </div>
            <button class="btn btn-secondary" onclick="buttonPressed()">Поиск</button>
        </div>
    </div>
</div>
<div id="container" hidden="hidden">
    <table border="0" margin="0" padding="0" width="100%"
           class="dataTables_wrapper" id="found-group">
        <thead>
        <tr>
            <th>Имя группы</th>
            <th>Добавить</th>
        </tr>
        </thead>
    </table>
</div>

<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js.map"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datatables.min.js"></script>
<script>
    const buttonPressed = () => {
        var div = document.getElementById('container');
        div.removeAttribute("hidden");
        var url = window.location;
        var string = url.toString();
        var newUrl = string.substring(0, string.lastIndexOf("/"));
        $.ajax({
            url: newUrl + '/result-search?search=' + document.getElementById('group-name').value,
            method: 'get',
        });
        load();
    }

    function destroy() {
        $('#found-group').DataTable().destroy();
    }

    function load() {
        destroy();
        var url = window.location;
        var string = url.toString();
        var newUrl = string.substring(0, string.lastIndexOf("/"));
        const table = $('#found-group').DataTable({
            processing: true,
            searching: false,
            serverSide: true,
            ajax: {
                url: newUrl + '/get-groups',
                dataSrc: 'searchResults'
            },
            columns: [
                {data: 'name'},
                {defaultContent: '<button class="btn btn-secondary">Добавить</button>'},
            ]
        });

        $('#found-group tbody').on('click', 'button', function () {
            const data = table.row($(this).parents('tr')).data();
            let id = data.id;
            $.ajax({
                type: "GET",
                url: newUrl + '/add-group/' + id
            });
        });
    };
</script>
<%@ include file="../footers.jsp" %>
</body>
</html>