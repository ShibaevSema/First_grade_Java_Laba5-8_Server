package semashibaev.ifmo.server.structCollection.commands;

import semashibaev.ifmo.server.CommandProcessing;

public class RemoveAllByGoldenPalmCount extends Command {
    public RemoveAllByGoldenPalmCount(CommandProcessing commandProcessing) {
        super(commandProcessing);
        this.helpInfo = "Removes all Movie element with this <GoldenPalmCount> from collection";
        this.answer="";
    }

    @Override
    public void run() throws Exception {
        this.answer="";
        int goldenPalmCount = Integer.parseInt(args.get(0));
        this.commandProcessing.struct.removeIf(mv -> mv.getGoldenPalmCount() == goldenPalmCount);
        this.answer="Element(s) was removed.";
    }

    @Override

    protected void checkArgs() throws Exception {
        if (this.args.size() != 1)
            this.answer="**Invalid arguments";
        try {
            Integer.parseInt(args.get(0));
        } catch (Exception e) {
            this.answer="**Invalid golden palm count format\n" + e.getMessage();
        }
    }
}


