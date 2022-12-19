package sp;

// discarding state
public class StateN extends State {

    public StateN() {
        super.accepting = false;
    }
    @Override
    public IState changeState(char in) {
        return this;
    }
}
