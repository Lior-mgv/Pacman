package View.ObjectViews;

import Model.Direction;
import Model.Character;

public abstract class CharacterView extends ObjectView {
    public Direction getDirection() {
        return getObject().getDirection();
    }
    public Character getObject(){
        return (Character) object;
    }
}
