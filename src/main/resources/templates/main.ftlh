<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Чат</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <script>
        let webSocket;

        function connect() {
            webSocket = new WebSocket('ws://c6311307eb46.ngrok.io/wschat/${user.getId()}');

            webSocket.onmessage = function receiveMessage(response) {
                let json = JSON.parse(response['data']);
                console.log(json);
                $('#messagesList').first().after("<li>" + json['chatId'] + ' ' + json['lastMessage']['text'] + "</li>")
            }
            webSocket.onclose = function () {
                console.log("closed");
            }
        }
    </script>
</head>
<body onload="connect()">
<div>
<#--    <label for="message">Текст сообщения</label>-->
<#--    <input name="message" id="message" placeholder="Сообщение">-->
<#--    <button onclick="sendMessage($('#message').val(), 1)">Отправить</button>-->
    <h3>Сообщения</h3>
    <ul id="messagesList">

    </ul>
</div>
</body>
</html>
