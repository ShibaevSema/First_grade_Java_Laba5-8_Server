package semashibaev.ifmo.server.structCollection.commands;

import semashibaev.ifmo.server.CommandProcessing;

public class Exit extends Command {
    public Exit(CommandProcessing commandProcessing) {
        super(commandProcessing);
        helpInfo = "Stops program";
        this.answer= "The program was stopped,collection was saved.";
    }

    @Override
    public void run() throws Exception {
        commandProcessing.stop();
//        saveMovies(commandProcessing.struct);

    }

    @Override
    protected void checkArgs() throws Exception {
        if (!this.args.isEmpty()) {
            this.answer="**Too many arguments";
        }
    }
}
