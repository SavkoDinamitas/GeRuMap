package raf.dsw.gerumap.app.gui.swing.stateDepartment;

public class StateManager {

    private DodavanjePojmaState dodavanjePojmaState;

    private DodavanjeVezeState dodavanjeVezeState;

    private SelekcijaState selekcijaState;

    private BrisanjeState brisanjeState;

    private MenjanjeStilaState menjanjeStilaState;

    private PromenaImenaPojmaState promenaImenaPojmaState;

    private PomeranjeState pomeranjeState;

    private UvelicavanjeState uvelicavanjeState;

    private State currentState;

    public StateManager(){
        initStates();
    }

    private void initStates(){
        dodavanjePojmaState = new DodavanjePojmaState();
        dodavanjeVezeState = new DodavanjeVezeState();
        selekcijaState = new SelekcijaState();
        brisanjeState = new BrisanjeState();
        menjanjeStilaState = new MenjanjeStilaState();
        promenaImenaPojmaState = new PromenaImenaPojmaState();
        pomeranjeState = new PomeranjeState();
        uvelicavanjeState = new UvelicavanjeState();
        currentState = dodavanjePojmaState;
    }

    public State getCurrentState(){
        return currentState;
    }

    public void setDodavanjePojmaState(){
        currentState = dodavanjePojmaState;
        System.out.println("Postavljeno stanje dodavanja pojma");
    }

    public void setDodavanjeVezeState(){
        currentState = dodavanjeVezeState;
        System.out.println("Postavljeno stanje dodavanja veze");
    }

    public void setSelekcijaState(){
        currentState = selekcijaState;
        System.out.println("Postavljeno stanje selekcije");
    }

    public void setBrisanjeState(){
        currentState = brisanjeState;
        System.out.println("Postavljeno stanje brisanja");
    }

    public void setMenjanjeStilaState(){
        currentState = menjanjeStilaState;
        System.out.println("Postavljeno stanje menjanja stila");
    }

    public void setPromenaImenaPojmaState(){
        currentState = promenaImenaPojmaState;
        System.out.println("Postavljeno stanje promene imena pojma");
    }

    public void setPomeranjeState(){
        currentState = pomeranjeState;
        System.out.println("Postavljeno stanje pomeranja pojma");
    }

    public void setUvelicavanjeState(){
        currentState = uvelicavanjeState;
        System.out.println("Postavljeno stanje uvelicavanja mape uma");
    }
}
