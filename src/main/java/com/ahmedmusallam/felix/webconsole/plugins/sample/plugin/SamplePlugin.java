package com.ahmedmusallam.felix.webconsole.plugins.sample.plugin;

import static org.apache.felix.webconsole.WebConsoleConstants.PLUGIN_CATEGORY;
import static org.apache.felix.webconsole.WebConsoleConstants.PLUGIN_LABEL;
import java.io.IOException;
import java.net.URL;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.felix.webconsole.AbstractWebConsolePlugin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(
    service = Servlet.class,
    property = {
      PLUGIN_LABEL + "=" + SamplePlugin.LABEL,
      PLUGIN_CATEGORY + "=" + SamplePlugin.CATEGORY,
    })
public class SamplePlugin extends AbstractWebConsolePlugin {

  // Can be any of the existing ones: "Main", "OSGI", "Sling", "Status", "Web Console"
  // using "Sample" creates a new menu titled "Sample"
  public static final String CATEGORY = "Sample";

  public static final String LABEL = "simple-plugin"; // used for the URL
  public static final String TITLE = "Simple Plugin"; // used for the menu item title (UI)
  public static final String RESOURCE_PREFIX = "/" + LABEL + "/";

  private String pluginHtml;

  @Activate
  protected void activate() {
    this.pluginHtml = getPluginHtml();
  }


  @Override
  protected void renderContent(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.getWriter().write(getPluginHtml());
  }

  // I know, strange, does not override anything...
  // see: https://github.com/justinedelson/felix/blob/df21d1b2eb10543de05727dce890cd6a9a347375/webconsole/src/main/java/org/apache/felix/webconsole/AbstractWebConsolePlugin.java#L230
  // This method is called by this plugin to return a URL of a "resource" file
  public URL getResource(final String path) {
    if (path.startsWith(RESOURCE_PREFIX)) {
      // get resource from this project/bundle "resources" folder
      return this.getClass().getResource(path);
    }
    return null;
  }

  @Override
  public String getLabel() {
    return LABEL;
  }

  @Override
  public String getTitle() {
    return TITLE;
  }

  /**
   * Returns the plugin's HTML as String
   * The HTML in this case comes fom the plugin.html file in the "resources" folder.
   */
  private String getPluginHtml() {
    if (null == this.pluginHtml) {
      this.pluginHtml = readResourceString("plugin.html");
    }
    return this.pluginHtml;
  }

  /**
   * Read resource file from the RESOURCE_PREFIX folder.
   */
  private String readResourceString(String name) {
    return readTemplateFile(RESOURCE_PREFIX + name);
  }
}
