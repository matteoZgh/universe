package netty.rpc;

import netty.common.SimpleClient;
import netty.rpc.dto.RpcDTO;
import netty.rpc.handler.ResultHandler;
import netty.rpc.handler.RpcClientHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

public class RpcClient {
    public static Future<Object> rpc(RpcDTO rpcDTO) {
        return rpc0(rpcDTO);
    }

    private static Future<Object> rpc0(RpcDTO rpcDTO) {
        AtomicReference<Future<Object>> res = new AtomicReference<>();
        SimpleClient client = new SimpleClient("localhost", 8848);
        client.addHandler(RpcClientHandler.class);
        client.addProcess(() -> {
            client.getChannel().writeAndFlush(rpcDTO.toRpcMsg());
            ExecutorService executor = Executors.newFixedThreadPool(1);
            Future<Object> future = executor.submit(() -> {
                ResultHandler resultHandler = null;
                while (resultHandler == null) {
                    resultHandler = (ResultHandler) client.getChannel().pipeline().get("res");
                }
                client.getChannel().pipeline().remove("res");
                client.getChannel().close();
                return resultHandler.get();
            });
            executor.shutdownNow();
            res.set(future);
        });
        client.run();
        return res.get();
    }
}
