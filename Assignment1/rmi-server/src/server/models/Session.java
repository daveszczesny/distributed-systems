package server.models;

import java.util.Date;
import java.util.Random;

public class Session {
    Long id;
    Long sessionStarted;

    private Random random;

    public Session() {
        this.random = new Random();
        this.id = Math.abs(random.nextLong());
        this.sessionStarted = new Date().getTime();
    }

    public long getId() {
        return id;
    }

    public long getSessionStarted(){
        return sessionStarted;
    }
}
