package org.cprover.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.cprover.ui.Utils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Filters wrapped console output from SATABS xml output.
 * 
 * @author Gérard Basler
 */
public class XmlMessageFilter extends DefaultHandler {

    private static final String MESSAGE = "MESSAGE";
	private static final String TEXT = "TEXT";
    
    private InputStream is;
    private OutputStream os;
    
    private boolean gotMessage = false;
    private boolean gotText = false;

    public XmlMessageFilter(InputStream is, OutputStream os) {
        this.is = is;
        this.os = os;
    }
    
    public void parse() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();
            saxParser.parse(is, this);
        } catch (ParserConfigurationException e) {
            Utils.showErrorDialog(CProverPlugin.getActiveWorkbenchShell(), "", e.toString(), Utils.NORMAL);
        } catch (SAXException e) {
            e.printStackTrace();
//            Utils.showErrorDialog(CProverPlugin.getActiveWorkbenchShell(), "", e.toString(), Utils.NORMAL);
        } catch (IOException e) {
        	CProverPlugin.log(e);
//            Utils.showErrorDialog(CProverPlugin.getActiveWorkbenchShell(), "", e.toString(), Utils.NORMAL);
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if( qName.equalsIgnoreCase(MESSAGE) ) {
            gotMessage = true;
        }
        else if( qName.equalsIgnoreCase(TEXT) ) {
        	if( gotMessage ) {
        		gotText = true;
        	}
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if( qName.equalsIgnoreCase(MESSAGE) ) {
        	assert( !gotText );
            gotMessage = false;
        }
        else if( qName.equalsIgnoreCase(TEXT) ) {
        	gotText = false;
        	String newLine = "\n";
        	try {
				os.write(newLine.getBytes(), 0, newLine.length());
			} catch (IOException e) {
				throw new SAXException(e);
			}
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if( gotText ) {
            String str = new String(ch, start, length);
            try {
                os.write(str.getBytes(), 0, str.length());
            } catch (IOException e) {
                throw new SAXException(e);
            }
        }
    }
}
