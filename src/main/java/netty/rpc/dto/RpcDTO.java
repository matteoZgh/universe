package netty.rpc.dto;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import lombok.Builder;
import lombok.Data;

import java.util.stream.Stream;

@Data
@Builder
public class RpcDTO {
    private Class<?> target;
    private String method;
    private Class<?>[] args;
    private Object[] argsInstance;

    public String toRpcMsg() {
        return target.getName() + "$" + method + "$" + JSON.toJSONString(args) + "$" + JSON.toJSONString(argsInstance);
    }
}
