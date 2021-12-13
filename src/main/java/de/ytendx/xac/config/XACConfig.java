package de.ytendx.xac.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class XACConfig {

    private boolean silent;
    private boolean logViolationsInConsole;
    private boolean isTestServer;
    private String notifyPermission;
    private String kickMessage;
    private boolean kickCheater;
    private boolean ignoreExperimentalFlags;
    private int violationResetTicks;

    public static XACConfig getDefault(){
        return new XACConfig(true, true, true, "xac.notify",
                "§cYou were kicked cause of cheating.", true, false, 20*60*2
        );
    }

    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    public static XACConfig fromString(String content){
        return new Gson().fromJson(content, XACConfig.class);
    }

}
