package populate;

import akka.actor.UntypedActor;
import java.util.ArrayList;

public class Dart extends UntypedActor {
  
    // Location of darts thrown.
    private ArrayList<Point> darts;

    /**
     * Throws a bunch of "darts" at a square.
     * @param size  The number of darts thrown.
     */
    private void throwDarts(int size) {
        for (int i = 0; i < size; i++) {
            darts.add(Point.genRandPoint());
        }
    }
    
    /**
     * Use Monte Carlo integration to approximate pi.
     * (see: https://www.wikiwand.com/en/Monte_Carlo_integration)
     */
    private float approximatePi() {
        int total = 0; // Keep track of total points thrown.
        int inside = 0; // Keep track of points inside the circle.
        for (Point d : darts) {
            if (d.x * d.x + d.y * d.y <= 1) {
                inside += 1;
            }
            total += 1;
        }
        return 4 * ((float) inside) / total;
    }

    @Override
    public void onReceive(Object msg) {
        if (msg != null) {
            this.throwDarts(50);
            getSender().tell(approximatePi(), getSelf());
        } else {
            getContext().stop(getSelf());
            unhandled(msg);
        }
    }

    @Override
    public void preStart() {
        darts = new ArrayList<Point>();
    }
}
