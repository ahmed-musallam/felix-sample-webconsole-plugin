package com.ahmedmusallam.felix.webconsole.plugins.sample;

import com.ahmedmusallam.felix.webconsole.plugins.sample.plugin.SamplePlugin;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class PluginActivator implements BundleActivator {

  private SamplePlugin samplePlugin;

  @Override
  public void start(BundleContext bundleContext) throws Exception {
    SamplePlugin plugin = new SamplePlugin(bundleContext);
    plugin.register();
  }

  @Override
  public void stop(BundleContext bundleContext) throws Exception {
    samplePlugin = null;
  }
}
