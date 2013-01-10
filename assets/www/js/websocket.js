(function () {
	if (window.WebSocket) {
		return;
	}
	
	var WebSocket = window.WebSocket = function(url, protocols) {
		// listener to overload
		this.onopen = null;
		this.onmessage = null;
		this.onerror = null;
		this.onclose = null;
		
		this._handler = WebSocketFactory.getNew(url, protocols);
		WebSocket.collection[this._handler.getIdentifier()] = this;
		
		return this;
	};
	
	WebSocket.collection = {};
	
	WebSocket.triggerEvent = function(evt) {
		if (!WebSocket.collection[evt.socket_id]) {
			WebSocketFactory.removeSocket(evt.socket_id);
			return;
		}
		if (WebSocket.collection[evt.socket_id]['on' + evt.type]) {
			WebSocket.collection[evt.socket_id]['on' + evt.type].apply(window, [evt]);
		}
	}
	
	WebSocket.prototype.send = function(data) 
	{
		data = "" + data;
		this._handler._send(data);
	}
	
	WebSocket.prototype.close = function() 
	{
		this._handler.close();
	}
})();