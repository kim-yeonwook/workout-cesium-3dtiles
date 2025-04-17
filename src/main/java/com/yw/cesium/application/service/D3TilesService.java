package com.yw.cesium.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yw.cesium.domains.TilesHandler;
import com.yw.cesium.infrastructure.exception.BadRequestException;
import com.yw.cesium.infrastructure.exception.InternalServerException;
import com.yw.cesium.infrastructure.utils.FileUtils;
import com.yw.cesium.web.dto.TilesJsonRequestDTO;
import com.yw.cesium.web.dto.TilesPolygonResponseDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class D3TilesService {

    public TilesPolygonResponseDTO readTilesJsonReturnConvexHull(TilesJsonRequestDTO dto) {
        try {
            File file = new File(dto.getTilesPath());
            if (!file.exists()) {
                throw new BadRequestException("Tileset.json 파일을 찾을수 업습니다. FILE: " + file);
            }

            String json = FileUtils.readFile(file);
            TilesHandler handler = new TilesHandler(json);

            return new TilesPolygonResponseDTO(handler.getPolygonConvexHull(4326).toText());
        } catch (JsonProcessingException jpe) {
            throw new InternalServerException("3D Tileset 의 객체 변환에 실패 했습니다. Tileset.json 파일의 형식 확인 및 로직 확인이 필요 합니다.", jpe);
        } catch (IOException ioe) {
            throw new InternalServerException("Tileset 파일을 받아 오는데 실패 했습니다. 서버 상태 확인 및 잘못된 서버에 요청 하는지 확인이 필요 합니다.", ioe);
        }
    }
}