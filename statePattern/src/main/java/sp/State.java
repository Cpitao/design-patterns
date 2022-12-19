package sp;

public abstract class State implements IState{
    protected boolean accepting;

    public boolean isAccepting() {
        return accepting;
    }
}
