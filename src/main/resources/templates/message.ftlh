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
        let webSocketChatList;
        let webSocketMessage;

        function connect() {
            webSocketMessage = new WebSocket('ws://c6311307eb46.ngrok.io/wschat/user/${user.getId()}/chat/${chat.getId()}');
            webSocketChatList = new WebSocket('ws://c6311307eb46.ngrok.io/wschat/${uuid}');

            webSocketChatList.onmessage = function receiveMessage(response) {
                let json = JSON.parse(response['data']);
                console.log(json);
            }

            webSocketMessage.onmessage = function receiveMessage(response) {
                let json = JSON.parse(response['data']);
                console.log(json);
                $('#messagesList').first().after("<li>" + json['userName'] + ' ' + json['text'] + "</li>")
            }
        }

        function sendMessage(text) {
            let messageDto = {
                "chatId": ${chat.getId()},
                "userId": ${user.getId()},
                "userName": "${user.getFullName()}",
                "text": text
            };
            let chatDto = {
                "id": ${chat.getId()},
                "title": "${chat.getTitle()}",
                "lastMessage": messageDto,
                "chatType": "${chat.getChatType()}"
            };

            webSocketChatList.send(JSON.stringify(chatDto));
            webSocketMessage.send(JSON.stringify(messageDto));
        }
    </script>
</head>
<body onload="connect()">
<div>
    <label for="message">Текст сообщения</label>
    <input name="message" id="message" placeholder="Сообщение">
    <button onclick="sendMessage($('#message').val())">Отправить</button>
    <h3>Сообщения</h3>
    <ul id="messagesList">

    </ul>
</div>
</body>
</html>
