package com.pauloedums.service;

import com.pauloedums.controller.ImageKitController;
import com.pauloedums.model.ImageKitResultResponse;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FreeImageService {


    @Inject
    ImageKitController imageKitController;

    public List<ImageKitResultResponse> uploadImages(MultipartFormDataInput input, String folder) {
        imageKitController.initImageKitConfig();

        List<ImageKitResultResponse> results = new ArrayList<>();
        input.getParts().forEach(image -> {
            try {
                FileCreateRequest fileCreateRequest = new FileCreateRequest(image.getBody().readAllBytes(), image.getFileName());
                fileCreateRequest.setFolder(folder);
                Result result = ImageKit.getInstance().upload(fileCreateRequest);
                ImageKitResultResponse imageKitResultResponse = ImageKitResultResponse.builder()
                        .name(result.getName())
                        .size(result.getSize())
                        .url(result.getUrl())
                        .width(result.getWidth())
                        .height(result.getHeight())
                        .fileType(result.getFileType())
                        .isPrivateFile(result.isPrivateFile())
                        .thumbnail(result.getThumbnail())
                        .filePath(result.getFilePath())
                        .fileId(result.getFileId())
                        .build();
                results.add(imageKitResultResponse);


            } catch (IOException | ForbiddenException | TooManyRequestsException | InternalServerException |
                     UnauthorizedException | BadRequestException | UnknownException e) {
                throw new RuntimeException(e);
            }
        });

        return results;
    }

    public Result deleteImage(String fileId) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException {
        imageKitController.initImageKitConfig();
        return ImageKit.getInstance().deleteFile(fileId);
    }

    public String getFile(String fileId) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException {
        imageKitController.initImageKitConfig();
        return ImageKit.getInstance().getFileDetail(fileId).getResponseMetaData().getRaw();
    }
}
