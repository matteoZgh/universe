package param;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public abstract class Param {
    public void copyOfParam(Param p) {
        try {
            BeanUtils.copyProperties(this, p);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
