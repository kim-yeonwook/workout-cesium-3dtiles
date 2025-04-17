package com.yw.cesium.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TilesJsonRequestDTO {

    @NotBlank(message = "tilesetPath 는 필수 값 입니다. ")
    @Pattern(regexp = ".*/tileset.json$", message = "파일명 이 tileset.json 이 아닙니다. 파일 확인이 필요 합니다.")
    private String tilesPath;
}
