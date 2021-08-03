package semashibaev.ifmo.server;

import java.nio.channels.SocketChannel;

public class Result {
    SocketChannel channel;
    String result;
    public Result(SocketChannel channel){
        this.result="";
        this.channel=channel;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
