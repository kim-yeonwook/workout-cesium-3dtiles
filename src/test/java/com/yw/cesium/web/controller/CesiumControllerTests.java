package com.yw.cesium.web.controller;

import com.yw.cesium.application.service.D3TilesetService;
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
@Import(D3TilesetService.class)
public class CesiumControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("3D_타일셋_CONVEX_HULL")
    void readTilesetJsonReturnConvexHull() throws Exception {
        mockMvc.perform(get("/cesium/3d/tiles/convexHull")
                        .param("tilesetPath", "src/test/resources/tileset.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.polygon").value("POLYGON ((129.18348498806824 35.55515826843067, 129.18347791506264 35.55516360978402, 129.1832798509624 35.55540341245406, 129.18327858293455 35.55540978083982, 129.18384628801118 35.55572290077811, 129.1838502987925 35.55571804517329, 129.18404836196777 35.55547824157876, 129.18405360640142 35.555471891491976, 129.1834859022883 35.55515877251672, 129.18348498806824 35.55515826843067))"));
    }
}
