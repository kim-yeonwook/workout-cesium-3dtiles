package com.yw.cesium.web.controller;

import com.yw.cesium.application.service.D3TilesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CesiumController.class)
@Import(D3TilesService.class)
public class CesiumControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("3D_타일셋_BOX_CONVEX_HULL")
    void readBoxTilesJsonReturnConvexHull() throws Exception {
        mockMvc.perform(get("/cesium/3d/tiles/convexHull")
                        .param("tilesPath", "src/test/resources/box/tileset.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.polygon").value("POLYGON ((129.18348498806824 35.55515826843067, 129.18347791506264 35.55516360978402, 129.1832798509624 35.55540341245406, 129.18327858293455 35.55540978083982, 129.18384628801118 35.55572290077811, 129.1838502987925 35.55571804517329, 129.18404836196777 35.55547824157876, 129.18405360640142 35.555471891491976, 129.1834859022883 35.55515877251672, 129.18348498806824 35.55515826843067))"));
    }

    @Test
    @DisplayName("3D_타일셋_SPHERE_CONVEX_HULL")
    void readSphereTilesJsonReturnConvexHull() throws Exception {
        mockMvc.perform(get("/cesium/3d/tiles/convexHull")
                        .param("tilesPath", "src/test/resources/sphere/tileset.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.polygon").value("POLYGON ((126.47403762385214 33.479162441358895, 126.47289993503347 33.479551223476726, 126.47190698884516 33.480822925322734, 126.47124866171096 33.48185403715651, 126.47038517767346 33.48413469003787, 126.47007645586265 33.48510135136782, 126.47085908625293 33.48559084717926, 126.4719178814248 33.48522902055235, 126.47260761510755 33.48428876410873, 126.47459377392512 33.48087121965932, 126.47487867612664 33.479688354560686, 126.47403762385214 33.479162441358895))"));
    }

    @Test
    @DisplayName("3D_타일셋_REGION_CONVEX_HULL")
    void readRegionTilesJsonReturnConvexHull() throws Exception {
        mockMvc.perform(get("/cesium/3d/tiles/convexHull")
                        .param("tilesPath", "src/test/resources/region/tileset.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.polygon").value("POLYGON ((127.12596540067814 37.37509158095596, 127.09305785088456 37.37528110838554, 127.07527198543572 37.37637221604535, 127.07489254684685 37.38642084334332, 127.07489254684685 37.39540707775488, 127.08048545493519 37.41492115908572, 127.09544682816849 37.414989364431676, 127.11106305318962 37.414990710441835, 127.12541005411836 37.414616378029166, 127.14082984658849 37.40667348736102, 127.14073141599802 37.38380048224883, 127.14031930251797 37.37509158095596, 127.12596540067814 37.37509158095596))"));
    }
}
