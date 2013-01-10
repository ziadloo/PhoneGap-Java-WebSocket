package com.Method.WebSocket;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Map;

import android.webkit.WebView;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

 public class AndroidWebSocket extends WebSocketClient
{
	private WebView mView;
	private final AndroidWebSocket instance;
	
	public AndroidWebSocket(WebView v, String url, Map<String,String> headers) throws URISyntaxException
	{
		super(URI.create(url), new Draft_17(), headers, 0);
		mView = v;
		this.instance = this;
		instance.connect();
	}
	
	protected static class JSEvent
	{
		static String buildJSON(String type, String socket_id, String data)
		{
			return "{\"type\":\"" + type + "\",\"socket_id\":\"" + socket_id + "\",\"data\":'"+ data +"'}";
		}
		
		static String buildJSON(String type, String socket_id)
		{
			return "{\"type\":\"" + type + "\",\"socket_id\":\"" + socket_id + "\",\"data\":\"\"}";
		}		
	}
		
	public String getIdentifier()
	{
		return this.toString();
	}

	@Override
	public void onMessage(final String data)
	{
		mView.post(new Runnable() {
			public void run()
			{
				mView.loadUrl("javascript:WebSocket.triggerEvent(" + JSEvent.buildJSON("message", instance.toString(), data) + ")");
			}
		});
	}

	@Override
	public void onMessage(ByteBuffer blob)
	{
		//getConnection().send( blob );
	}

	@Override
	public void onError(Exception ex)
	{
		mView.post(new Runnable() {
			@Override
			public void run()
			{
				mView.loadUrl("javascript:WebSocket.triggerEvent(" + JSEvent.buildJSON("error", instance.toString()) + ")");
			}
		});
	}

	@Override
	public void onOpen(ServerHandshake handshake)
	{
		mView.post(new Runnable() {
			@Override
			public void run()
			{
				mView.loadUrl("javascript:WebSocket.triggerEvent(" + JSEvent.buildJSON("open", instance.toString()) + ")");
			}
		});
	}

	@Override
	public void onClose(int code, String reason, boolean remote)
	{
		mView.post(new Runnable() {
			@Override
			public void run()
			{
				mView.loadUrl("javascript:WebSocket.triggerEvent(" + JSEvent.buildJSON("close", instance.toString()) + ")");
			}
		});
	}

	@Override
	public void onWebsocketMessageFragment(WebSocket conn, Framedata frame)
	{
	}

	public void _send(final String text)
	{
		new Thread(new Runnable() {
			@Override
			public void run()
			{
				instance.send(text);
			}
		}).start();
	}
}
