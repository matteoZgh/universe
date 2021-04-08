package param;

import com.google.common.base.Preconditions;

import java.util.function.BooleanSupplier;

public abstract class Validator {
    protected boolean result;

    protected Validator() {
        result = true;
    }

    protected Validator and(BooleanSupplier expression) {
        if (result) {
            result = expression.getAsBoolean();
        }
        return this;
    }

    public void validate(String msg) {
        Preconditions.checkArgument(result, msg);
    }
}
