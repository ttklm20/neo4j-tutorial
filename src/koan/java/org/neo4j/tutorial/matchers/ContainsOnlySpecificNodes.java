package org.neo4j.tutorial.matchers;

import java.util.HashSet;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;

public class ContainsOnlySpecificNodes extends TypeSafeMatcher<Iterable<Path>>
{

    private final HashSet<Node> nodes = new HashSet<>();

    public ContainsOnlySpecificNodes( Node... nodes )
    {
        for ( Node n : nodes )
        {
            this.nodes.add( n );
        }
    }

    @Override
    public void describeTo( Description description )
    {
        description.appendText( String.format( "Path does not contain only the specified nodes." ) );
    }

    @Override
    public boolean matchesSafely( Iterable<Path> paths )
    {
        for ( Path path : paths )
        {
            for ( Node n : path.nodes() )
            {
                if ( nodes.contains( n ) )
                {
                    nodes.remove( n );
                }
            }
        }
        return nodes.size() == 0;
    }

    @Factory
    public static <T> Matcher<Iterable<Path>> containsOnlySpecificNodes( Node... nodes )
    {
        return new ContainsOnlySpecificNodes( nodes );
    }
}
