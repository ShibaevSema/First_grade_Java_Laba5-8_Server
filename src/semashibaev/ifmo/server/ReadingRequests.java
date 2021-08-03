package semashibaev.ifmo.server;

import semashibaev.ifmo.cfs.CommandForServer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;


public class ReadingRequests implements Runnable {
    private Result result;
    private CommandProcessing commandProcessing;
    private ExecutorService writer;
    private ReentrantLock collectionLock;
    private NetGuard guard;
    static ByteBuffer buffer = ByteBuffer.allocate(1024);
    private CommandForServer commandForServer;

    public ReadingRequests(Result result, CommandProcessing commandProcessing, ExecutorService writer, ReentrantLock collectionLock, NetGuard Guard) {
        this.commandProcessing = commandProcessing;
        this.writer = writer;
        this.result = result;
        this.collectionLock = collectionLock;
        this.guard = Guard;
        this.commandForServer = null;
    }

    @Override
    public void run() {
        try {
                SocketChannel channel = (SocketChannel) result.getChannel();
                try {
                    channel.read(buffer);
                } catch (IOException e) {
                    return;
                }
                buffer.flip();
                if (buffer.array()[0] == 0) {
                    try {
                        closeConnection(result.getChannel());
                    } catch (IOException e) {
                        return;
                    }
                    System.out.println("Client has closed connection");
                    return;
                }
                buffer.clear();
                byte[] bytes = buffer.array();


                CommandForServer commandForServer = null;
                try {
                    commandForServer = deserialize(bytes);
                    this.commandForServer = commandForServer;
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    return;
                }

                try {
                    System.out.println("Server got [" + commandForServer + "] от " + channel.getLocalAddress() + ". ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String commandRequest = commandForServer.getCommand();
                ArrayList<String> res = commandForServer.getArgs();

                StringBuilder arg = new StringBuilder();

                for (int i = 0; i < res.toArray().length; i++)
                    arg.append(res.get(i)).append(" ");

                result.setResult(commandRequest + " " + arg);
                System.out.println(commandRequest + " " + arg);

                new Thread(new ExecutorWorker(this.commandProcessing, this.writer, this.result, this.guard, this.collectionLock, this.commandForServer)).start();

        } catch (Exception e) {
            System.out.println("Fatal exception");
        }

    }

    public static CommandForServer deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        CommandForServer commandForServer = (CommandForServer) ois.readObject();
        return commandForServer;
    }

    private static void closeConnection(SocketChannel channel) throws IOException {
        channel.close();
    }

}
