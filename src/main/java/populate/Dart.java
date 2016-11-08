package populate;

import akka.actor.UntypedActor;
import java.util.ArrayList;

public class Dart extends UntypedActor {

    // Basically I made it so that we don't have an arraylist of Darts anymore
    // because its just not needed. We can also remove the Point class too. - Jesse Gao
  
    // Location of darts thrown.
    //private ArrayList<Point> darts; Takes up extra memory - Jesse

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
    //private float approximatePi() {
    private float approximatePi(int size) {
        int total = 0; // Keep track of total points thrown.
        int inside = 0; // Keep track of points inside the circle.
        //for (Point d : darts) {
        for (int i = 0; i < size; i++)
            Point d = Point.genRandPoint();
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
            //this.throwDarts(Config.DARTS_PER_ACTOR); // I thought this was just extra memory/cpu usage
            getSender().tell(approximatePi(Config.DARTS_PER_ACTOR), getSelf());
        } else {
            getContext().stop(getSelf());
            unhandled(msg);
        }
    }

    @Override
    public void preStart() {
        //darts = new ArrayList<Point>(DARTS_PER_ACTOR); //right here we can also initialize arraylist with the number of darts for each actor so that it doesnt have to resize later. - Jesse
    }
}
