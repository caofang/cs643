
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;



public class WordCount  {

    public static void main(String[] args) throws Exception {

        System.out.print("===== trace =====\n");

        
    
        String in = "hello -hello- world\n\n hello world hello ";
        int i = 0;
        Pattern p = Pattern.compile("hello");
        Matcher m = p.matcher( in );
        while (m.find()) {
            i++;
        }
        System.out.println(i); // Prints 2
                




    }
}