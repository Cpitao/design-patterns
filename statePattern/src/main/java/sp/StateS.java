package sp;

public class StateS extends State {

    public StateS() {
        super.accepting = false;
    }

    @Override
    public IState changeState(char in) {
        return switch (in) {
            case 'a' -> new StateA();
            case 'b' -> new StateC();
            default -> new StateN();
        };
    }
}
