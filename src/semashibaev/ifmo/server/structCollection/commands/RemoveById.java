package semashibaev.ifmo.server.structCollection.commands;

import semashibaev.ifmo.server.CommandProcessing;

public class RemoveById extends Command {
    public RemoveById(CommandProcessing commandProcessing) {
        super(commandProcessing);
        this.helpInfo = "Removes Movie element with this <id> from collection";
        this.answer = "";
    }

    @Override
    public void run() throws Exception {
        this.answer="";
        int id = Integer.parseInt(args.get(0));
        this.commandProcessing.struct.removeIf(el -> el.getId() == id);
        this.answer="Element was removed.";
    }

    @Override
    protected void checkArgs() throws Exception {
        if (this.args.size() != 1)
            this.answer = "**Invalid arguments";
        try {
            Integer.parseInt(args.get(0));
        } catch (Exception e) {
            answer = "**Invalid id format\n" + e.getMessage();
        }
    }
}