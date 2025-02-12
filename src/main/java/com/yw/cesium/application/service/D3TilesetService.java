package com.yw.cesium.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yw.cesium.domains.Tileset3DHandler;
import com.yw.cesium.infrastructure.exception.BadRequestException;
import com.yw.cesium.infrastructure.exception.InternalServerException;
import com.yw.cesium.infrastructure.utils.FileUtils;
import com.yw.cesium.web.dto.TilesetPolygonResDTO;
import com.yw.cesium.web.dto.TilesetReqDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

@Service
public class D3TilesetService {

    public TilesetPolygonResDTO readTilesetJsonReturnConvexHull(TilesetReqDTO dto) {
        try {
            File file = new File(dto.getTilesetPath());
            if (!file.exists()) {
                throw new BadRequestException("Tileset.json 파일을 찾을수 업습니다. , " + dto.getTilesetPath());
            }

            String json = FileUtils.readFile(file);
            Tileset3DHandler handler = new Tileset3DHandler(json);

            return new TilesetPolygonResDTO(handler.getPolygonConvexHull(4326).toText());
        } catch (MalformedURLException murle) {
            throw new BadRequestException("URL 형식이 아닙니다. URL 형식을 확인 하십시요. " + dto.getTilesetPath());
        } catch (JsonProcessingException jpe) {
            throw new InternalServerException("3D Tileset 의 객체 변환에 실패 했습니다. Tileset.json 파일의 형식 확인 및 로직 확인이 필요 합니다.");
        } catch (IOException ioe) {
            throw new InternalServerException("Tileset 파일을 받아 오는데 실패 했습니다. 서버 상태 확인 및 잘못된 서버에 요청 하는지 확인이 필요 합니다.", ioe);
        }
    }
}
