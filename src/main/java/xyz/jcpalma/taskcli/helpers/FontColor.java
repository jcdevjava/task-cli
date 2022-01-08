package xyz.jcpalma.taskcli.helpers;

public enum FontColor {
    BLACK(0),
    RED(1),
    GREEN(2),
    YELLOW(3),
    BLUE(4),
    MAGENTA(5),
    CYAN(6),
    WHITE(7),
    BRIGHT(8);

    private final int code;

    FontColor(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
