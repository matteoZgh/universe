package netty.rpc;

import netty.common.SimpleServer;
import netty.rpc.handler.RpcServerHandler;

public class RpcServer {
    public static void main(String[] args) {
        SimpleServer server = new SimpleServer(8848);
        server.addHandler(RpcServerHandler.class);
        server.run();
    }
}
