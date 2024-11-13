package com.pauloedums.model;

import lombok.Builder;

@Builder
public record ImageKitResultResponse(
        String fileId,
        String name,
        String url,
        String thumbnail,
        long size,
        int height,
        int width,
        String filePath,
        Boolean isPrivateFile,
        String fileType
) {
}
