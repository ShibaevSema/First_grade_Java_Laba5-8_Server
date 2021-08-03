package semashibaev.ifmo.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SendingAnswers implements Runnable {
    private boolean closeOnWrite;
    private Result result;
    public SendingAnswers(Result result,boolean closeOnWrite){
        this.result=result;
        this.closeOnWrite=closeOnWrite;
    }

    @Override
    public void run() {
        try {
            SocketChannel channel = (SocketChannel) result.getChannel();
            System.out.println("Send answer on"+result.getChannel().getLocalAddress());
            channel.write(ByteBuffer.wrap(result.getResult().getBytes()));
            if (closeOnWrite)
                channel.close();
        } catch (IOException e) {
            try {
                result.getChannel().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
