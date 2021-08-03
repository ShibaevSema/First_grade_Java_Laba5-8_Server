package semashibaev.ifmo.server.structCollection.commands;

import semashibaev.ifmo.server.CommandProcessing;

public class RemoveFirst extends Command {
    public RemoveFirst(CommandProcessing commandProcessing) {
        super(commandProcessing);
        this.helpInfo = "Removes the first Movie elements from the collection";
        this.answer = "";
    }

    @Override
    public void run() {
        this.answer="";
        this.commandProcessing.struct.removeFirst();
        this.answer="Element was removed.";
    }

    @Override
    protected void checkArgs() throws Exception {
        if (this.args.size() != 0)
            this.answer = "**Too many arguments";
    }
}