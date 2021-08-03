package semashibaev.ifmo.server.structCollection.commands;


import semashibaev.ifmo.server.CommandProcessing;

public class FilterStartsWithName extends Command {
    public FilterStartsWithName(CommandProcessing commandProcessing) {
        super(commandProcessing);
        this.helpInfo = "Prints all Movie elements with name contains <substring>";
        this.answer = "";
    }

    @Override
    public void run() {
        this.answer="This elements are :" +"\n" ;
        String subName = this.args.get(0);
        this.commandProcessing.struct.stream()
                .filter(movie -> movie.getName().startsWith(subName))
                .forEach(validMovie -> this.answer += validMovie.toString() + "\n");

    }

    @Override
    protected void checkArgs() throws Exception {
        if (this.args.size() != 1)
            this.answer = ("**Invalid arguments");
    }
}
