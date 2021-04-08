package args;

import com.google.common.collect.Maps;

import java.util.Map;

public class Args {
    private Map<String, ArgumentHandler> arg2Type;

    public Args(String scheme, String[] args) {
        this.arg2Type = Maps.newHashMap();
        parseScheme(scheme);

    }

    private void parseScheme(String scheme) {
        char sign = scheme.charAt(0);
        char type = scheme.charAt(1);
        switch (sign) {
            case '#' :
                arg2Type.put(String.valueOf(sign), new IntegerArgumentHandler());
                break;
            default:
        }
    }

    private void parseArgs(String[] args) {

    }

    private void parseArg(String arg) {
        String[] signAndValue = arg.split(" ");
        String sign = signAndValue[0];
        String value = signAndValue[1];
        ArgumentHandler handler = arg2Type.get(sign);
        handler.parse(value);
    }

}
