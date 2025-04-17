package com.yw.cesium.web.controller;


import com.yw.cesium.application.service.D3TilesService;
import com.yw.cesium.web.dto.TilesPolygonResponseDTO;
import com.yw.cesium.web.dto.TilesJsonRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CesiumController {

    private final D3TilesService d3TilesetService;

    @GetMapping("/cesium/3d/tiles/convexHull")
    public TilesPolygonResponseDTO readTilesJsonReturnConvexHull(TilesJsonRequestDTO dto) {
        return d3TilesetService.readTilesJsonReturnConvexHull(dto);
    }
}
