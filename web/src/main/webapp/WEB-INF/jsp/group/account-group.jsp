<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 30.01.2022
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Ваши группы</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="../navbar.jsp" %>

<button class="btn btn-secondary" onclick="location.href='create-group'">Создать группу</button>
<button class="btn btn-secondary" onclick="location.href='account-find-group'">поиск группы</button>

<div class="container" style="margin-top: 1rem;">
    <div class="list text-center">
    </div>
</div>

<%@ include file="../footers.jsp" %>
<script>
    var currentScrollHeight = 0;
    var count = 1;
    var page = 0;
    var url = window.location;
    var string = url.toString();
    var newUrl = string.substring(0, string.lastIndexOf("/"));
    var size;
    var accountId;

    jQuery(document).ready(function ($) {
        accountId = "${account.id}";
        size = getSize();
        callData(count);
        count = 1;
    });

    $(window).on("scroll", function () {
        const scrollHeight = $(document).height();
        const scrollPos = Math.floor($(window).height() + $(window).scrollTop());
        const isBottom = scrollHeight - 100 < scrollPos;
        if (isBottom && currentScrollHeight < scrollHeight && count < size) {
            callData(count);
            count += 6;
            currentScrollHeight = scrollHeight;
        }
    });

    function getSize() {
        let res;
        $.ajax({
            type: "GET",
            url: newUrl + '/get-size-groups/' + accountId,
            async: false,
            dataType: "json",
            success: function (result) {
                res = result;
            },
            error: function () {
                res = 0;
            }
        });
        return res;
    }

    function callData(counter) {
        $.ajax({
            type: "GET",
            url: newUrl + '/get-groups/' + accountId + '/' + page,
            dataType: "json",
            success: function (result) {
                create(result)
            },
            error: function (result) {
                $('<div class="card my-4 py-3"><h4 class="card-title">Запись отсутствует</h4><p>' + 'Данные отсутсвуют' +
                    '</p></div>').appendTo('.list');
            }
        });
        page++;
    }

    function createDiv(result) {
        let id = result.id;
        console.log(result)
        var div = document.createElement('div');
        div.type = 'div';
        div.className = 'card my-4 py-3';
        div.addEventListener("click", function () {
            buttonPressed(result.id);
        });
        var h4 = document.createElement('h4');
        h4.type = 'h4';
        h4.textContent = 'Название группы: ' + result.group.groupName;
        h4.className = 'text-muted mb-1';
        var p1 = document.createElement('p');
        p1.type = 'p';
        p1.textContent = 'Описание группы: ' + result.group.info;
        p1.className = 'fw-bold mb-1';
        h4.appendChild(p1);
        div.appendChild(h4);
        $(div).appendTo('.list');
    }

    function create(result) {
        for (var i = 0; i < result.length; i++) {
            createDiv(result[i]);
        }
    }

    function buttonPressed(id) {
        console.log("id - " + id);
        var url = newUrl + "/show-group" + "?groupId=" + id;
        console.log(url)
        var payload = {
            groupId: id
        };
        var form = document.createElement('form');
        form.style.visibility = 'hidden';
        form.method = 'POST';
        form.action = url;
        for (key in Object.keys(payload)) {
            var input = document.createElement('submit');
            input.name = 'id';
            input.value = id;
            form.append(input);
        }
        document.body.appendChild(form);
        form.submit();
    }

</script>
</body>
</html>