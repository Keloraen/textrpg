package textRPGAndor;

public class GameObject implements Cloneable {
    protected enum type{GameCharacter, Item, MapObject};
    public int posX, posY, lastPosX, lastPosY;

    @Override
    public Object clone() throws CloneNotSupportedException // Копирование объектов
    {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Клонирование невозможно");
            return this;
        }

    }
}