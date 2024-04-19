package sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Queries {
    
    private static final String GET_PUBLICATIONS_TABLE_QUERY = "SELECT * FROM PUBLICATIONS";
    private static final String GET_AUTHORS_TABLE_QUERY = "SELECT * FROM AUTHORS";
    private static final String GET_PUBLICATION_FROM_ID_QUERY = "SELECT * FROM PUBLICATIONS WHERE PUBLICATIONID = %s";
    private static final String GET_AUTHOR_COUNT_QUERY = "SELECT COUNT(a.author) AS COUNT FROM AUTHORS a JOIN PUBLICATIONS p ON (a.PUBLICATIONID = p.PUBLICATIONID) WHERE (a.PUBLICATIONID = %s)";
    private static final String GET_PUBLICATIONS_FROM_AUTHOR_QUERY = "SELECT p.* FROM PUBLICATIONS p JOIN AUTHORS a ON (a.PUBLICATIONID = p.PUBLICATIONID) WHERE (a.AUTHOR LIKE '%s')";

    private static ResultSet getPublicationsTable()
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            ResultSet result = statement.executeQuery(GET_PUBLICATIONS_TABLE_QUERY);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> getPublicationsData()
    {
        ArrayList<ArrayList<String>> publicationsData = new ArrayList<ArrayList<String>>();

        ResultSet publicationsResult = getPublicationsTable();

        try {
            if (publicationsResult != null) {
                System.out.println("\nGetting Publications Table Data...");
                while (publicationsResult.next()) {
                    String pubID = publicationsResult.getString("PUBLICATIONID");
                    String year = publicationsResult.getString("YEAR");
                    String type = publicationsResult.getString("TYPE");
                    String title = publicationsResult.getString("TITLE");
                    String summary = publicationsResult.getString("SUMMARY");
    
                    ArrayList<String> row = new ArrayList<>();
                    row.add(pubID);
                    row.add(year);
                    row.add(type);
                    row.add(title);
                    row.add(summary);

                    publicationsData.add(row);
                }
                publicationsResult.getStatement().close();
                System.out.println("\nPublications Table Data Retrieval Complete!");
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return publicationsData;
    }

    private static ResultSet getAuthorsTable()
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            ResultSet result = statement.executeQuery(GET_AUTHORS_TABLE_QUERY);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> getAuthorsData()
    {
        ArrayList<ArrayList<String>> authorsData = new ArrayList<ArrayList<String>>();

        ResultSet authorsResult = getAuthorsTable();

        try {
            if (authorsResult != null) {
                System.out.println("\nGetting Authors Table Data...");
                while (authorsResult.next()) {
                    String pubID = authorsResult.getString("PUBLICATIONID");
                    String author = authorsResult.getString("AUTHOR");
    
                    ArrayList<String> row = new ArrayList<>();
                    row.add(pubID);
                    row.add(author);

                    authorsData.add(row);
                }
                authorsResult.getStatement().close();
                System.out.println("\nAuthors Table Data Retrieval Complete!");
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return authorsData;
    }

    private static ResultSet getPublicationIdTuple(String publicationId)
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            ResultSet result = statement.executeQuery(String.format(GET_PUBLICATION_FROM_ID_QUERY, publicationId));
            System.out.println(result);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> getPublicationIdTupleData(String publicationId)
    {
        ArrayList<ArrayList<String>> publicationsIdTupleData = new ArrayList<ArrayList<String>>();

        ResultSet publicationIdTupleResult = getPublicationIdTuple(publicationId);

        try {
            if (publicationIdTupleResult != null) {
                System.out.println("\nGetting Publication ID Data...");
                while (publicationIdTupleResult.next()) {
                    String pubID = publicationIdTupleResult.getString("PUBLICATIONID");
                    String year = publicationIdTupleResult.getString("YEAR");
                    String type = publicationIdTupleResult.getString("TYPE");
                    String title = publicationIdTupleResult.getString("TITLE");
                    String summary = publicationIdTupleResult.getString("SUMMARY");
    
                    ArrayList<String> row = new ArrayList<>();
                    row.add(pubID);
                    row.add(year);
                    row.add(type);
                    row.add(title);
                    row.add(summary);

                    publicationsIdTupleData.add(row);
                }
                publicationIdTupleResult.getStatement().close();
                System.out.println("\nPublication ID Data Retrieval Complete!");
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return publicationsIdTupleData;
    }

    private static ResultSet getAuthorCount(String publicationId)
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            ResultSet result = statement.executeQuery(String.format(GET_AUTHOR_COUNT_QUERY, publicationId));
            System.out.println(result);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static int getAuthorCountData(String publicationId)
    {
        int authorCount = 0;
        ResultSet authorCountResult = getAuthorCount(publicationId);

        try {
            if (authorCountResult != null) {
                System.out.println("\nGetting Author Count Data...");
                authorCountResult.next();
                authorCount = authorCountResult.getInt("COUNT");
                authorCountResult.getStatement().close();
                System.out.println("\nPublication ID Data Retrieval Complete!");
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return authorCount;
    }

    private static ResultSet getPublicationsFromAuthor(String author)
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            ResultSet result = statement.executeQuery(String.format(GET_PUBLICATIONS_FROM_AUTHOR_QUERY, author));
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> getPublicationsFromAuthorData(String author)
    {
        ArrayList<ArrayList<String>> publicationsData = new ArrayList<ArrayList<String>>();

        ResultSet publicationsResult = getPublicationsFromAuthor(author);

        try {
            if (publicationsResult != null) {
                System.out.println("\nGetting Publications Table Data...");
                while (publicationsResult.next()) {
                    String pubID = publicationsResult.getString("PUBLICATIONID");
                    String year = publicationsResult.getString("YEAR");
                    String type = publicationsResult.getString("TYPE");
                    String title = publicationsResult.getString("TITLE");
                    String summary = publicationsResult.getString("SUMMARY");
    
                    ArrayList<String> row = new ArrayList<>();
                    row.add(pubID);
                    row.add(year);
                    row.add(type);
                    row.add(title);
                    row.add(summary);

                    publicationsData.add(row);
                }
                publicationsResult.getStatement().close();
                System.out.println("\nPublications Table Data Retrieval Complete!");
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return publicationsData;
    }
}
