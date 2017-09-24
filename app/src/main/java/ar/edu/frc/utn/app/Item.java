package ar.edu.frc.utn.app;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mario Di Giorgio on 12/08/2017.
 *
 * Clase que representa la etiqueta <item> del feed
 */

@Root(name = "item", strict = false)
public class Item {

    @Element(name="title")
    private String title;

    @Element(name = "description")
    private String descripcion;

    @Element(name="link")
    private String link;

    @Element(name="pubDate")
    private String pubDate;

    @Element(name="guid")
    private String guid;

    public Item() {
    }

    public Item(String title, String descripcion, String link, String pubDate, String guid) {
        this.title = title;
        this.descripcion = descripcion;
        this.link = link;
        this.pubDate = pubDate;
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLink() {
        return link;
    }

    public String getPubDate() {
        DateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        pubDate = pubDate.replace("enero", "Jan");
        pubDate = pubDate.replace("febrero", "Feb");
        pubDate = pubDate.replace("marzo", "Mar");
        pubDate = pubDate.replace("abril", "Apr");
        pubDate = pubDate.replace("mayo", "May");
        pubDate = pubDate.replace("junio", "Jun");
        pubDate = pubDate.replace("agosto", "Aug");
        pubDate = pubDate.replace("septiembre", "Sep");
        pubDate = pubDate.replace("octubre", "Oct");
        pubDate = pubDate.replace("noviembre", "Nov");
        pubDate = pubDate.replace("diciembre", "Dec");
        Date dPubDate = null;
        try {
            dPubDate = df.parse(pubDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String sPubDate = df2.format(dPubDate);

        return sPubDate;
    }

    public  String getGuid() { return guid; }
}

