package com.pauloedums.controller;

import com.pauloedums.model.FreeImageUploadResponseDTO;
import com.pauloedums.model.FreeImageUploadRequestDTO;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.exceptions.BadRequestException;
import io.imagekit.sdk.exceptions.ForbiddenException;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jetbrains.annotations.NotNull;

import java.io.*;

@Produces(MediaType.APPLICATION_JSON)
@Path("")
public class FreeImageController {

    @ConfigProperty(name = "imagekit.url")
    private String imageKitUrlEndpoint;
    @ConfigProperty(name = "imagekit.api")
    private String imageKitAPIPrivateKey;
    @ConfigProperty(name = "imagekit.public")
    private String imageKitPublicKey;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(@MultipartForm @NotNull FreeImageUploadRequestDTO freeImageUploadRequestDTO) throws IOException {
        initImageKitConfig();

        try {
            FileCreateRequest fileCreateRequest = new FileCreateRequest(freeImageUploadRequestDTO.getImage().readAllBytes(), "temp");

            try {
                Result result = ImageKit.getInstance().upload(fileCreateRequest);
                FreeImageUploadResponseDTO freeImageUploadResponseDTO = FreeImageUploadResponseDTO
                        .builder()
                        .url(result.getUrl())
                        .fileId(result.getFileId())
                        .build();
                return Response.ok(freeImageUploadResponseDTO).build();
            } catch (ForbiddenException e) {
                throw new RuntimeException(e);
            } catch (TooManyRequestsException e) {
                throw new RuntimeException(e);
            } catch (InternalServerException e) {
                throw new RuntimeException(e);
            } catch (UnauthorizedException e) {
                throw new RuntimeException(e);
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            } catch (UnknownException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @DELETE
    @Path("/delete/{fileId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(@PathParam("fileId") String fileId) throws IOException, ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException {
        initImageKitConfig();
        Result result= ImageKit.getInstance().deleteFile(fileId);
        return Response.ok(result).build();
    }

    @GET
    @Path("/get/{fileId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFile(@PathParam("fileId") String fileId) throws IOException, ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException {
        initImageKitConfig();
        Result result= ImageKit.getInstance().getFileDetail(fileId);
        return Response.ok(result.getResponseMetaData().getRaw()).build();
    }

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
