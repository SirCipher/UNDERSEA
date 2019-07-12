package undersea.uuv.dsl.factory;

import org.antlr.v4.runtime.ParserRuleContext;
import undersea.uuv.dsl.model.UUV;

public class UUVFactory implements AbstractFactory<UUV> {

    @Override
    public UUV create(ParserRuleContext context) {
        return null;
    }

    @Override
    public UUV get(String name) {
        return null;
    }

}
