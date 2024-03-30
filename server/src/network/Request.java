package network;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Request implements Serializable {
	private static final long serialVersionUID = 1L;
	
	Map<String, String> request = new HashMap<>();
	
	public void put(String key, String value) {
		this.request.put(key, value);
	}
	public String get(String key) {
		return this.request.get(key);
	}
	@Override
	public String toString() {
		return "\n[\n" + request + "\n]";
	}
}
