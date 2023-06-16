package com.pauloedums.model;

import io.imagekit.sdk.models.results.Result;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeImageUploadResponseDTO {

    public String url;
    public String fileId;
}
