package sp;

public class StateA extends State{

    public StateA() {
        super.accepting = true;
    }

    @Override
    public IState changeState(char in) {
        return switch(in) {
            case 'a' -> new StateA();
            case 'b' -> new StateB();
            default -> new StateN();
        };
    }

}
