package com.geekcap.informit;

import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;
/**
 * Java Connector
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		// Set driver
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "Sherlock118" ) );
		Session session = driver.session();

		// run something
		session.run( "CREATE (a:Person {name: {name}, title: {title}})", parameters( "name", "Arthur", "title", "King" ) );

		StatementResult result = session.run( "MATCH (a:Person) WHERE a.name = {name} " + "RETURN a.name AS name, a.title AS title", parameters( "name", "Arthur" ) );

		while ( result.hasNext() )
		{
    		Record record = result.next();
    		System.out.println( record.get( "title" ).asString() + " " + record.get( "name" ).asString() );
		}

		session.close();
    }
}
