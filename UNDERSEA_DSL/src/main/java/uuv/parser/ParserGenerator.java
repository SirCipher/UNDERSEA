package uuv.parser;

public class ParserGenerator {

    private static String grammar = "grammar/UUV.g4";

    public static void main(String[] args) {
        //Generate the language
        String[] arg0 = {"-listener", "-visitor", grammar, "-o", "./src/uuv/dsl/gen"};
//						"-package", "org.spg.antlr.src.gen"};

        org.antlr.v4.Tool.main(arg0);
        System.out.println("UUV parser generated successfully!\n");
    }
}

