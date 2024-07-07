package com.example.game1.Logic;

public class Score implements Comparable<Score> {
    private int distance;
    private int coins;
    private double lat;
    private double lon;

    public Score(int distance, int coins, double lat, double lon) {
        this.distance = distance;
        this.coins = coins;
        this.lat = lat;
        this.lon = lon;
    }

    public int getdistance() {
        return distance;
    }

    public int getCoins() {
        return coins;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public int compareTo(Score other) {
        int distanceCompersion = Integer.compare(other.getdistance(),this.distance);
        if(distanceCompersion==0){
            // Distances are equal, compare coins
            return Integer.compare(other.getCoins(),this.coins);
        }
        return Integer.compare(other.getdistance(),this.distance);

    }
}
