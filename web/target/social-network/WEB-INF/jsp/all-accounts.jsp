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
</head>
<body>
<%@ include file="navbar.jsp" %>
tttttttttyta
<br>
${messages}
<br>
<br>
<div class="container" style="margin-top: 1rem;">
    <div class="list text-center">
    </div>
</div>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js.map"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script>
    var currentscrollHeight = 0;
    var count = 1;
    var url = window.location;
    var string = url.toString();
    var newUrl = string.substring(0, string.lastIndexOf("/"));
    var size;

    jQuery(document).ready(function ($) {
        size = getSize();
        callData(count);
        count = 6;
    });

    $(window).on("scroll", function () {
        const scrollHeight = $(document).height();
        const scrollPos = Math.floor($(window).height() + $(window).scrollTop());
        const isBottom = scrollHeight - 100 < scrollPos;
        if (isBottom && currentscrollHeight < scrollHeight && count < size) {
            callData(count);
            count++;
            console.log("count - 1")
            console.log(count)
            currentscrollHeight = scrollHeight;
        }
    });

    function getSize() {
        let res;
        $.ajax({
            type: "GET",
            url: newUrl + '/test-get-size-message/',
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
            url: newUrl + '/test-get-message/' + counter,
            dataType: "json",
            success: function (result) {
                create(result)
            },
            error: function (result) {
                $('<div class="card my-4 py-3"><h4 class="card-title">Запись отсутствует</h4><p>' + 'Данные отсутсвуют' + '</p></div>').appendTo('.list');
            }
        });
    }

    function createDiv(result) {
        let id = result.id;
        var div = document.createElement('div');
        let messageDate = new Date(result.publicationDate);
        div.type = 'div';
        div.className = 'card my-4 py-3';
        div.addEventListener("click", function () {
            buttonPressed(id);
        });
        var h4 = document.createElement('h4');
        h4.type = 'h4';
        h4.textContent = "Имя пользователя: " + result.usernameSender;
        h4.className = 'card-title';
        var p = document.createElement('p');
        p.type = 'p';
        p.textContent = messageDate.getMonth() + "м " + messageDate.getDay() + "д "
            + messageDate.getHours() + ":" + messageDate.getMinutes()
            + ":" + messageDate.getSeconds() + " - " + result.message;
        h4.appendChild(p);
        div.appendChild(h4);
        $(div).appendTo('.list');
    }

    function create(result) {
        let length = result.length;
        for (var i = 0; i < length; i++) {
            createDiv(result[i]);
            console.log("result[i] - ");
            console.log(result[i]);
            console.log("i - " + i);

        }
    }

    function buttonPressed(id) {
        console.log("tyt - " + id);
    }

</script>
</body>

</html>
