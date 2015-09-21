package pl.net.lkd.freqlist;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.servlet.ServletProperties;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class FreqList extends ResourceConfig {

  public FreqList() {
    super();
    packages("pl.net.lkd.freqlist.controllers");
    property(JspMvcFeature.TEMPLATES_BASE_PATH, "/WEB-INF/views");
    property(ServletProperties.FILTER_FORWARD_ON_404, true);
    register(JspMvcFeature.class);
    register(MultiPartFeature.class);
  }
}
