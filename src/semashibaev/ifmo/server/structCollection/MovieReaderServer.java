package semashibaev.ifmo.server.structCollection;

import semashibaev.ifmo.server.Struct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MovieReaderServer {


    public static Movie movieRead(ArrayList<String> args, Struct struct) throws Exception {

        System.out.println(args);
        String screenwriter;
        String mpaaRating;
        String name = args.get(0);
        String oscarsCount = args.get(1);
        String goldenPalmCount = args.get(2);
        String totalBoxOffice = args.get(3);
        String coordX = args.get(4);
        String coordY = args.get(5);
        screenwriter = args.get(6);
        mpaaRating = args.get(7);

        Movie m = new Movie();
        m.setName(name);
        m.setCoordinates(coordX,coordY);
        m.setOscarsCount(oscarsCount);
        m.setGoldenPalmCount(goldenPalmCount);
        m.setTotalBoxOffice(totalBoxOffice);
        m.setMpaaRating(mpaaRating);
        m.setPerson(screenwriter);

        return m;
    }

    public static final String header = "name;coord_x;coord_y;creation_date;oscars_count;golden_palm_count;total_box_office;mpaa_rating ;screenwriter" + "\n";

    public static Struct fileReader(String rawData) throws Exception {
        Struct struct = new Struct();
        List<String> objects = Arrays.asList(rawData.split("\n"));

        if (objects.size() == 0)
            return struct;

        String header = objects.get(0);

        if (!header.equals(header)) {
            throw new Exception("**Invalid CSV format");
        }

        for (String line : objects) {
            if (line.equals("") || header.equals(line))
                continue;

            List<String> params = Arrays.asList(line.split(";", -1));


            String name = params.get(0);
            String coordX = params.get(1);
            String coordY = params.get(2);
            String creationDate = params.get(3);
            String oscarsCount = params.get(4);
            String goldenPalmCount = params.get(5);
            String totalBoxOffice = params.get(6);
            String mpaaRating = params.get(7);
            String screenwriter = params.get(8);

            Movie m = new Movie();
            m.setName(name);
            m.setCoordinates(coordX,coordY);
            m.setCreationDate(creationDate);
            m.setOscarsCount(oscarsCount);
            m.setGoldenPalmCount(goldenPalmCount);
            m.setTotalBoxOffice(totalBoxOffice);
            m.setMpaaRating(mpaaRating);
            m.setPerson(screenwriter);
            struct.addFirst(m);

        }
        return struct;
    }
}
