package com.pauloedums.controller;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Produces
public class ImageKitController {

    @ConfigProperty(name = "imagekit.url")
    private String imageKitUrlEndpoint;
    @ConfigProperty(name = "imagekit.api")
    private String imageKitAPIPrivateKey;
    @ConfigProperty(name = "imagekit.public")
    private String imageKitPublicKey;

    public void initImageKitConfig(){
        ImageKit imageKit = ImageKit.getInstance();
        Configuration config = new Configuration(
                imageKitPublicKey,
                imageKitAPIPrivateKey,
                imageKitUrlEndpoint
        );
        imageKit.setConfig(config);
    }
}
