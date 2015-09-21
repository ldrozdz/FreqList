package pl.net.lkd.freqlist.controllers

import org.apache.tika.Tika
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.AutoDetectParser
import org.apache.tika.parser.ParseContext
import org.apache.tika.sax.BodyContentHandler
import org.glassfish.jersey.media.multipart.FormDataContentDisposition
import org.glassfish.jersey.media.multipart.FormDataParam
import org.glassfish.jersey.server.mvc.Viewable

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/")
public class IndexController {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable index() {
        def params = [:];
        return new Viewable("/index", params);
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Viewable process(@FormDataParam("file") InputStream fileInputStream,
                            @FormDataParam("file") FormDataContentDisposition fileDisposition,
                            @FormDataParam("lowercase") String lowercase) {
        def ci = lowercase as boolean
        def text = readFile(fileInputStream)
        def freqs = countFrequencies(text, ci)
        return new Viewable("/index", freqs);
    }

    private String readFile(InputStream fis) {
        byte[] bytes = fis.bytes
        fis.close()
        InputStream is = new ByteArrayInputStream(bytes)
        Metadata metadata = new Metadata()
        BodyContentHandler handler = new BodyContentHandler(1024 * 1024 * 1024)
        AutoDetectParser parser = new AutoDetectParser()

        String mimeType = new Tika().detect(is)
        metadata.set(Metadata.CONTENT_TYPE, mimeType)


        parser.parse(is, handler, metadata, new ParseContext())
        is.close()

        return handler.toString()
    }

    private Map countFrequencies(String text, boolean ci) {
        def frequency = {
            it.inject([:]) { map, value -> map[value] = (map[value] ?: 0) + 1; map }
        }

        def words = text?.replaceAll('\\p{Z}',' ')?.tokenize()
        if (ci) {
            words = words.collect {it.toLowerCase()}
        }
        def freq = frequency(words).sort {-it.value}
        return freq
    }


}
