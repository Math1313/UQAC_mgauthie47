import Observer.Observer;

import java.util.ArrayList;

public class Organisme implements Observer
{
    public ArrayList<Centre> listeCentres = new ArrayList<>();

    Organisme()
    {

    }

    @Override
    public void update(String message) {
        System.out.println(message);
    }
}
