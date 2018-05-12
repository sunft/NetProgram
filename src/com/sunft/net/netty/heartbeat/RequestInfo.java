package com.sunft.net.netty.heartbeat;

import java.io.Serializable;
import java.util.HashMap;

public class RequestInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ip;
	private HashMap<String, Object> cpuPerMap;
	private HashMap<String, Object> memoryMap;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public HashMap<String, Object> getCpuPerMap() {
		return cpuPerMap;
	}

	public void setCpuPerMap(HashMap<String, Object> cpuPerMap) {
		this.cpuPerMap = cpuPerMap;
	}

	public HashMap<String, Object> getMemoryMap() {
		return memoryMap;
	}

	public void setMemoryMap(HashMap<String, Object> memoryMap) {
		this.memoryMap = memoryMap;
	}

}
