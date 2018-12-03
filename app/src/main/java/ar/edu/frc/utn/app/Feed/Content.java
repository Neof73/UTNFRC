package ar.edu.frc.utn.app.Feed;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by Mario Di Giorgio on 12/08/2017.
 *
 * Clase que representa la etiqueta <media:content> del feed
 */

@Root(name="content", strict = false)
public class Content {

    @Attribute(name="url")
    private String url;

    public Content() {
    }

    public Content(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
