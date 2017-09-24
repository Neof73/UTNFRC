package ar.edu.frc.utn.app;

import android.content.ClipData;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.List;

/**
 * Created by Mario Di Giorgio on 12/08/2017.
 *
 * Clase que representa la etiqueta <channel> del feed
 */

@Root(name = "channel", strict = false)
public class Channel {


    @ElementList(inline = true)
    private List<Item> items;

    @Path("title")
    @Element(name = "title", required = false)
    private String title;

    @Path("link")
    @Element(name = "link", required = false)
    private String link;

    @Path("description")
    @Element(name = "description", required = false)
    private String description;

    @Path("language")
    @Element(name = "language", required = false)
    private String language;

    @Path("pubDate")
    @Element(name = "pubDate", required = false)
    private String pubDate;

    @Path("webMaster")
    @Element(name = "webMaster", required = false)
    private String webMaster;

    @Path("copyright")
    @Element(name = "copyright", required = false)
    private String copyright;

    @Path("ttl")
    @Element(name = "ttl", required = false)
    private int ttl;

    @Path("image")
    @Element(name = "image", required = false)
    private String image;

    public Channel() {
    }

    public Channel(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }
    public String getPubDate() {
        return pubDate;
    }
    public String getWebMaster() {
        return webMaster;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getImage() {
        return image;
    }

    public int getTtl(){
        return ttl;
    }
}