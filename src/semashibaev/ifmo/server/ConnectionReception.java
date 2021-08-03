package semashibaev.ifmo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import static semashibaev.ifmo.server.App.db;
import static semashibaev.ifmo.server.App.struct;


public class ConnectionReception {
    public static final int PORT = 6000;
    public static final String EXIT = "EXIT";
    public static ServerSocketChannel channel;
    public static boolean running = true;
    private static ExecutorService executorReader = Executors.newCachedThreadPool();
    private static ExecutorService executorSendingAnswers = Executors.newFixedThreadPool(10);
    private ReentrantLock collectionLock = new ReentrantLock();
    private NetGuard guard;
    static ServerSocketChannel serverSocketChannel;
    static Selector selector;
    CommandProcessing commandProcessing;

    public static void main(String[] args) throws Exception {
        App app = new App();
        CommandProcessing commandProcessing = new CommandProcessing(struct,db);
        ConnectionReception connectionReception = new ConnectionReception(PORT, commandProcessing);
        System.out.println("Server started working. " + "\nPort " + PORT +
                "\nWaiting for clients to connect...");
        connectionReception.connect();

    }

    public ConnectionReception(int PORT, CommandProcessing commandProcessing) throws IOException, SQLException, ClassNotFoundException {
        this.serverSocketChannel = ServerSocketChannel.open();
        this.selector = bootstrapServer(serverSocketChannel, PORT);
        this.commandProcessing = commandProcessing ;
        this.guard = new NetGuard();
    }


    public void connect() throws Exception {
        Scanner scanner = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        while (running) {
            if (reader.ready()) {
                String result = reader.readLine().trim();
                System.out.println("result now: " + result);
                if (result.equals(EXIT)) {
                    close();
                    System.out.println(result);
                    continue;

                }

            }


            selector.select(1000);


            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isAcceptable()) {
                    executorReader.execute(HandleAccept());
                }
                iterator.remove();
            }
        }
        executorReader.shutdownNow();
        executorSendingAnswers.shutdownNow();
        selector.close();
        serverSocketChannel.close();
        connect();

    }


    private static Selector bootstrapServer(ServerSocketChannel serverSocketChannel, int port) throws IOException {
        Selector selector = Selector.open();
        InetSocketAddress address = new InetSocketAddress(port);
        serverSocketChannel.bind(address);
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, serverSocketChannel.validOps());
        return selector;
    }

    public static void close() {
        running = false;
        System.out.println("Completion of the program.");
        System.exit(0);
    }

    private ReadingRequests HandleAccept() throws IOException, ClassNotFoundException {
        SocketChannel client = serverSocketChannel.accept();
        System.out.println(client.getLocalAddress() + " connected.");
        Result result = new Result(client);
        return new ReadingRequests(result, this.commandProcessing, executorReader,this.collectionLock,this.guard);


    }
}



