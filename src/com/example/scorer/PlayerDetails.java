package com.example.scorer;

import java.util.LinkedList;

public class PlayerDetails {
		private String name = "name";
		private int id = 0;
		private int score = 0;
		private LinkedList<Integer> scoreLog;
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public LinkedList<Integer> getScoreLog() {
			return scoreLog;
		}

		public void setScoreLog(LinkedList<Integer> scoreLog) {
			this.scoreLog = scoreLog;
		}

		
		public PlayerDetails(String name) {
			this.name = name;
			scoreLog = new LinkedList<Integer>();
		}

}
