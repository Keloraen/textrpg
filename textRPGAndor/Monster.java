package textRPGAndor;

public class Monster extends GameCharacter {
    
    //конструктор новой версии
    public Monster(EnumClass charClass, String name, EnumGender gender, int rank, int strength, int will, Sprite sprite){
        super(charClass, name, gender, rank, strength, will, sprite);
    }

    //конструктор старой версии
    public Monster(EnumClass charClass, String name, EnumGender gender, int rank, int strength, int will) {
        super(charClass, name, gender, rank, strength, will);
    }
}
