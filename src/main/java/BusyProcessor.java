import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.TypedIOPort;
import ptolemy.actor.util.Time;
import ptolemy.data.DoubleToken;
import ptolemy.data.RecordToken;
import ptolemy.data.Token;
import ptolemy.data.type.BaseType;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class BusyProcessor extends TypedAtomicActor {

    protected TypedIOPort input, output, discard;

    protected Time finishTime;

    /**
     * Created for EMBS Exercise 2.3.2 Q1 & 2
     * This actor receives record tokens with 'comptime' field and becomes busy for the specified value.
     * While busy, this actor discards all interim inputs.
     * Comptime is time in milliseconds.
     *
     * Outputs processor util (0 or 100)
     */

    public BusyProcessor(CompositeEntity container, String name) throws NameDuplicationException, IllegalActionException {
        super(container, name);

        input = new TypedIOPort(this, "input", true, false);
        output = new TypedIOPort(this, "util", false, true);
        output.setTypeEquals(BaseType.DOUBLE);
        discard = new TypedIOPort(this, "discard", false, true);

        finishTime = getDirector().getModelTime();
    }

    public void fire() throws IllegalActionException {
        //Discard if running and has input token
        Token t = null;
        if(input.hasToken(0)) {
            t = input.get(0);
        }

        if(taskRunning() && t != null) {
            //task is running, discard
            System.out.println("Token discarded");
            discard.send(0, t);
            return;
        }

        if(!taskRunning()) {
            output.send(0, new DoubleToken(0));
            if (t != null) {
                output.send(0, new DoubleToken(100));
                System.out.println("New task created");
                finishTime = getDirector().getModelTime().add(TokenUtils.extractCompTime(t));
                getDirector().fireAt(this, finishTime);
            }
        }
    }

    private boolean taskRunning() {
        return getDirector().getModelTime().compareTo(finishTime) < 0;
    }

}
