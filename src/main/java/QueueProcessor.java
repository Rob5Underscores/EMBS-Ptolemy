import ptolemy.actor.CompositeActor;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.TypedIOPort;
import ptolemy.actor.util.Time;
import ptolemy.data.IntToken;
import ptolemy.data.Token;
import ptolemy.data.expr.Parameter;
import ptolemy.data.type.BaseType;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import java.util.LinkedList;
import java.util.Queue;

public class QueueProcessor extends TypedAtomicActor {

    protected TypedIOPort input, queueLength;
    protected Parameter processorID;

    protected Time finishTime;
    protected Queue<Double> processQueue;

    /**
     * Created for EMBS Exercise 2.3.2 Q3 & 4
     * This actor receives record tokens with 'comptime' field and adds them to a queue.
     * The actor polls the queue and becomes busy for the specified time.
     * Comptime is time in milliseconds.
     *
     * Outputs current processQueue length
     */

    public QueueProcessor(CompositeActor container, String name) throws IllegalActionException, NameDuplicationException {
        super(container, name);

        input = new TypedIOPort(this, "input", true, false);
        queueLength = new TypedIOPort(this, "queue", false, true);
        queueLength.setTypeEquals(BaseType.INT);
        queueLength.send(0, new IntToken(0));

        processorID = new Parameter(this, "Processor ID");
        processorID.setTypeEquals(BaseType.INT);

        processQueue = new LinkedList<>();
        //finishTime = getDirector().getModelTime();
    }

    public void fire() throws IllegalActionException {
        if(input.hasToken(0)) {
            Token t = input.get(0);
            Double compTime = TokenUtils.extractCompTime(t);
            System.out.println("Process added to queue: " + compTime);
            processQueue.add(compTime);
        }

        if(!taskRunning()) {
            System.out.println("Process finished! Queue size: " + processQueue.size());
            if(processQueue.size() > 0) {
                Double compTime = processQueue.poll();
                System.out.println("Process started from queue: " + compTime);
                finishTime = getDirector().getModelTime().add(compTime);
                getDirector().fireAt(this, finishTime);
            }
        }
        queueLength.send(0, new IntToken(processQueue.size()));
    }

    private boolean taskRunning() {
        return getDirector().getModelTime().compareTo(finishTime) < 0;
    }

}
