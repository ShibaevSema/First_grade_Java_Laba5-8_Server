package semashibaev.ifmo.server.structCollection.commands;

import semashibaev.ifmo.server.CommandProcessing;

import java.util.ArrayList;

/**
 * Class represents command in interactive interpreter
 */
public abstract class Command {

    /**
     * Reference to interactive interpreter (used for access user input, output and main structure @see Struct)
     */
    final CommandProcessing commandProcessing;
    /**
     * Arguments transferred from user input
     */
    ArrayList<String> args;
    /**
     * Help information for help command @see Help
     */
    public String helpInfo;
    public String answer;
    public String user;

    Command(CommandProcessing commandProcessing) {
        this.commandProcessing = commandProcessing;
    }

    public void execute() throws Exception {
        checkArgs();
        run();
    }

    protected abstract void run() throws Exception;

    /**
     * Checks user input
     */
    protected abstract void checkArgs() throws Exception;

    public void setArgs(ArrayList<String> args) throws Exception {
        this.args = args;
        checkArgs();
    }
    public void setUser(String user ) {
        this.user = user;
    }

    public String getHelpInfo() {
        return helpInfo;
    }

    public String getAnswer() { return answer; }
}
