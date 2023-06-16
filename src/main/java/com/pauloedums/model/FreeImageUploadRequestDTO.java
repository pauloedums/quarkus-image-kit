package com.pauloedums.model;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import lombok.Getter;
import lombok.Setter;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import java.io.InputStream;

@Getter
@Setter
public class FreeImageUploadRequestDTO {
    @FormParam("file")
    @PartType(MediaType.MULTIPART_FORM_DATA)
    private InputStream image;

}
