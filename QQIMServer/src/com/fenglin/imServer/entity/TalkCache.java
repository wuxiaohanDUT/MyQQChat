package com.fenglin.imServer.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fenglin.commons.entity.Message;

public class TalkCache {
	
	private Map<String,List<Message>> talkCache = new HashMap<String,List<Message>>();

	public Map<String, List<Message>> getTalkCache() {
		return talkCache;
	}

	public void setTalkCache(Map<String, List<Message>> talkCache) {
		this.talkCache = talkCache;
	}
	
	public void  put(String key, List<Message> value) {
		talkCache.put(key, value);
	}
	
	public void  remove(String key) {
		talkCache.remove(key);
	}
	
}
