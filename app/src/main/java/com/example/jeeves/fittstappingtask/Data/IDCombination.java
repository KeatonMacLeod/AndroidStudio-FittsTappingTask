package com.example.jeeves.fittstappingtask.Data;

public class IDCombination {
    private int trialCount = 1;
    private int attempted = 0;
    private int amplitude;
    private int width;

    public IDCombination(int width, int amplitude) {
        this.width = width;
        this.amplitude = amplitude;
    }

    public boolean completedAllTrials() {
        return attempted >= trialCount;
    }

    public int getTotal() {
        return trialCount;
    }

    public void setTotal(int total) {
        this.trialCount = total;
    }

    public int getAttempted() {
        return attempted;
    }

    public void setAttempted(int attempted) {
        this.attempted = attempted;
    }

    public int getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(int amplitude) {
        this.amplitude = amplitude;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void incrementAttempted() {
        attempted += 1;
    }

    public void incrementTrialCount() {
        trialCount += 1;
    }
}
