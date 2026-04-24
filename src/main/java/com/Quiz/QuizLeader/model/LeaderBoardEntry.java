package com.Quiz.QuizLeader.model;

public class LeaderBoardEntry implements Comparable<LeaderBoardEntry>{
    private String participant;
    private int totalScore;

    public LeaderBoardEntry() {}

    public LeaderBoardEntry(String participant, int totalScore) {
        this.participant = participant;
        this.totalScore  = totalScore;
    }

    @Override
    public int compareTo(LeaderBoardEntry other) {
        return Integer.compare(other.totalScore, this.totalScore);
    }

    public String getParticipant() { return participant; }
    public int    getTotalScore()  { return totalScore; }

    public void setParticipant(String participant) { this.participant = participant; }
    public void setTotalScore(int totalScore)      { this.totalScore = totalScore; }
}
