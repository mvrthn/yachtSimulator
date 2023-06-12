import java.awt.*;

public class Data {
    public int yachtWidth;
    public int yachtHeight;
    public Point rP; //rudder left corner position
    public Point rA; //rudder axis position
    public Point rS; //rudder size
    public Point mPA; //main sail tack position
    public Point mPB; //main sail sheet position
    public int mL; //main sail length
    public int mSML; //main sail sheet max len
    public int mSmL; //main sail sheet min len
    public Point jPA; //jib sail tack position
    public Point jPB; //jib sail left sheet position
    public Point jPC; //jib sail right sheet position
    public int jL; //jib sail length
    public int jSML; //jib sail sheet max len
    public int jSmL; //jib sail sheet min len

    public Data() {
        yachtWidth = 192;
        yachtHeight = 512;
        rP = new Point(92, 412);
        rA = new Point(96, 512);
        rS = new Point(8, 132);
        mPA = new Point(64, 150);
        mPB = new Point(64, 300);
        mL = 150;
        mSML = 200;
        mSmL = 0;
        jPA = new Point(64, 4);
        jPB = new Point(16, 172);
        jPC = new Point(48, 172);
        jL = 128;
        jSML = 180;
        jSmL = 48;
    }
}

