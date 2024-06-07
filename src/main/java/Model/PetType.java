package Model;

public enum PetType {
    Cat, Dog, Hamster;

    public static PetType getType(int typeId) {
        return switch (typeId) {
            case 1 -> PetType.Cat;
            case 2 -> PetType.Dog;
            case 3 -> PetType.Hamster;
            default -> null;
        };
    }

    public static Integer getTypeNumber(PetType type) {
        return switch (type) {
            case Cat -> 1;
            case Dog -> 2;
            case Hamster -> 3;
            default -> null;
        };
    }
}
