package com.geekcap.informit;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Path;

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
		//session.run( "CREATE (a:Person {name: {name}, title: {title}})", parameters( "name", "Arthur", "title", "King" ) );
		
		//StatementResult result = session.run( "MATCH (a:Person) WHERE a.name = {name} " + "RETURN a.name AS name, a.title AS title", parameters( "name", "Arthur" ) );
		StatementResult result = session.run(
		"MATCH (n1:node), (n2:node), p = shortestPath((n1)-[*]->(n2))"
		+ "WHERE n1.id='1' AND n2.id='4'"
 		+ "RETURN p",
		parameters());
		
		//Path path = result.start().get("p").asPath(); 
		
		//for( Path.Segment segment : path ) {
		//	System.out.println(segment.start().id(), segment.end().id());
		//}
		while ( result.hasNext() )
		{
    		Record record = result.next();
			System.out.println(record.get( "p" ));
		}
		session.close();
    }
}
