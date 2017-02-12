package com.geekcap.informit;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Path;
import java.io.*;

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
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "118" ) );
		Session session = driver.session();

		// run something
		//session.run( "CREATE (a:Person {name: {name}, title: {title}})", parameters( "name", "Arthur", "title", "King" ) );
		
		//StatementResult result = session.run( "MATCH (a:Person) WHERE a.name = {name} " + "RETURN a.name AS name, a.title AS title", parameters( "name", "Arthur" ) );
		int id_1, id_2;
		
		//TODO loop in different nodes
		id_1 = 12;
		id_2 = 1000;
		StatementResult result = session.run(
		"MATCH (n1:node {id: {id_start}}), (n2:node {id: {id_des}}), p = shortestPath((n1)-[*]-(n2))"
 		+ " RETURN EXTRACT(n in NODES(p) | n.id) AS nodes",
		parameters("id_start", id_1, "id_des", id_2));
		
		//Path path = result.start().get("p").asPath(); 
		
		//for( Path.Segment segment : path ) {
		//	System.out.println(segment.start().id(), segment.end().id());
		//}
		try{ 
			PrintWriter writer = new PrintWriter("/mnt/myssd/data/shortest.out", "UTF-8"); 
			while ( result.hasNext() )
			{
    			Record record = result.next();
				writer.println(record.get("nodes"));
			}
			
			writer.close(); 
		
		} catch (IOException e) {
 			// do something 
			System.out.println("Encountering IO failure!");
		}
		
		session.close();
    }
}
