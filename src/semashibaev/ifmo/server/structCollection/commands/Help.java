package semashibaev.ifmo.server.structCollection.commands;


import semashibaev.ifmo.server.CommandProcessing;

public class Help extends Command {

    public Help(CommandProcessing commandProcessing) {
        super(commandProcessing);
        this.helpInfo = "Shows info about all commands";
        this.answer="";
    }
    @Override
    public void run() {
        this.answer="";
        this.commandProcessing.commands.forEach((name, command) -> this.answer+=(name + " : " + command.helpInfo + "\n"));
    }

    @Override
    protected void checkArgs() throws Exception {
        if (!this.args.isEmpty()) {
            this.answer="**Too many arguments";
        }
    }

}
