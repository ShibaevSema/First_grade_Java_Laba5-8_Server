package semashibaev.ifmo.server;

import semashibaev.ifmo.cfs.CommandForServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutorWorker implements Runnable {
    private CommandProcessing commandProcessing;
    private ExecutorService writer;
    private Result result;
    private ReentrantLock collectionLock;
    private NetGuard guard;
    private CommandForServer commandForServer;

    ExecutorWorker(CommandProcessing commandProcessing, ExecutorService writer, Result result, NetGuard guard, ReentrantLock collectionLock, CommandForServer commandForServer) {
        this.commandProcessing = commandProcessing;
        this.writer = writer;
        this.result = result;
        this.collectionLock=collectionLock;
        this.guard= guard;
        this.commandForServer=commandForServer;
    }

    @Override
    public void run() {
        if (this.commandProcessing != null && this.guard.auth(this.commandForServer)) {
            try {
                synchronized (this.commandProcessing.struct){
        try {
            this.writer.submit(new SendingAnswers(commandProcessing.handleCommand(result,commandForServer.getUsername()), false));
        } catch (Exception e) {
            e.printStackTrace();
        }
         System.out.println("Successfully executed by user: " + commandForServer.getUsername());
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            result.setResult("Invalid Credentials, register or type valid password");
            this.writer.submit(new SendingAnswers(result, false));
        }
        if (this.commandForServer == null){
            this.writer.submit(new SendingAnswers(result, true));
        }
    }

}