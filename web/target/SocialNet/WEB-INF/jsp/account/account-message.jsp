<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 08.02.2022
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Сообщения пользователя</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="../navbar.jsp" %>

<div class="container" style="margin-top: 1rem;">
    <div class="list text-center">
    </div>
</div>

<%@ include file="../footers.jsp" %>
<script>
    var currentScrollHeight = 0;
    var page = 0;
    var url = window.location;
    var string = url.toString();
    var newUrl = string.substring(0, string.lastIndexOf("/"));
    var size;
    var step = 12;
    var senderId;
    var accountId;
    var accounts = [];

    jQuery(document).ready(function ($) {
        accountId = "${account.id}";
        size = getSize();
        callData();
    });

    $(window).on("scroll", function () {
        const scrollHeight = $(document).height();
        const scrollPos = Math.floor($(window).height() + $(window).scrollTop());
        const isBottom = scrollHeight - 100 < scrollPos;
        if (isBottom && currentScrollHeight < scrollHeight && page < size) {
            callData();
            currentScrollHeight = scrollHeight;
        }
    });

    function getSize() {
        let res;
        $.ajax({
            type: "GET",
            url: newUrl + '/get-size-messages/' + accountId,
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

    function callData() {
        $.ajax({
            type: "GET",
            url: newUrl + '/get-messages/' + accountId + '/' + page,
            dataType: "json",
            success: function (result) {
                fillDiv(result);
            },
            error: function () {
                $('<div class="card my-4 py-3"><h4 class="card-title">Запись отсутствует</h4><p>' + 'Данные отсутсвуют' +
                    '</p></div>').appendTo('.list');
            }
        });
        page = page + step;
    }

    function createDiv(message, account) {
        let id = message.id;
        var div = document.createElement('div');
        let messageDate = new Date(message.publicationDate);
        div.type = 'div';
        div.className = 'card my-4 py-3';
        div.addEventListener("click", function () {
            buttonPressed(message.receiverId);
        });
        var h4 = document.createElement('h4');
        h4.type = 'h4';
        h4.textContent = "Имя пользователя: " + account.username;
        h4.className = 'text-muted mb-1';
        var p1 = document.createElement('p');
        p1.type = 'p';
        p1.textContent = 'Сообщение: ' + message.message;
        p1.className = 'fw-bold mb-1';
        var p2 = document.createElement('p');
        p2.type = 'p';
        p2.className = 'text-muted mb-0';
        p2.textContent = messageDate.toLocaleString(undefined, {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            weekday: "long",
            hour: '2-digit',
            hour12: false,
            minute: '2-digit',
            second: '2-digit'
        });
        p1.appendChild(p2);
        h4.appendChild(p1);
        div.appendChild(h4);
        $(div).appendTo('.list');
    }

    function fillDiv(message) {
        let length = message.length;
        clearArray(accounts);
        for (let i = 0; i < length; i++) {
            getAccounts(message[i].receiverId);
        }
        for (var i = 0; i < length; i++) {
            createDiv(message[i], accounts[i]);
        }
    }

    function clearArray(array) {
        while (array.length > 0) {
            array.pop();
        }
    }

    function getAccounts(receiverId) {
        $.ajax({
            type: "GET",
            async: false,
            url: newUrl + '/get-account-by-id/' + receiverId,
            contentType: "application/json",
            dataType: "json",
            success: function (account) {
                fillAccount(account);
            },
        });
    }

    function fillAccount(account) {
        accounts.push(account);
    }

    function buttonPressed(senderId) {
        var url = newUrl + "/account-message" + "?selectUser=" + senderId;
        console.log(url)
        var payload = {
            selectUser: senderId
        };
        var form = document.createElement('form');
        form.style.visibility = 'hidden';
        form.method = 'POST';
        form.action = url;
        for (key in Object.keys(payload)) {
            var input = document.createElement('submit');
            input.name = 'selectUser';
            input.value = senderId;
            form.append(input);
        }
        document.body.appendChild(form);
        form.submit();
    }
</script>
</body>
</html>
