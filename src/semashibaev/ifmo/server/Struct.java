package semashibaev.ifmo.server;

import semashibaev.ifmo.server.structCollection.Movie;
import semashibaev.ifmo.server.structCollection.substruct.MpaaRating;
import semashibaev.ifmo.server.structCollection.substruct.Person;

import java.sql.ResultSet;
import java.util.LinkedList;

public class Struct extends LinkedList<Movie> {

    public static Struct fromDatabase(DataBase db) throws Exception {
        Struct struct = new Struct();
        ResultSet rs = db.getCollection();
        while (rs.next()) {
            Movie m = new Movie();
            m.setId(rs.getInt("id"));
            m.setName(rs.getString("name"));
            m.setCoordinates(rs.getString("coordinates_x"), rs.getString("coordinates_y"));

            m.setCreationDate(rs.getString("creationdate"));
            m.setOscarsCount(rs.getString("oscarscount"));
            m.setGoldenPalmCount(rs.getString("goldenpalmcount"));
            m.setGoldenPalmCount(rs.getString("totalboxoffice"));

            String mpaaRating = rs.getString("mpaarating");
            if (mpaaRating.isEmpty())
                m.setMpaaRating(null);
            else
                m.setMpaaRating(MpaaRating.fromString(mpaaRating).toString());

            String screenwriter = rs.getString("screenwriter");
            if (screenwriter.isEmpty())
                m.setPerson(null);
            else
                m.setPerson(String.valueOf(new Person(screenwriter)));
            ;
            m.setOwner(rs.getString("owner"));

            struct.add(m);
        }
        return struct;
    }

    @Override
    public String toString() {
        return "Struct{}";
    }
}