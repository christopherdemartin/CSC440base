import org.w3c.dom.*;
import java.util.*;

/**
 *  @author Lee Stemkoski
 */
public class DomUtils
{
    public static Element createElementWithText(Document d, String elementName, String elementText)
    {
        Element e = d.createElement( elementName );
        Text t = d.createTextNode( elementText );
        e.appendChild( t );
        return e;
    }

    /**
     *   Returns the textContent of the first child with tagName;
     *   returns null if no such child exists.
     */
    public static String getChildText(Element e, String tagName)
    {
        NodeList nList = e.getElementsByTagName( tagName );

        if (nList.getLength() == 0)
            return null;
        else
        {
            Node n = nList.item(0);
            return n.getTextContent();
        }
    }

    /**
     *   NodeList is a minimal implementation;
     *   this method is useful when additional functionality is needed.
     */
    public static List<Element> convertNodeList(NodeList nList)
    {
        List<Element> eList = new ArrayList<Element>();
        for (int i = 0; i < nList.getLength(); i++)
        {
            Node n = nList.item(i);
            if (n instanceof Element)
                eList.add( (Element)n );
        }
        return eList;
    }

    /**
     *   Alternative to provided method with a more useful return type.
     */
    public static List<Element> getElementsByTagName(Element e, String tagName)
    {
        return convertNodeList( e.getElementsByTagName(tagName) );
    }

    /**
     *   Get the first child element with the given tag name (null if none exists).
     */
    public static Element getElementByTagName(Element e, String tagName)
    {
        List<Element> eList = getElementsByTagName(e, tagName);
        if (eList.size() == 0)
            return null;
        else
            return eList.get(0);
    }

    /**
     *   Returns a sublist of originalList.
     *   Every element in the sublist 
     *     has a child of the form <name>text</name>.
     *   (Assumes at most one child with tag <name>.)
     */

    public static List<Element> filter(List<Element> originalList, String name, String text)
    {
        List<Element> filteredList = new ArrayList<Element>();

        for (Element e : originalList)
        {
            if ( getChildText(e, name).equals(text) )
                filteredList.add(e);
        }
        return filteredList;
    }

    public static List<Element> filter(NodeList nList, String name, String text)
    {
        List<Element> originalList = convertNodeList(nList);
        return filter(originalList, name, text);
    }

}