package sp;

public class StateC extends State {

    public StateC() {
        super.accepting = true;
    }

    @Override
    public IState changeState(char in) {
        return switch(in) {
            case 'c' -> new StateB();
            case 'a' -> new StateC();
            default -> new StateN();
        };
    }
}
