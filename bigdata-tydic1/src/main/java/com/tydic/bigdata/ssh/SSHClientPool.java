package com.tydic.bigdata.ssh;

import java.util.HashMap;
import java.util.Map;

public class SSHClientPool {
	private static Map<String,SSHClient> clientPool = new HashMap<String,SSHClient>();
	
	public static void put(SSHClient client){
		clientPool.put(client.toString(), client);
	}
	
	public static SSHClient get(SSHClient clientTemplate){
		SSHClient client = clientPool.get(clientTemplate.toString());
		return client;
	}
	
	private SSHClientPool(){
		
	}
}
