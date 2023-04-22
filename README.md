<div align="center">

# 💬 JChat Multicast

![Java](https://img.shields.io/badge/19-ED8B00?logo=java&logoColor=white&label=Java)
[![javax.swing](https://img.shields.io/badge/Swing-included-yellow)](https://docs.oracle.com/en/java/javase/19/docs/api/java.desktop/javax/swing/package-summary.html)
![maven](https://img.shields.io/badge/4.0-C71A36?logo=Apache%20Maven&logoColor=white&label=Maven)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

</div>
📣 JChat-Multicast is a Java application that allows users to communicate using the multicast protocol without the need for a central server 🚫🖥️. Conversations are broadcast to all participants in the network 🌐, allowing for easy and fast message transmission ⚡📨.

## Features 🚀

- Multicast based messaging
- No central server required
- Simple graphical user interface using Java Swing
- User list with status indication
- User nickname validation
- Automatic removal of inactive users after a specified time

## Architecture 🏗️
JChat-MultiCast is designed with a decentralized architecture, which eliminates the need for a central server to manage users and conversations. Each client acts as a node on the network, managing its own connections and transmitting messages to other clients via multicast technology. This architecture is a unique and interesting solution that offers a more efficient and reliable way to chat on a network.

## Benefits 🌟
- **Improved fault tolerance:** Because there is no central server, the system is less vulnerable to failures or attacks on a single point of failure.
- **Reduced latency:** Messages are transmitted directly between clients via multicast technology, resulting in faster communication.
- **Greater scalability:** Because each client manages its own connections, the system can scale more easily without requiring additional resources on a central server.
- **Lower costs:** Since there is no need for a dedicated server to manage the network, the costs associated with maintenance and hardware are significantly reduced.
- **Easy to use:** JChat has an intuitive user interface that makes it easy for users to start chatting right away.

## Drawbacks 🤔
- **Potential for network congestion:** Since each client is transmitting messages to all other clients via multicast technology, there is the potential for network congestion, especially in larger chat rooms.
- **Security concerns:** Decentralized networks can be more difficult to secure and may be vulnerable to attacks or breaches of privacy.
- **Dependency on client nodes:** Since the system relies on each client to manage its own connections, the reliability of the network depends on the stability of each client node.

## Technologies 🛠️
- Java
- AWT & Swing
- Multicasting

## Usage 💻

1. Download the JChat-Multicast project from GitHub
2. Open the project in an IDE or compile using the command line
3. Run the example main function to start the application
4. Enter a unique nickname in the login screen and click the "Join" button
5. Start chatting with other users!

## How it Works 🤖
JChat-Multicast uses the multicast protocol to send messages between users. When a user sends a message, it is broadcast to all users in the multicast group. Each user receives the message and displays it in their chat window.

New users can join the chat by sending a "LOGIN_REQUEST" message to the multicast group. Other users in the group will respond with an "LOGIN_SUCCESS" message if the user can join, or a "NICKNAME_TAKEN" or "MAX_USERS_REACHED" message if the user cannot join.

When a user leaves the chat, they send a "LOGOUT" message to the multicast group. Other users will receive the message and remove the user from their user list.

## Screenshot

<img src="/JChat.jpg" alt="JChat-screenshot"/>
