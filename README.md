# MultiUser-Chat-App-Java  
## 💬 Java Multithreaded Chat Application

This is a simple multithreaded chat application built using Java Sockets. It allows multiple users to chat with each other in real time through the terminal.

---

## ✅ Features

- Handles multiple clients using multithreading  
- Real-time chat between users  
- Simple and lightweight terminal-based interface  

---

## 🧰 Requirements

- Java JDK 8 or above  
- Any terminal or command prompt  

---

## 🚀 How to Run

### Step 1: Compile the Java files

Open a terminal in the project folder and run:

```bash```

javac ChatServer.java
javac ChatClient.java


Step 2: Start the Server
In the same terminal, run:

java ChatServer
This will start the server and wait for clients to connect.

Step 3: Start the Clients
Open a new terminal for each client you want to run, then type:

java ChatClient
You can run this command in multiple terminals to start multiple clients. All connected clients can now chat with each other in real time.
