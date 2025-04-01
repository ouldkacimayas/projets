import java.io.*;
import org.antlr.v4.runtime.*;
public class MainCalculette {
    public static void main(String args[]) throws Exception {
        ma_grammaireLexer lex;
        if (args.length == 0) 
            lex = new ma_grammaireLexer(CharStreams.fromStream(System.in));
        else 
            lex = new ma_grammaireLexer(CharStreams.fromFileName(args[0]));
            
        CommonTokenStream tokens = new CommonTokenStream(lex);
        
        ma_grammaireParser parser = new ma_grammaireParser(tokens);
        try {
            parser.calcul();    // start l'axiome de la grammaire 
        } catch (RecognitionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
     
    }
}