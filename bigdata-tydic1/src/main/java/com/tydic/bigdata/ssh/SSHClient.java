package com.tydic.bigdata.ssh;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSHClient {
	private String hostIp;
	private String hostName;
	private String hostPwd;
	private Connection conn;
	
	private boolean isLogin;
	private String rootPath;
	private String workpath;
	
	public String ftp(String localFile,String remotePath){
		SCPClient scpClient;
		try {
			scpClient = this.getConn().createSCPClient();
	        scpClient.put(localFile,remotePath);
		} catch (IOException e) {
			e.printStackTrace();
		}   
		return "";
	}
	
	public String execCmdWithBack(String cmdStr){
		if(this.isLogin==false){
			this.login();
		}
		try{
			Session ss = this.getSession();
			InputStream stdout = new StreamGobbler(ss.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout,"UTF-8"));
			ss.execCommand(cmdStr+" \n");
			String result = OutFilter.read(br);
			stdout.close();
			br.close();
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean login(){
		this.setConn(null);
		try{
			this.setConn(new Connection(this.getHostIp()));
			this.getConn().connect();
			//this.conn = new Connection(this.getHostIp());
			//this.conn.connect();
			boolean bo = this.getConn().authenticateWithPassword(this.getHostName(), this.getHostPwd());
			if(bo==false){
				throw new Exception("user or password is wrong");
			}else{
				this.setLogin(bo);
				return bo;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	//通过此方法获取实例
	public static SSHClient getInstance(String hostIp,String hostName,String hostPwd){
		//System.out.println(hostIp+" "+hostName+" "+hostPwd);
		SSHClient clientTemplate = new SSHClient(hostIp,hostName,hostPwd);
		SSHClient client = SSHClientPool.get(clientTemplate);
		if(client==null){
			client = clientTemplate;
			boolean bo = client.login();
			if(bo==false){
				return null;
			}else{
				SSHClientPool.put(client);
			}
		}
		return client;
	}
	
	private SSHClient(String hostIp,String hostName,String hostPwd){
		this.setHostIp(hostIp);
		this.setHostName(hostName);
		this.setHostPwd(hostPwd);
		//this.setWorkpath(F.getProperty("sys.workpath"));
	}

	private Session getSession() throws IOException{
		Session ss = null;
		try {
			ss = this.getConn().openSession();
		} catch (IOException e) {
			boolean isLogin = this.login();
			if(isLogin==true){
				try {
					ss = this.getConn().openSession();
				} catch (IOException e1) {
				}
			}else{
				throw new IOException(this.getHostIp()+" "+this.getHostName()+" "+this.getHostPwd()+" can't conect");
			}
		}

		return ss;
	}
	
	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getHostPwd() {
		return hostPwd;
	}

	public void setHostPwd(String hostPwd) {
		this.hostPwd = hostPwd;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getWorkpath() {
		return workpath;
	}

	public void setWorkpath(String workpath) {
		this.workpath = workpath;
	}

	@Override
	public String toString() {
		return this.getHostIp()+File.separator+this.getHostName();
	}
}
