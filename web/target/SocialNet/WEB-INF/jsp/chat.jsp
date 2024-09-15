<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 26.11.2022
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Чат</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/chat.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet">
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container">
    <div class="card">
        <div class="card-header">
            <div class="col-12 col-lg-7 col-xl-9">
                <div class="py-2 px-4 border-bottom d-none d-lg-block">
                    <div class="d-flex align-items-center py-1">
                        <div class="position-relative">
                            <img src="data:image/jpg;base64, ${friendAvatar}"
                                 class="rounded-circle mr-1" width="40" height="40"
                                 alt="Responsive image" onerror="this.src='resources/img/noPhotoAvailable.jpg'">
                        </div>
                        <div class="flex-grow-1 pl-3">
                            <strong>${friend.accountDetails.name} ${friend.accountDetails.lastName} ${friend.accountDetails.surname}</strong>
                            <div class="text-muted small"><em>${friend.username}</em></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-body">
            <div class="position-relative">
                <div class="chat-messages p-4">
                    <div id="messages">
                    </div>
                </div>
            </div>
            <div class="flex-grow-0 py-3 px-4 border-top">
                <div class="input-group">
                    <label class="btn btn-primary" for="my-file-selector">
                        <input id="my-file-selector" type="file" accept="image/*" class="d-none" name="file"
                               onchange="chatUnit.sendFile(this)">
                        Выбрать файл
                    </label>
                    <input id="text-message" type="text" class="form-control" placeholder="Введите Ваше сообщение">
                    <button id="send-message" class="btn btn-primary" onclick="chatUnit.send()">Оправить</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var messages = [];
    var accounts = [];
    var account = {};
    var senderAccount = {};
    var friendAccount = {};

    let chatUnit = {
        init() {
            this.openSocket();
        },
        onOpenSocket() {
            this.messageTextArea = addEventListener("keyup", e => {
                if (e.ctrlKey && e.keyCode === 13) {
                    e.preventDefault();
                    this.send();
                }
            });
        },
        sendFile(inputImage) {
            let file = inputImage.files[0];
            let reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => this.convertImage(reader.result);
        },
        convertImage(imageFromFile) {
            let date = new Date();
            let sending = {
                senderId: senderId,
                message: '',
                picture: imageFromFile,
                publicationDate: date,
                edited: 'false',
                usernameSender: username,
                usernameReceiving: usernameReceiving
            };
            let accountMessage = {
                receiverId: receiverId,
                messageType: 'PRIVATE',
                message: sending
            };
            console.log(accountMessage);
            this.sendMessage(accountMessage);
        },
        send() {
            let sendingText = document.getElementById("text-message").value;
            let date = new Date();
            let sending = {
                senderId: senderId,
                message: sendingText,
                picture: '',
                publicationDate: date,
                edited: 'false',
                usernameSender: username,
                usernameReceiving: usernameReceiving
            };
            let accountMessage = {
                receiverId: receiverId,
                messageType: 'PRIVATE',
                message: sending
            };
            this.sendMessage(accountMessage);
        },
        onMessage(message) {
            this.getAccountInfo();
            this.fillMessages(senderAccount, message);
        },
        getAccountInfo() {
            senderAccount.photo = "${accountAvatar}";
            senderAccount.id = "${account.id}";
            senderAccount.date = "${account.accountDetails.date}";
            senderAccount.addressHome = "${account.accountDetails.addressHome}";
            senderAccount.username = "${account.username}";
            senderAccount.name = "${account.accountDetails.name}";
            senderAccount.surname = "${account.accountDetails.surname}";
            senderAccount.lastName = "${account.accountDetails.lastName}";
        },
        getFriendInfo() {
            friendAccount.photo = "${friendAvatar}";
            friendAccount.id = "${friend.id}";
            friendAccount.date = "${friend.accountDetails.date}";
            friendAccount.addressHome = "${friend.accountDetails.addressHome}";
            friendAccount.username = "${friend.username}";
            friendAccount.name = "${friend.accountDetails.name}";
            friendAccount.surname = "${friend.accountDetails.surname}";
            friendAccount.lastName = "${friend.accountDetails.lastName}";
        },
        getMessage() {
            <c:forEach items="${messages}" var="message">
            var message = {};
            var senderAccount = {};
            message.receiverId = "${message.receiverId}";
            message.messageType = "${message.messageType}";
            message.message = "${message.message}";
            message.picture = "${message.picture}";
            message.edited = "${message.edited}";
            message.publicationDate = "${message.publicationDate}";
            message.senderId = "${message.senderId}";
            message.id = "${message.id}";
            if ("${message.senderId}" === account.id) {
                senderAccount.photo = account.photo;
                senderAccount.id = account.id;
                senderAccount.date = account.date;
                senderAccount.addressHome = account.addressHome;
                senderAccount.username = account.username;
                senderAccount.name = account.name;
                senderAccount.surname = account.surname;
                senderAccount.lastName = account.lastName;
            } else if ("${message.senderId}" === friendAccount.id) {
                senderAccount.photo = friendAccount.photo;
                senderAccount.id = friendAccount.id;
                senderAccount.date = friendAccount.date;
                senderAccount.addressHome = friendAccount.addressHome;
                senderAccount.username = friendAccount.username;
                senderAccount.name = friendAccount.name;
                senderAccount.surname = friendAccount.surname;
                senderAccount.lastName = friendAccount.lastName;
            }
            accounts.push(senderAccount);
            messages.push(message);
            </c:forEach>
        },
        fillMessages(accountForMessage, message) {
            let wall = document.createElement('div');
            if (friendAccount.id !== accountForMessage.id) {
                wall.className = "chat-message-right pb-4";
            } else {
                wall.className = "chat-message-left pb-4";
            }
            let div = document.createElement('div');
            let time = document.createElement('div');
            time.innerHTML += message.publicationDate;
            time.className = "text-muted small text-nowrap mt-2";
            let photo = document.createElement('img');
            if (accountForMessage.photo !== "") {
                photo.src = "data:image/jpg;base64," + accountForMessage.photo;
            } else {
                photo.src = "resources/img/noPhotoAvailable.jpg";
            }
            photo.width = "40";
            photo.height = "40";
            photo.className = "rounded-circle mr-1";
            div.appendChild(photo);
            div.appendChild(time);
            let visibleMessage = document.createElement('div');
            visibleMessage.className = "flex-shrink-1 bg-light rounded py-2 px-3 ml-3";
            let nameSender = document.createElement('div');
            nameSender.className = "fw-bolder mb-3";
            nameSender.innerHTML += accountForMessage.name + " " + accountForMessage.lastName +
                " " + accountForMessage.surname;
            let text;
            if (message.message !== "") {
                text = document.createElement("div");
                text.innerHTML += message.message;
            } else {
                text = document.createElement('img');
                text.width = "400";
                text.height = "400";
                text.src = message.picture;
            }
            visibleMessage.appendChild(nameSender);
            visibleMessage.appendChild(text);
            visibleMessage.appendChild(time);
            wall.appendChild(div);
            wall.appendChild(visibleMessage);
            $("#messages").before(wall);
            document.querySelector("#messages").scrollIntoView(false);

        },
        onClose() {
        },
        sendMessage(message) {
            this.onMessage(message);
            document.getElementById("text-message").value = "";
            document.querySelector("#messages").scrollIntoView(false);
            this.ws.send(JSON.stringify(message));
        },
        openSocket() {
            username = "${account.username}";
            usernameReceiving = "${friend.username}";
            usernameSender = "${account.username}";
            senderId = "${account.id}";
            var url = window.location;
            var string = url.toString();
            let newUrl = string.substring(5, string.lastIndexOf("/"));
            receiverId = "${friend.id}";
            this.ws = new WebSocket("ws:" + newUrl + "/goChat/" + username);
            this.ws.onopen = () => this.onOpenSocket();
            this.ws.onmessage = (e) => this.onMessage(JSON.parse(e.data));
            this.ws.onclose = (e) => this.onClose();
        },
        fillAccount() {
            account.photo = "${accountAvatar}";
            account.id = "${account.id}";
            account.date = "${account.accountDetails.date}";
            account.addressHome = "${account.accountDetails.addressHome}";
            account.username = "${account.username}";
            account.name = "${account.accountDetails.name}";
            account.surname = "${account.accountDetails.surname}";
            account.lastName = "${account.accountDetails.lastName}";
        },
        onLoad() {
            this.fillAccount();
            this.getFriendInfo();
            this.getMessage();
            for (let i = 0; i <= accounts.length - 1; i++) {
                this.fillMessages(accounts[i], messages[i]);
            }
        }
    };
    window.addEventListener("load", e => chatUnit.init());
    window.addEventListener("load", e => chatUnit.onLoad());
</script>
</html>