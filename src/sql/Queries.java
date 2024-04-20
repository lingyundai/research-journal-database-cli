/**
 * File: PublicationsAttrSearchView.java
 * Authors: Lauren Dennedy G01462079, Lingyun Dai G01464288
 * Class: CS 550 Database Systems
 * Assignment: Group Project - Project 2
 * Created: 4/18
 * Last Modified: 4/20
 */

package sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import utils.Statements;

public class Queries {

    private static ResultSet getPublicationsTable()
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            ResultSet result = statement.executeQuery(Statements.GET_PUBLICATIONS_TABLE_QUERY);
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
            ResultSet result = statement.executeQuery(Statements.GET_AUTHORS_TABLE_QUERY);
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
            ResultSet result = statement.executeQuery(String.format(Statements.GET_PUBLICATION_FROM_ID_QUERY, publicationId));
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
            ResultSet result = statement.executeQuery(String.format(Statements.GET_AUTHOR_COUNT_QUERY, publicationId));
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

    private static ArrayList<ArrayList<String>> getPublicationDataFromQuery(ResultSet publicationsResult) 
    {
        ArrayList<ArrayList<String>> publicationsData = new ArrayList<ArrayList<String>>();

        if (publicationsResult != null) {
            System.out.println("\nGetting Publications Table Data...");
            try {
                while (publicationsResult.next()) {
                    String pubID = publicationsResult.getString("PUBLICATIONID");
                    String pubYear = publicationsResult.getString("YEAR");
                    String pubType = publicationsResult.getString("TYPE");
                    String pubTitle = publicationsResult.getString("TITLE");
                    String pubSummary = publicationsResult.getString("SUMMARY");
                    String pubAuthor = publicationsResult.getString("AUTHOR");

                    ArrayList<String> row = new ArrayList<>();
                    row.add(pubID);
                    row.add(pubYear);
                    row.add(pubType);
                    row.add(pubTitle);
                    row.add(pubSummary);
                    row.add(pubAuthor);

                    publicationsData.add(row);
                }
            } catch (SQLException sqle) {
                System.out.println(sqle);
            }
        }
        return publicationsData;
    }

