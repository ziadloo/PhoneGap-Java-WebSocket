package com.Method.WebSocket;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import android.webkit.WebView;

public class WebSocketFactory
{
	private WebView mView;
	private Map<String, AndroidWebSocket> collection;
	
	public WebSocketFactory(WebView view)
	{
		mView = view;
		collection = new HashMap<String, AndroidWebSocket>();
	}
	
	public AndroidWebSocket getNew(String url) throws URISyntaxException
	{
		return getNew(url, (String)null);
	}
	
	public AndroidWebSocket getNew(String url, String[] protocols) throws URISyntaxException
	{
		String p = new String();
		if (protocols != null && protocols.length > 0) {
			StringBuilder sb = new StringBuilder(protocols[0]);
			for (int i=1; i<protocols.length; i++) {
				sb.append(", ");
				sb.append(protocols[i]);
			}
			p = sb.toString();
		}
		return getNew(url, p);
	}
	
	public AndroidWebSocket getNew(String url, String protocols) throws URISyntaxException
	{
		Map<String, String> headers = null;
		if (protocols != null) {
			headers = new HashMap<String, String>();
			headers.put("Sec-WebSocket-Protocol", protocols);
		}
		AndroidWebSocket ws = new AndroidWebSocket(mView, url, headers);
		if (!collection.containsKey(ws.getIdentifier())) {
			collection.put(ws.getIdentifier(), ws);
		}
		return ws;
	}
	
	public void removeSocket(String key)
	{
		if (!collection.containsKey(key)) {
			AndroidWebSocket ws = collection.get(key);
			collection.remove(key);
			ws.close();
		}
	}
}
