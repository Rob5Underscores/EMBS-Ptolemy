import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.TypedIOPort;
import ptolemy.data.Token;
import ptolemy.data.expr.Parameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class ExamplePipe extends TypedAtomicActor {

    protected TypedIOPort input, output;
    protected Parameter param;

    public ExamplePipe(CompositeEntity container, String name) throws NameDuplicationException, IllegalActionException {
        super(container, name);

        input = new TypedIOPort(this, "inputlabel", true, false);
        output = new TypedIOPort(this, "outputlabel", false, true);
        param = new Parameter(this, "parameter name");
        param.setExpression("20"); // initial value
    }

    public void fire() throws IllegalActionException{
        Token t = input.get(0);
        output.send(0, t);
    }

}
