package de.linzn.pelltech.webApi;

import de.linzn.pelltech.PelltechPlugin;
import de.linzn.webapi.WebApiPlugin;
import de.linzn.webapi.modules.WebModule;
import de.stem.stemSystem.modules.pluginModule.STEMPlugin;

public class WebApiHandler {

    private final STEMPlugin stemPlugin;
    private final WebModule stemWebModule;

    public WebApiHandler(PelltechPlugin pelltechPlugin) {
        this.stemPlugin = pelltechPlugin;
        stemWebModule = new WebModule("pelltech");
        stemWebModule.registerSubCallHandler(new PelltechWebApi(pelltechPlugin), "data");
        WebApiPlugin.webApiPlugin.getWebServer().enableCallModule(stemWebModule);
    }
}
