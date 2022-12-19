package sp;

public class StateB extends State {

    public StateB() {
        super.accepting = true;
    }

    @Override
    public IState changeState(char in) {
        return switch(in) {
            case 'b' -> new StateB();
            case 'a' -> new StateF();
            default -> new StateN();
        };
    }
}
