package semashibaev.ifmo.server.structCollection.commands;

import semashibaev.ifmo.server.CommandProcessing;
import semashibaev.ifmo.server.structCollection.Movie;
import semashibaev.ifmo.server.structCollection.MovieReaderServer;


public class RemoveLower extends Command {
    public RemoveLower(CommandProcessing commandProcessing) {
        super(commandProcessing);
        this.helpInfo = "Removes the lower Movie elements from the collection";
        this.answer = "";
    }

    @Override
    public void run() throws Exception {
        this.answer="";
        Movie m = MovieReaderServer.movieRead(args, this.commandProcessing.struct);
        this.answer = "Elements was removed (or not).";
        this.commandProcessing.struct.removeIf(mv -> mv.compareTo(m) < 0);
        this.commandProcessing.struct.remove(m);
    }

    @Override
    protected void checkArgs() throws Exception {
        if (this.args.size() != 0)
            this.answer = "**Too many arguments";
    }
}
