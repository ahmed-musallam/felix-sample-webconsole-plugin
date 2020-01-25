package com.ahmedmusallam.felix.webconsole.plugins.sample.plugin;

import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.felix.webconsole.AbstractWebConsolePlugin;
import org.apache.felix.webconsole.WebConsoleConstants;
import org.osgi.framework.BundleContext;

public class SamplePlugin extends AbstractWebConsolePlugin {

  // Can be any of the existing ones: "Main", "OSGI", "Sling", "Status", "Web Console"
  // using "Sample" creates a new menu titled "Sample"
  public static final String CATEGORY = "Sample";

  public static final String LABEL = "simple-plugin"; // used for the URL
  public static final String TITLE = "Simple Plugin";

  protected BundleContext bundleContext;

  public SamplePlugin(BundleContext bundleContext) {
    this.bundleContext = bundleContext;
  }

  @Override
  protected void renderContent(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    final String plugin = readTemplateFile("/" + getLabel() + "/plugin.html");
    response.getWriter().write(plugin);
  }

  protected Dictionary<String, Object> createProps() {
    final Dictionary<String, Object> props = new Hashtable<>();
    props.put(WebConsoleConstants.PLUGIN_LABEL, getLabel());
    props.put(WebConsoleConstants.PLUGIN_CATEGORY, CATEGORY);
    return props;
  }

  public void register() {
    bundleContext.registerService(Servlet.class.getName(), this, createProps());
  }

  public URL getResource(final String path) {
    String prefix = "/" + getLabel() + "/";
    if (path.startsWith(prefix)) {
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
}