    public static ArrayList<ArrayList<String>> getAllDataFromCustomSearch(String author, String title, String year, String type, String sortOption)
    {
        String query = buildCustomSearchQuery(author, title, year, type, sortOption);

        ResultSet publicationsResult = getAllDataFromCustomSearchResults(query);
        ArrayList<ArrayList<String>> allData = new ArrayList<ArrayList<String>>();
        try {
            allData = getPublicationDataFromQuery(publicationsResult);
            publicationsResult.getStatement().close();
            System.out.println("\nAll Data From Custom Inputs Retrieval Complete!");
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return allData;
    }

    private static String buildCustomSearchQuery(String author, String title, String year, String type, String sortOption)
    {
        String query = Statements.CUSTOM_SEARCH_HEADER;
        boolean hasFirstCondition = false;

        if (!author.isEmpty()) {
            query = query + String.format(Statements.CONDITION_AUTHOR, author);
            hasFirstCondition = true;
        }

        if (!title.isEmpty()) {
            if (hasFirstCondition) {
                query = query + " AND " + String.format(Statements.CONDITION_TITLE, title);
            } else {
                query = query + String.format(Statements.CONDITION_TITLE, title);
                hasFirstCondition = true;
            }
        }

        if (!year.isEmpty()) {
            if (hasFirstCondition) {
                query = query + " AND " + String.format(Statements.CONDITION_YEAR, year);
            } else {
                query = query + String.format(Statements.CONDITION_YEAR, year);
                hasFirstCondition = true;
            }
        }

        if (!type.isEmpty()) {
            if (hasFirstCondition) {
                query = query + " AND " + String.format(Statements.CONDITION_TYPE, type);
            } else {
                query = query + String.format(Statements.CONDITION_TYPE, type);
                hasFirstCondition = true;
            }
        }

        query = query + String.format(Statements.ORDER_BY, sortOption);
        return query;
    }

    private static ResultSet getAllDataFromCustomSearchResults(String query)
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    /*
    private static ResultSet getPublicationsFromYearAuthor(String year, String author, String sortBy) 
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            String prior = String.format(Statements.GET_PUBLICATIONS_FROM_YEAR_AUTHOR_QUERY, year, author);
            String query = String.format(prior + Statements.ORDER_BY, sortBy);
            ResultSet result = statement.executeQuery(query);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> getPublicationsFromYearAuthorData(String year, String author, String sortBy) 
    {
        ResultSet publicationsResult = getPublicationsFromYearAuthor(year, author, sortBy);
        ArrayList<ArrayList<String>> publicationsData = new ArrayList<ArrayList<String>>();
        try {
            publicationsData = getPublicationDataFromQuery(publicationsResult);
            publicationsResult.getStatement().close();
            System.out.println("\nPublications Table Data From Year Author Inputs Retrieval Complete!");
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return publicationsData;
    }

    private static ResultSet getPublicationsFromYearType(String year, String type, String sortBy) 
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            String prior = String.format(Statements.GET_PUBLICATIONS_FROM_YEAR_TYPE_QUERY, year, type);
            String query = String.format(prior + Statements.ORDER_BY, sortBy);
            ResultSet result = statement.executeQuery(query);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> getPublicationsFromYearTypeData(String year, String type, String sortBy) 
    {
        ArrayList<ArrayList<String>> publicationsData = new ArrayList<ArrayList<String>>();

        ResultSet publicationsResult = getPublicationsFromYearType(year, type, sortBy);
        try {
            publicationsData = getPublicationDataFromQuery(publicationsResult);
            publicationsResult.getStatement().close();
            System.out.println("\nPublications Table Data From Year Type Inputs Retrieval Complete!");
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return publicationsData;
    }

    private static ResultSet getPublicationsFromAuthorYearType(String author, String year, String type, String sortBy)
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            String prior = String.format(Statements.GET_PUBLICATIONS_FROM_ALL_AUTHOR_YEAR_TYPE_QUERY, author, year, type);
            String query = String.format(prior + Statements.ORDER_BY, sortBy);
            ResultSet result = statement.executeQuery(query);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> getPublicationsFromAuthorYearTypeData(String author, String year, String type, String sortBy) 
    {
        ArrayList<ArrayList<String>> publicationsData = new ArrayList<ArrayList<String>>();

        ResultSet publicationsResult = getPublicationsFromAuthorYearType(author, year, type, sortBy);
        try {
            publicationsData = getPublicationDataFromQuery(publicationsResult);
            publicationsResult.getStatement().close();
            System.out.println("\nPublications Table Data From Year Type Inputs Retrieval Complete!");
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return publicationsData;
    }
    
    private static ResultSet getPublicationsFromAllInputs(String author, String title, String year, String type, String sortBy)
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            String prior = String.format(Statements.GET_PUBLICATIONS_FROM_ALL_INPUTS_QUERY, author, title, year, type);
            String query = String.format(prior + Statements.ORDER_BY, sortBy);
            ResultSet result = statement.executeQuery(query);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> getPublicationsFromAllInputData(String author, String title, String year, String type, String sortBy) 
    {
        ArrayList<ArrayList<String>> publicationsData = new ArrayList<ArrayList<String>>();

        ResultSet publicationsResult = getPublicationsFromAllInputs(author, title, year, type, sortBy);
        try {
            publicationsData = getPublicationDataFromQuery(publicationsResult);
            publicationsResult.getStatement().close();
            System.out.println("\nPublications Table Data From All Inputs Retrieval Complete!");
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return publicationsData;
    }

    private static ResultSet getPublicationsFromAuthor(String author, String sortBy)
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            String prior = String.format(Statements.GET_PUBLICATIONS_FROM_AUTHOR_QUERY, author);
            String query = String.format(prior + Statements.ORDER_BY, sortBy);
            ResultSet result = statement.executeQuery(query);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> getPublicationsFromAuthorData(String author, String sortBy)
    {
        ArrayList<ArrayList<String>> publicationsData = new ArrayList<ArrayList<String>>();
        ResultSet publicationsResult = getPublicationsFromAuthor(author, sortBy);

        try {
            publicationsData = getPublicationDataFromQuery(publicationsResult);
            publicationsResult.getStatement().close();
            System.out.println("\nPublications Table Data Retrieval Complete!");
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return publicationsData;
    }

    private static ResultSet getPublicationsFromYearTitle(String year, String title, String sortBy)
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            String prior = String.format(Statements.GET_PUBLICATIONS_FROM_YEAR_TITLE_QUERY, year, title);
            String query = String.format(prior + Statements.ORDER_BY, sortBy);
            ResultSet result = statement.executeQuery(query);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> getPublicationsFromYearTitleData(String year, String title, String sortBy)
    {
        ArrayList<ArrayList<String>> publicationsData = new ArrayList<ArrayList<String>>();
        ResultSet publicationsResult = getPublicationsFromYearTitle(year, title, sortBy);

        try {
            publicationsData = getPublicationDataFromQuery(publicationsResult);
            publicationsResult.getStatement().close();
            System.out.println("\nPublications Table Data Retrieval Complete!");
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return publicationsData;
    }

    private static ResultSet getPublicationsFromAuthorTitle(String author, String title, String sortBy)
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            String prior = String.format(Statements.GET_PUBLICATIONS_FROM_AUTHOR_TITLE_QUERY, author, title);
            String query = String.format(prior + Statements.ORDER_BY, sortBy);
            ResultSet result = statement.executeQuery(query);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> getPublicationsFromAuthorTitleData(String author, String title, String sortBy)
    {
        ArrayList<ArrayList<String>> publicationsData = new ArrayList<ArrayList<String>>();
        ResultSet publicationsResult = getPublicationsFromAuthorTitle(author, title, sortBy);

        try {
            publicationsData = getPublicationDataFromQuery(publicationsResult);
            publicationsResult.getStatement().close();
            System.out.println("\nPublications Table Data Retrieval Complete!");
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return publicationsData;
    }

    private static ResultSet getPublicationsFromType(String type, String sortBy)
    {
        try {
            Statement statement = Driver.getDriverConnection().createStatement();
            String prior = String.format(Statements.GET_PUBLICATIONS_FROM_TYPE_QUERY, type);
            String query = String.format(prior + Statements.ORDER_BY, sortBy);
            ResultSet result = statement.executeQuery(query);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> getPublicationsFromTypeData(String type, String sortBy)
    {
        ArrayList<ArrayList<String>> publicationsData = new ArrayList<ArrayList<String>>();
        ResultSet publicationsResult = getPublicationsFromType(type, sortBy);

        try {
            publicationsData = getPublicationDataFromQuery(publicationsResult);
            publicationsResult.getStatement().close();
            System.out.println("\nPublications Table Data Retrieval Complete!");
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return publicationsData;
    }
    */
}
