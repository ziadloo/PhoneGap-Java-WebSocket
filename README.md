PhoneGap-Java-WebSocket
=======================

Few small missing classes to make the Java-WebSocket (client) project accessible through Javascript in PhoneGap+Android.

This project is a variation of PhoneGap-Android-HTML5-WebSocket (https://github.com/FreakDev/PhoneGap-Android-HTML5-WebSocket).
Here I have used the awesome Java-WebSocket project as the base to create a WebSocket client. It supports all the diffrent drafts
(to this moment) of WebSocket protocols, up to "Hybi 17" and "RFC 6455". I've tested this project with Cordova 2.2.0 and Android 4.1.2.

How to use:

	0. I'm assuming you already have a PhoneGap project created with "src" and "assets" folders ready.

	1. Copy "src" folder of this project to your PhoneGap "src" folder.

	2. Copy "websocket.js" to your "www" folder (or some subfolder of it).

	3. Download and copy the Java-WebSocket project: https://github.com/TooTallNate/Java-WebSocket to your PhoneGap "src" folder.

	4. Add the following line to your App's onCreate method (a sample App is provided in the "src" folder, named "Test.java"):

		appView.addJavascriptInterface(new WebSocketFactory(appView), "WebSocketFactory");

	5. In your html file include the "websocket.js" file and instantiate an object like this:

		var socket = new WebSocket("ws://localhost:8080");

		socket.onopen = function() { socket.send("Hello World!"); socket.close(); }

		socket.onclose = function() { alert("WebSocket is closed"); }

		socket.onmessage = function(msg) { alert(msg.data); }

Things to be done:

	1. "send" method does not accept files (binary data) as the WebSocket does in the Chrome.

	2. Event responses of onmessage, onerror and onclose do not get an accurate input.

