package populate;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.ActorRef;

/**
 * This class is responsible for creating the "dart-throwing" actors.
 * It is also responsible for averaging the results of the Dart actors.
 */
public class World extends UntypedActor {

    // Sum of the Dart Actor results.
    private static double sum = 0.0;
    // Keep track of actors still going.
    private static Integer actorsLeft = Config.ACTOR_COUNT;

    /**
     * Calculate the "average of the averages".
     * Each Dart actor approximates pi. This function takes those results
     * and averages them to get a *hopefully* better approximation of pi.
     */
    public void onReceive(Object msg) {
        if (msg != null) {
            sum += (double) msg;
            actorsLeft--;
            if (actorsLeft <= 0) {
                System.out.println(sum / (double) Config.ACTOR_COUNT);
                getContext().stop(getSelf());
            }
        } else {
            unhandled(msg);
        }
    }
    
    /**
     * Initializes a number of darts and tells the Dart actors
     * and tells them to start computing.
     */
    @Override
    public void preStart() {
        for (int i = 0; i < Config.ACTOR_COUNT; i++) {
            final ActorRef dart = getContext()
                .actorOf(
                    Props.create(Dart.class), 
                    "dart" + Integer.toString(i));
            // The choice of "0" is used, but anything non-null would
            // work. (If it were null, the Dart actor would die before
            // it did any work.
            dart.tell(0, getSelf());
        }
    }
}
