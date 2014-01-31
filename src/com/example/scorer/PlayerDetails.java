package com.example.scorer;

import java.util.HashMap;
import java.util.Map;

public class PlayerDetails extends HashMap<String, String>{

	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		static final String KEY_NAME = "name";
	    static final String KEY_ID = "id";
	    static final String KEY_SCORE = "score";
	    static final String KEY_SCORE_LOG = "score_log";
		public PlayerDetails() {
			super();
			// TODO Auto-generated constructor stub
		}
		public PlayerDetails(int capacity, float loadFactor) {
			super(capacity, loadFactor);
			// TODO Auto-generated constructor stub
		}
		public PlayerDetails(int capacity) {
			super(capacity);
			// TODO Auto-generated constructor stub
		}
		public PlayerDetails(Map<? extends String, ? extends String> map) {
			super(map);
			// TODO Auto-generated constructor stub
		}
		
		public PlayerDetails(String name) {
			
			this.put(KEY_NAME, name);
			this.put(KEY_ID, "-1");
			this.put(KEY_SCORE, "0");
			this.put(KEY_SCORE_LOG, "0");
		}

}
