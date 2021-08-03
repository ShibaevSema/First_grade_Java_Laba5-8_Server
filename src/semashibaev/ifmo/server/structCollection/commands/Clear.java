package semashibaev.ifmo.server.structCollection.commands;

import semashibaev.ifmo.server.CommandProcessing;

/**
 * Simply removes all elements from the collection
 */
public class Clear extends Command {
    public Clear(CommandProcessing commandProcessing) {
        super(commandProcessing);
        this.helpInfo = "Removes all Movie elements from collection";
    }

    @Override
    public void run() {
        this.answer = "";
        if (this.commandProcessing.db.deleteCollection(this.user)) {
            this.commandProcessing.struct.stream().filter(m -> this.user.equals(m.owner)).forEach(this.commandProcessing.struct::remove);
            this.answer = " ";
        }else
            this.answer = "database error (try again)";

    }



    @Override
    protected void checkArgs() throws Exception {
        if (!this.args.isEmpty()) {
            this.answer = "**Too many arguments";
        }
    }
}