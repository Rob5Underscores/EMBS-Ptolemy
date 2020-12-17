import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.TypedIOPort;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

import java.util.HashMap;
import java.util.Map;

public class ProcessorLoadBalancer extends TypedAtomicActor {

    protected TypedIOPort input, queues, output;

    protected Map<Integer, Integer> processorQueues;

    public ProcessorLoadBalancer(CompositeEntity container, String name) throws IllegalActionException, NameDuplicationException {
        super(container, name);

        input = new TypedIOPort(this, "input" , true, false);
        queues = new TypedIOPort(this, "queues", true, false);
        queues.setMultiport(true);
        output = new TypedIOPort(this, "output", false, true);
        queues.setMultiport(true);

        processorQueues = new HashMap<>();
    }

    public void fire() {

    }

}
