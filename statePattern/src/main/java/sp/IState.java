package sp;

public interface IState {
    IState changeState(char in);
    boolean isAccepting();
}
