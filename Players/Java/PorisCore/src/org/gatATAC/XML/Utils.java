/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gatATAC.XML;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author txinto
 */
public class Utils {

    /**
     * 
     * @param s
     * @return
     */
    public static String decamelize(String s) {
        if(s == null || s.equals("")) {
            return s;
        }
        StringBuffer buf = new StringBuffer();
        buf.append(Character.toLowerCase(s.charAt(0)));
        for(int i=1; i<s.length(); i++) {
            if(Character.isUpperCase(s.charAt(i))) {
                if(s.length() > i+1 && Character.isLowerCase(s.charAt(i+1))) {
                    buf.append("-");
                }
            }
            buf.append(Character.toLowerCase(s.charAt(i)));
        }
        return buf.toString();
    }

    /**
     * 
     * @param word
     * @return
     */
    public static String camelize(String word) {
        return camelize(word, false);
    }

    /**
     * 
     * @param word
     * @param lowercaseFirstLetter
     * @return
     */
    public static String camelize(String word, boolean lowercaseFirstLetter) {
        // replace all slashes with dots (package separator)
        Pattern p = Pattern.compile("\\/(.?)");
        Matcher m = p.matcher(word);
        while (m.find()) {
            word = m.replaceFirst("." + m.group(1)/*.toUpperCase()*/);
            m = p.matcher(word);
        }

        // uppercase the class name
        p = Pattern.compile("(\\.?)(\\w)([^\\.]*)$");
//        System.out.println("Does " + word + " match " + p + "?");
        m = p.matcher(word);
        if (m.find()) {
//            System.out.println("match! group count: " + m.groupCount());
//            for (int i = 1; i <= m.groupCount(); i++) {
//                System.out.println("group " + i + "=" + m.group(i));
//            }
            String rep = m.group(1) + m.group(2).toUpperCase() + m.group(3);
//            System.out.println("replacement string raw: " + rep);
            rep = rep.replaceAll("\\$", "\\\\\\$");
//            System.out.println("replacement string processed: " + rep);
            word = m.replaceAll(rep);
        }

        // replace two underscores with $ to support inner classes
        p = Pattern.compile("(__)(.)");
        m = p.matcher(word);
        while (m.find()) {
            word = m.replaceFirst("\\$" + m.group(2).toUpperCase());
            m = p.matcher(word);
        }

        // remove all underscores
        p = Pattern.compile("(_)(.)");
        m = p.matcher(word);
        while (m.find()) {
            word = m.replaceFirst(m.group(2).toUpperCase());
            m = p.matcher(word);
        }

        if (lowercaseFirstLetter) {
            word = word.substring(0, 1).toLowerCase() + word.substring(1);
        }

        return word;

    }
    /**
     * 
     * @param e
     * @return
     */
    public static String getTextContent(Element e) {
                String ret=null;
                boolean found=false;
		StringBuffer buffer = new StringBuffer();
		NodeList childList = e.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			Node child = childList.item(i);
			if (child.getNodeType() != Node.TEXT_NODE)
				continue; // skip non-text nodes
			buffer.append(child.getNodeValue());
                        found=true;
		}
                if (found){
                    ret=buffer.toString();
                }
		return ret;
	}

        /**
         * 
         * @param doc
         * @param e
         * @param text
         */
        public static void setTextContent(Document doc,Element e, String text){

		NodeList childList = e.getChildNodes();
		Node nodeFound=null;
		for (int i = 0; i < childList.getLength() && (nodeFound==null); i++) {
			Node child = childList.item(i);
			if (child.getNodeType() == Node.TEXT_NODE) {
				nodeFound=child;
			}
		}
		if (nodeFound!=null){
			nodeFound.setNodeValue(text);
		} else {
			e.appendChild(doc.createTextNode(text));
		}
	}
}
