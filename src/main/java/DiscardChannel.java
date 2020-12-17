import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.TypedIOPort;
import ptolemy.data.Token;
import ptolemy.data.expr.Parameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

import java.util.Random;

import static java.lang.Integer.parseInt;

public class DiscardChannel extends TypedAtomicActor {

    protected TypedIOPort input, output;
    protected Parameter discardPercent;

    /**
     * Created for EMBS Exercise 2.3.1 Q5
     * This actor discards % of the input tokens based on parameter.
     */

    public DiscardChannel(CompositeEntity container, String name) throws NameDuplicationException, IllegalActionException {
        super(container, name);

        input = new TypedIOPort(this, "input", true, false);
        output = new TypedIOPort(this, "output", false, true);
        discardPercent = new Parameter(this, "Discard %");
        discardPercent.setExpression("20"); // initial value
    }

    public void fire() throws IllegalActionException{
        Random rand = new Random();
        Token t = input.get(0);

        if(parseInt(discardPercent.getExpression()) >= rand.nextInt(100)) {
            return;
        }
        output.send(0, t);
    }

}
