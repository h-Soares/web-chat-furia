const url = "ws://localhost:8080/websocket";
const public_topic = "/topic/public";
const private_topic = "/user/queue/private";
const app_public = "/chat/public";
const app_private = "/chat/private";

let username = "";
const client = new StompJs.Client({
    brokerURL: url,
    connectHeaders: {}
});

client.onConnect = (frame) => {
    console.log("Connected:", frame);

    document.getElementById("login-section").classList.add("hidden");
    document.getElementById("chat-section").classList.remove("hidden");

    const userLabel = document.getElementById("logged-user");
    userLabel.textContent = `Você está logado como: ${username}`;
    userLabel.classList.remove("hidden");

    client.subscribe(public_topic, (message) => {
        const msg = JSON.parse(message.body);
        showGreeting(`${msg.from}: ${msg.content}`, false);
    });

    client.subscribe(private_topic, (message) => {
        const msg = JSON.parse(message.body);
        showGreeting(`${msg.from}: ${msg.content}`, true);
    });
};

client.onWebSocketError = (error) => {
    console.error("WebSocket error:", error);
    alert("Erro na conexão com o WebSocket.");
};

client.onStompError = (frame) => {
    console.error("STOMP error:", frame.headers["message"]);
    console.error("Details:", frame.body);
    alert("Erro no STOMP: " + frame.headers["message"]);
};

function connect() {
    const inputName = document.getElementById("name").value.trim();
    if (!inputName) {
        alert("Por favor, digite seu nome.");
        return;
    }

    username = inputName;

    client.brokerURL = `ws://localhost:8080/websocket?username=${encodeURIComponent(username)}`;
    client.connectHeaders = { username: username };
    client.activate();
}

function disconnect() {
    client.deactivate();
    document.getElementById("chat-section").classList.add("hidden");
    document.getElementById("login-section").classList.remove("hidden");
    document.getElementById("greetings").innerHTML = "";
    document.getElementById("name").value = "";
    username = "";

    document.getElementById("logged-user").classList.add("hidden");
}

function sendMessage() {
    const messageInput = document.getElementById("message");
    const rawMessage = messageInput.value.trim();
    if (!rawMessage) return;

    if (rawMessage.startsWith("/private:")) {
        const separatorIndex = rawMessage.indexOf(" ");
        if (separatorIndex === -1) {
            alert("Formato inválido. Use: /private:Username Mensagem");
            return;
        }

        const recipientTag = rawMessage.substring(9, separatorIndex).trim();
        const privateMessage = rawMessage.substring(separatorIndex + 1).trim();

        if (!recipientTag || !privateMessage) {
            alert("Formato inválido. Use: /private:Username Mensagem");
            return;
        }

        if (recipientTag === username) {
            alert("Você não pode enviar uma mensagem privada para si mesmo.");
            return;
        }

        client.publish({
            destination: app_private,
            body: JSON.stringify({
                to: recipientTag,
                from: username,
                content: privateMessage
            })
        });

    } else {
        client.publish({
            destination: app_public,
            body: JSON.stringify({
                from: username,
                content: rawMessage
            })
        });
    }

    messageInput.value = "";
}

function showGreeting(text, isPrivate = false) {
    const greetings = document.getElementById("greetings");
    const msgDiv = document.createElement("div");

    const isFromMe = text.startsWith(`${username}:`)
    msgDiv.classList.add("message");
    msgDiv.classList.add(isPrivate ? "private" : "public");
    if (isFromMe) {
        msgDiv.classList.add("self");
    }

    const separatorIndex = text.indexOf(":");
    if (separatorIndex !== -1) {
        const sender = text.substring(0, separatorIndex);
        const content = text.substring(separatorIndex + 1).trim();
        msgDiv.innerHTML = `<strong>${sender}:</strong> ${content}`;
    } else {
        msgDiv.textContent = text;
    }

    greetings.appendChild(msgDiv);
    greetings.scrollTop = greetings.scrollHeight;
}

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("connect").addEventListener("click", connect);
    document.getElementById("disconnect").addEventListener("click", disconnect);
    document.getElementById("send").addEventListener("click", sendMessage);

    document.getElementById("message").addEventListener("keydown", (e) => {
        if (e.key === "Enter") {
            sendMessage();
        }
    });

    document.getElementById("name").addEventListener("keydown", (e) => {
        if (e.key === "Enter") {
            connect();
        }
    });
});