package com.pauloedums.controller;

import com.pauloedums.model.ImageKitResultResponse;
import com.pauloedums.service.FreeImageService;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.exceptions.BadRequestException;
import io.imagekit.sdk.exceptions.ForbiddenException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("")
public class FreeImageController {

    @Inject
    FreeImageService freeImageService;

    @POST
    @Path("/upload/{folder}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(@MultipartForm @NotNull MultipartFormDataInput input, @PathParam("folder") String folder){
        List<ImageKitResultResponse> results = freeImageService.uploadImages(input, folder);
        return Response.ok(results).build();
    }

    @DELETE
    @Path("/delete/{fileId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteImage(@PathParam("fileId") String fileId) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException {
        return Response.ok(freeImageService.deleteImage(fileId)).build();
    }

    @GET
    @Path("/get/{fileId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFile(@PathParam("fileId") String fileId) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException {
        return Response.ok(freeImageService.getFile(fileId)).build();
    }


}
