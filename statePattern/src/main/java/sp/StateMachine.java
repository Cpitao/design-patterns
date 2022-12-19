package sp;

public class StateMachine {

    private IState state;

    private void nextState(char c) {
        state = state.changeState(c);
    }

    public boolean checkWord(String word) {
        state = new StateS();
        for (char c: word.toCharArray()) {
            nextState(c);
        }

        return state.isAccepting();
    }
}
