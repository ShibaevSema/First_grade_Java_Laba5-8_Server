package semashibaev.ifmo.server;

import semashibaev.ifmo.server.structCollection.commands.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.StringTokenizer;


public class CommandProcessing {
    public static LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
    public static InputStream source;
    public Struct struct;
    public DataBase db;
    private boolean stop = false;
    public static PrintStream sink;
    private volatile boolean closed = false;
    public static Scanner scanner = new Scanner(System.in);


    public CommandProcessing(Struct struct,DataBase db) {
        this.struct = struct;
        this.db=db;
        commands.put("exit", new Exit(this));
        commands.put("help", new Help(this));
        commands.put("add", new Add(this));
        commands.put("clear", new Clear(this));
//        commands.put("execute_script", new ExecuteScript(this));
        commands.put("info", new Info(this));
        commands.put("filter_starts_with_name", new FilterStartsWithName(this));
        commands.put("print_unique_mpaa_rating", new PrintUniqueMpaaRating(this));
        commands.put("remove_lower", new RemoveLower(this));
        commands.put("remove_all_by_golden_palm_count", new RemoveAllByGoldenPalmCount(this));
        commands.put("remove_first", new RemoveFirst(this));
        commands.put("remove_greater", new RemoveGreater(this));
        commands.put("show", new Show(this));
        commands.put("update", new UpdateID(this));
        commands.put("remove_by_id", new RemoveById(this));

    }

    public static String answer;

    public Result handleCommand(Result result, String username) throws Exception {
        String command_answer = " ";
        StringTokenizer tokenizer = new StringTokenizer(result.getResult().trim(), " ");
        String command = tokenizer.nextToken();
        ArrayList<String> args = new ArrayList<>();
        while (tokenizer.hasMoreTokens())
            args.add(tokenizer.nextToken());
        Command c = commands.get(command);
        try {
            c.setArgs(args);
            c.setUser(username);
            c.execute();
            command_answer = c.getAnswer();
            answer = command_answer;
        } catch (Exception e) {
            answer = " ";
        }
        result.setResult(answer);

        return result;

    }

    public void stop() {
        this.stop = true;
    }

//    public static void handleScript(InputStream source) throws Exception {
//        Scanner scanner = new Scanner(source);
//        while (scanner.hasNextLine()) {
//            String resultScript = scanner.nextLine();
//            handleCommand(resultScript);
//        }
}



