package sp;

public class StateF extends State {

    public StateF() {
        super.accepting = true;
    }
    @Override
    public IState changeState(char in) {
        return new StateN();
    }
}
