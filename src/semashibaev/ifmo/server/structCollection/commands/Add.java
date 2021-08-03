package semashibaev.ifmo.server.structCollection.commands;

import semashibaev.ifmo.server.CommandProcessing;
import semashibaev.ifmo.server.Struct;
import semashibaev.ifmo.server.structCollection.Movie;
import semashibaev.ifmo.server.structCollection.MovieReaderServer;



/**
 * Command used for simply adding element {@link semashibaev.ifmo.server.structCollection.Movie} to collection {@link Struct}
 */
public class Add extends Command {
    public Add(CommandProcessing commandProcessing) {
        super(commandProcessing);
        this.helpInfo = "Add new Movie colection\n" +
                "\tfirst pass <name>;<oscars_count>;<golden_palm_count>;<total_box_office>\n" +
                "\tthen pass coordinates like <x>;<y>\n" +
                "\tmpaaRating and screenwriter could be empty";
        this.answer="";
    }

    @Override
    public void run() throws Exception {
        this.answer="";
        Movie m = MovieReaderServer.movieRead(this.args, this.commandProcessing.struct);
        m.setOwner(this.user);
        if (this.commandProcessing.db.addMovie(m)){
            this.commandProcessing.struct.addFirst(m);
            this.answer="Element was added";
        }else{
            this.answer="Database error (try again)";
        }
    }

    @Override
    protected void checkArgs() throws Exception {
        if (this.args.size() < 8)
            this.answer= "**Too many arguments";
    }
}