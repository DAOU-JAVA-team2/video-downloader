package network;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	
	Map<String, String> response = new HashMap<>();
	
	public void put(String key, String value) {
		this.response.put(key, value);
	}
	public String get(String key) {
		return this.response.get(key);
	}
	@Override
	public String toString() {
		return "\n[\n" + response + "\n]";
	}
	
}
