package netty.rpc;

import netty.rpc.dto.RpcDTO;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RpcTest {
    public String print(String text) {
        return text;
    }

    public static void main(String[] args) {
        RpcDTO rpcDTO = RpcDTO.builder().target(RpcTest.class).method("print")
                .args(new Class[]{String.class}).argsInstance(new Object[]{"asdasd"}).build();
        Future<Object> future = RpcClient.rpc(rpcDTO);
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
