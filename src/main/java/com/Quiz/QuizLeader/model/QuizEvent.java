package com.Quiz.QuizLeader.model;

public class QuizEvent {
    private String roundId;
    private String participant;
    private int score;

    public QuizEvent() {}

    public String getDedupKey() {
        return roundId + "|" + participant;
    }

    public String getRoundId()     { return roundId; }
    public String getParticipant() { return participant; }
    public int    getScore()       { return score; }

    public void setRoundId(String roundId)         { this.roundId = roundId; }
    public void setParticipant(String participant)  { this.participant = participant; }
    public void setScore(int score)                { this.score = score; }
}
