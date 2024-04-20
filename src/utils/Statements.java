/**
 * File: Statements.java
 * Authors: Lauren Dennedy G01462079, Lingyun Dai G01464288
 * Class: CS 550 Database Systems
 * Assignment: Group Project - Project 2
 * Created: 4/19
 * Last Modified: 4/20
 */

package utils;

public class Statements {

    public static final String GET_PUBLICATIONS_TABLE_QUERY = "SELECT * FROM PUBLICATIONS";
    public static final String GET_AUTHORS_TABLE_QUERY = "SELECT * FROM AUTHORS";
    public static final String GET_PUBLICATION_FROM_ID_QUERY = "SELECT * FROM PUBLICATIONS WHERE PUBLICATIONID = %s";
    public static final String GET_AUTHOR_COUNT_QUERY = "SELECT COUNT(a.author) AS COUNT FROM AUTHORS a JOIN PUBLICATIONS p ON (a.PUBLICATIONID = p.PUBLICATIONID) WHERE (a.PUBLICATIONID = %s)";
    
    // Beginning portion of query for custom search 
    public static final String CUSTOM_SEARCH_HEADER = "SELECT p.*, a.AUTHOR FROM PUBLICATIONS p JOIN AUTHORS a ON (a.PUBLICATIONID = p.PUBLICATIONID) WHERE ";

    // Conditions
    public static final String CONDITION_AUTHOR = "(a.AUTHOR LIKE '%s')";
    public static final String CONDITION_YEAR = "(p.YEAR LIKE '%s')";
    public static final String CONDITION_TYPE = "(p.TYPE LIKE '%s')";
    public static final String CONDITION_TITLE = "(p.TITLE LIKE '%s')";

    // Sort by
    public static final String ORDER_BY = " ORDER BY (%s)";
}
