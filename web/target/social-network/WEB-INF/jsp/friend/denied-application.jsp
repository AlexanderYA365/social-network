<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 26.03.2023
  Time: 19:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Отказ в заявках</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<%@ include file="../navbar.jsp" %>
<br>
<br>
<select id="selected-quantity" onchange='changeOption(this.value)'>
    <option value="10">10</option>
    <option value="50">50</option>
    <option value="100">100</option>
</select>
<table id="table-denied" class="table table-striped">
</table>

<div class="d-flex gap-2 justify-content-center py-5">
    <button class="btn btn-secondary" id="previous" hidden="true" onclick='previous(this)'>предыдущие</button>
    <button class="btn btn-secondary" id="next" onclick='next(this)' class='button'>следующие</button>
</div>


<body>
<script>
    var response;
    var count = 0;
    var url = window.location;
    var string = url.toString();
    var newUrl = string.substring(0, string.lastIndexOf("/"));
    var quantity = document.getElementById("selected-quantity").value;

    function getData() {
        console.log("selected-quantity = " + quantity)
        const requestURL = newUrl + `/get-application-rejected/0/` + quantity;
        const xhr = new XMLHttpRequest();
        xhr.open('GET', requestURL);
        xhr.onload = function () {
            if (xhr.status !== 200) {
                return;
            }
            response = JSON.parse(xhr.response);
            viewTable(response);
            console.log("response.data.length - ", Math.ceil(response.recordsTotal / quantity));
            for (let i = 0; i < Math.ceil(response.recordsTotal / quantity); i++) {
                if (Math.ceil(response.recordsTotal / quantity) <= 1) {
                    addButton(i);
                } else {
                    if (i < 3 ||
                        (Math.ceil(response.recordsTotal / quantity) - 2) <= i) {
                        addButton(i);
                    } else if (Math.ceil(Math.ceil(response.recordsTotal / quantity) / 2) == i) {
                        addCenterButton(i);
                    }
                }
            }
        }
        xhr.send();
    }

    function addCenterButton(i) {
        createButton(i);
        let btn = document.createElement('input');
        btn.type = 'button';
        btn.id = 'button-' + i;
        btn.className = 'btn btn-primary rounded-pill px-3';
        btn.value = i + 1;
        btn.addEventListener("click", function () {
            count = i;
            hiddenButton();
            removeTable();
            update();
        });
        let next = document.getElementById('next')
        $(".button").before(btn, next);
        createButton(i);
    }

    function createButton(i) {
        let btn = document.createElement('input');
        btn.type = 'button';
        btn.id = 'button-' + i;
        btn.className = 'btn btn-primary rounded-pill px-3';
        btn.value = "...";
        btn.addEventListener("click", function () {
            count = i;
            hiddenButton();
            removeTable();
            update();
        });
        let next = document.getElementById('next')
        $(".button").before(btn, next);
    }

    function update() {
        quantity = document.getElementById("selected-quantity").value;
        const requestURL = newUrl + `/get-application-rejected/` + count + '/' + quantity;
        const xhr = new XMLHttpRequest();
        xhr.open('GET', requestURL);
        xhr.onload = function () {
            if (xhr.status !== 200) {
                return;
            }
            response = JSON.parse(xhr.response);
            viewTable(response)
        }
        xhr.send();
    }

    function viewTable(response) {
        $('#table-denied').append($('<tr>')
            .append($('<th>').append("id"))
            .append($('<th>').append("Имя"))
            .append($('<th>').append("Фамилия"))
            .append($('<th>').append("Отчество"))
            .append($('<th>').append("Добавить в друзья"))
            .append($('<th>').append("Удалить заявку"))
            .append($('</tr>'))
        )
        quantity = document.getElementById("selected-quantity").value;
        for (let i = 0, length = response.data.length; i < length; i++) {
            $('#table-denied').append($('<tr>')
                .append($('<td>').append(response.data[i].id))
                .append($('<td>').append(response.data[i].accountDetails.name))
                .append($('<td>').append(response.data[i].accountDetails.surname))
                .append($('<td>').append(response.data[i].accountDetails.lastName))
                .append($('<td>').append("<button class='btn-add' onclick='addApplicant(this)'>Добавить</button>"))
                .append($('<td>').append("<button class='btn-del' onclick='deleteApplicant(this)'>Удалить</button>"))
                .append($('</tr>'))
            )
        }
    }

    function addButton(i) {
        let btn = document.createElement('input');
        btn.type = 'button';
        btn.id = 'button-' + i;
        btn.className = 'btn btn-primary rounded-pill px-3';
        btn.value = i + 1;
        btn.addEventListener("click", function () {
            count = i;
            hiddenButton();
            removeTable();
            update();
        });
        let next = document.getElementById('next');
        $(".button").before(btn, next);
    }

    function addApplicant(element) {
        var rowjQuery = $(element).closest("tr");
        var rowIndexjQuery = rowjQuery[0].rowIndex - 1;
        $.ajax({
            type: "POST",
            url: newUrl + '/add-application/' + response[rowIndexjQuery].id
        });
    }

    function deleteApplicant(element) {
        var rowjQuery = $(element).closest("tr");
        var rowIndexjQuery = rowjQuery[0].rowIndex - 1;
        $.ajax({
            type: "POST",
            url: newUrl + '/delete-application/' + response[rowIndexjQuery].id
        });
    }

    function removeTable() {
        $('#table-denied').empty();
    }

    function next() {
        count++;
        console.log("count : ", count);
        var prev = document.getElementById("previous");
        prev.hidden = false;
        removeTable();
        update();
    }

    function hiddenButton() {
        var prev = document.getElementById("previous");
        if (count == 0) {
            prev.hidden = true;
        } else {
            prev.hidden = false;
        }
    }

    function previous() {
        count--;
        hiddenButton();
        console.log("count : ", count);
        removeTable();
        update();
    }

    function changeOption() {
        removeTable();
        update();
    }

    document.onload = getData();

</script>
<%@ include file="../footers.jsp" %>
</body>
</html>