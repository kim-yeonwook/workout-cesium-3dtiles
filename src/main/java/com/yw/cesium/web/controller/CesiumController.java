package com.yw.cesium.web.controller;


import com.yw.cesium.application.service.D3TilesetService;
import com.yw.cesium.web.dto.TilesetPolygonResDTO;
import com.yw.cesium.web.dto.TilesetReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CesiumController {

    private final D3TilesetService d3TilesetService;

    @GetMapping("/cesium/3d/tiles/convexHull")
    public TilesetPolygonResDTO readTilesetJsonReturnConvexHull(TilesetReqDTO dto) {
        return d3TilesetService.readTilesetJsonReturnConvexHull(dto);
    }
}
