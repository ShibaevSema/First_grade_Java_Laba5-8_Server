package semashibaev.ifmo.server.structCollection.commands;
import semashibaev.ifmo.server.CommandProcessing;
import semashibaev.ifmo.server.structCollection.Movie;

import static semashibaev.ifmo.server.structCollection.MovieReaderServer.movieRead;


public class UpdateID extends Command {
    public UpdateID(CommandProcessing commandProcessing) {
        super(commandProcessing);
        this.helpInfo = "Rewrites a Movie element by <id>";
        this.answer="";
    }

    @Override
    public void run() throws Exception {
        this.answer="";
        int id = Integer.parseInt(this.args.get(0));
        Movie movie = this.commandProcessing.struct.stream()
                .filter(m -> m.getOwner().equals(this.user))
                .filter(m -> m.getId() == id).findFirst().orElse(null);
        if (movie == null)
            this.answer=(String.format("**Can not find object with id: %d", id));
        else {
            args.remove(0);
            Movie newM = movieRead(args, this.commandProcessing.struct);
            newM.owner = this.user;
            if (this.commandProcessing.db.deleteMovie(movie) && this.commandProcessing.db.addMovie(newM)) {
                this.commandProcessing.struct.remove(movie);
                this.commandProcessing.struct.addFirst(newM);
                newM.setId(id);
            }
            this.answer = "Item changed.";
        }
    }

    @Override
    protected void checkArgs() throws Exception {
        if (this.args.size() != 9)
            this.answer="**Invalid arguments";
        try {
            Integer.parseInt(args.get(0));
        } catch (Exception e) {
            this.answer="**Invalid id format";
        }
    }
}
