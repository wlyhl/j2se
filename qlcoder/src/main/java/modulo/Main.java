package modulo;

import com.fasterxml.jackson.databind.ObjectMapper;
import modulo.b.Modulo;
import modulo.b.Piece;

import java.util.Date;

/**
 * Created by cuijiandong on 2018/2/11.
 */
public class Main {
    //102110301130201001000012耗时518
    static String s26="{\"level\":26,\"modu\":\"4\",\"map\":[\"032200\",\"100310\",\"232330\",\"210230\",\"232333\",\"213230\"],\"pieces\":[\"XX,.X\",\".XX,XX.\",\"..X.,..X.,.XXX,XXXX,X...\",\"XXX.,..XX,..X.\",\"..X..,..X..,.XX..,XXXXX,..XX.\",\"...X,.XXX,XX..\",\"XX..,.XXX,.XX.,.X..\",\"XXX,XXX,.XX,XX.,.X.\",\".XX,..X,XXX,XX.,.X.\",\"..XX.,.XXXX,.XX..,XX...\",\".X...,XXXXX,...XX,...XX\",\".XX,XX.,XX.,.X.,.X.\"]}";
    static String s3="{\"level\":3,\"modu\":\"2\",\"map\":[\"100\",\"010\",\"011\"],\"pieces\":[\"XXX\",\"X\",\".X,XX\",\"X,X,X\"]}";
    static String s0="{\"modu\":\"2\",\"map\":[\"010\",\"011\",\"010\"],\"pieces\":[\"X.,XX\",\"XX\",\"X,X,X\"]}";
    static String s20="{\"level\":20,\"modu\":\"2\",\"map\":[\"111011\",\"001110\",\"001000\",\"110110\",\"110100\"],\"pieces\":[\"X.X.,XXX.,..XX,..X.\",\"XX.,.X.,XXX,X..\",\"X...,XXXX,.X..,.X..\",\"....XX,XXXXX.,...X..,...X..\",\"X..,X..,XXX\",\"...X.,XXXXX,.X...,.X...\",\"XXX,X..\",\"XXX.,..XX,...X,..XX,..X.\",\".X,XX\",\"XX,.X,.X\"]}";
    static String s19="{\"level\":19,\"modu\":\"3\",\"map\":[\"0022\",\"2102\",\"1112\",\"1111\",\"2210\"],\"pieces\":[\".X,.X,XX,XX\",\"XX,X.,X.\",\"X.,XX\",\"X..,XXX,.X.,.X.\",\"XX,X.\",\"XXX,..X\",\"X.,X.,XX,.X,.X\",\"XXXX\",\"XX,X.\",\"..X.,..X.,XXXX,...X\"]}";
    //10s 2s
    //a 000230122000102022002102耗时9352
    //b 000230122000102022002102耗时5005
    static String s25="{\"level\":25,\"modu\":\"3\",\"map\":[\"1222\",\"0200\",\"1012\",\"2222\",\"2121\"],\"pieces\":[\".X.,.XX,XXX\",\"X.,XX,X.,X.\",\"XXX,..X\",\".X,XX,X.,XX\",\"XXXX\",\"X.,X.,XX,X.,X.\",\"X..,XXX\",\".X,XX\",\".X,XX,X.\",\"X.,X.,X.,XX,.X\",\"X..,X..,XXX\",\".X,XX,.X\"]}";
    //8s 8s 000001014040120120201022耗时8324
    static String s24="{\"level\":24,\"modu\":\"2\",\"map\":[\"01010\",\"10100\",\"01101\",\"11101\",\"00111\",\"01011\"],\"pieces\":[\"XX.,.XX\",\"X,X,X,X\",\"X.XX,XXX.,.X..,.XX.\",\"XXX,.X.\",\"XXX..,X.XXX\",\"..X.,XXXX\",\".X,XX,XX,X.\",\".XXX,.XX.,..X.,XXX.,..X.\",\"...X,...X,XXXX\",\"..X,XXX\",\"..XX.,XXXXX,XX..X\",\"X..,X..,XXX,.X.\"]}";
    static String s27="{\"level\":27,\"modu\":\"3\",\"map\":[\"21211\",\"20220\",\"20012\",\"22002\",\"22000\"],\"pieces\":[\"XX.,X..,XXX\",\"X..,XXX\",\"XXX,X.X\",\"..XX,..X.,XXX.\",\"X..,XXX,X..\",\".X.,XXX,XX.\",\".X.,.XX,XX.,.X.\",\"X,X,X,X\",\"X,X,X\",\"XX,XX\",\"XXXX,..X.\",\".X,XX,X.\",\".XX,XX.\"]}";
//00000100040001201030120500耗时32
    static String s28="{\"level\":28,\"modu\":\"2\",\"map\":[\"001110\",\"001101\",\"010100\",\"011000\",\"111000\"],\"pieces\":[\"XXX,.X.,XX.,.X.\",\"XX..,XX..,.XXX\",\"XXXX,.X..\",\"X..,X.X,XXX,X..\",\".X,XX,.X,.X\",\"X....,XXXXX,.X...\",\"X.,XX,X.\",\"XXXX\",\".X.,XX.,.XX\",\"XX.,XXX\",\".XXX,XX..\",\"X,X,X,X\",\"..X..,XXXXX\"]}";
    //11220021313300013122103301耗时191770
    static String s29="{\"level\":29,\"modu\":\"3\",\"map\":[\"202220\",\"202112\",\"220000\",\"200201\",\"211221\",\"212001\",\"021211\"],\"pieces\":[\"XXXXX,XXXX.,XX...\",\"..X.,.XX.,XXXX,X...\",\"...X,..XX,.XX.,XX..\",\".X..,.XX.,XXX.,XXXX,.X..\",\"XXX,..X,.XX\",\"XXX,.XX,..X\",\"X...,XXXX,XX..,.X..,.X..\",\"...X,..XX,.XXX,..X.,XXX.\",\".X...,.X...,.XX..,XXXXX\",\".X..,XXXX,.XX.,..XX,...X\",\".X.,.XX,.X.,XX.,XX.\",\"X.,XX,.X,.X\",\".X.,.XX,.X.,XX.,XXX\"]}";
    //预处理耗时5441找到答案
//0020300030304101303243003112耗时4114735
    static String s30="{\"level\":30,\"modu\":\"2\",\"map\":[\"111000\",\"000100\",\"010101\",\"110001\",\"011010\",\"100010\",\"001111\",\"111111\"],\"pieces\":[\".XXX,.XXX,.X..,XX..,XXX.\",\"...XX,XX.X.,.XXXX,.XXX.\",\"..X,.XX,XX.,.XX\",\"..XX,..X.,.XXX,XXXX,..X.\",\".X,XX,XX,X.,XX\",\"..X..,.XX..,.XXX.,XXXX.,...XX\",\".X,XX,.X,.X\",\"X.X.,XXX.,..XX\",\"...X.,..XX.,XXXXX\",\"XXXX,...X\",\"XXX,XX.,.XX,..X\",\"XXXX.,..XXX,.XXXX,...X.\",\"XX.,.XX,.X.\",\"..X.,.XXX,XXX.,X.X.,X.X.\"]}";
    //预处理耗时329找到答案 2001110001000010101021003121耗时3121
    static String s31= "{\"level\":31,\"modu\":\"4\",\"map\":[\"20230\",\"01213\",\"32230\",\"23213\",\"00220\"],\"pieces\":[\"XX,X.\",\".X,.X,.X,.X,XX\",\"X..,X..,XXX,X..\",\"XXX,X..\",\"X.X,XXX\",\".X..,XXXX,.X..\",\".X.,.X.,XX.,.XX,.X.\",\".XX,XX.\",\".XXXX,XX...\",\"X.,X.,XX\",\".X,XX\",\"X..,XXX\",\"XXXX,..X.\",\"..X,.XX,XXX\"]}";
    static String s31_="{\"level\":31,\"modu\":\"4\",\"map\":[\"20230\",\"01213\",\"32230\",\"23213\",\"00220\"],\"pieces\":[\"XX,X.\",\".X,.X,.X,.X,XX\",\"X..,X..,XXX,X..\",\"XXX,X..\",\"X.X,XXX\",\".X..,XXXX,.X..\",\".X.,.X.,XX.,.XX,.X.\",\".XX,XX.\",\".XXXX,XX...\",\"X.,X.,XX\",\".X,XX\",\"X..,XXX\",\"XXXX,..X.\",\"..X,.XX,XXX\"]}";
    //预处理耗时734找到答案 0100120323000000020200124021耗时150896
    static String s32="{\"level\":32,\"modu\":\"2\",\"map\":[\"1111110\",\"0111001\",\"0011100\",\"0101111\",\"0100100\",\"0000011\"],\"pieces\":[\"..X.,.XXX,.X..,XX..\",\".X..,.XX.,XXXX,XXX.,.XX.\",\".X,.X,.X,XX,.X\",\"..X.,..XX,XXX.,.XX.,.XX.\",\".X..,XX..,.X..,.XXX\",\"XXXX.,..XXX\",\"..X..,XXXXX,X.XXX,X..X.\",\".X..,XXX.,..XX,...X\",\"..X.,..X.,XXX.,..XX,XXXX\",\".X...,XXX..,XXXX.,..XXX\",\"..X,..X,..X,.XX,XX.\",\"..XX,XXX.\",\"XXXXX,....X\",\"XXXX,.XXX,..XX,..X.\"]}";
    static String s33="{\"level\":33,\"modu\":\"3\",\"map\":[\"002220\",\"022000\",\"110002\",\"112011\",\"120121\",\"010120\",\"021100\",\"201122\",\"200100\"],\"pieces\":[\"...XX,..XXX,XXX.X,....X,....X\",\".X.,.X.,XXX\",\"..X.,..X.,.XXX,XX..\",\"..X.,.XX.,.XXX,XXX.,..X.\",\".X..,.XXX,XX..,.X..,.X..\",\"XX.,.X.,.XX,.XX\",\"...X,XXXX,X...\",\".XXX,.XXX,XXXX,X..X\",\"...X,.X.X,XXXX,XXXX,.XX.\",\"X.X..,XXX..,XXXXX,.XX..,..X..\",\".XXX,XXX.,X.X.,XX..,.X..\",\"XX.,.X.,.XX,..X\",\".X.,.X.,.X.,.X.,.XX,XX.\",\"XXX,X..\",\".X...,.X...,.XXXX,XXX.X\"]}";
    public static void main(String[]a) throws Exception {
        long l=new Date().getTime();
        Modulo m = new ObjectMapper().readValue(s33, Modulo.class);
        System.out.print("预处理耗时"+(new Date().getTime()-l));
        l=new Date().getTime();
        m.test();
        System.out.print("耗时"+(new Date().getTime()-l));
        System.out.println();
        for (int i = 0; i < Piece.count.length; i++) {
            System.out.println( Piece.countName[i]+":"+ Piece.count[i]);
        }
    }
}
