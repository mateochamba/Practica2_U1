package controlador.dao;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import java.io.File;

/**
 *
 * @author FA506ICB-HN114W
 */
public class Bridge {

    public static String URL = "files" + File.separatorChar;
    private static XStream conection;
    //private XStream xstream;
    /**
     * @return the conection
     */
    public static XStream getConection() {
        if(conection == null){
            conection = new XStream(new JettisonMappedXmlDriver());
            conection.addPermission(AnyTypePermission.ANY);
        }
        return conection;
    }

    /**
     * @param aConection the conection to set
     */
    public static void setConection(XStream aConection) {
        conection = aConection;
    }


    
}
