import ptolemy.data.RecordToken;
import ptolemy.data.Token;

public class TokenUtils {


    public static Double extractCompTime(Token token) {
        if(!(token instanceof RecordToken)) {
            System.out.println("Input isn't a map");
        }
        RecordToken rT = (RecordToken) token;

        if(!(rT.labelSet().contains("comptime"))) {
            System.out.println("Record token doesn't contain comptime");
        }
        return Double.valueOf(rT.get("comptime").toString());
    }
}
