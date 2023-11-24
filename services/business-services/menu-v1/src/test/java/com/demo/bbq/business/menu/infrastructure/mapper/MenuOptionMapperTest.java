package com.demo.bbq.business.menu.infrastructure.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.demo.bbq.business.menu.domain.model.request.MenuOptionSaveRequest;
import com.demo.bbq.business.menu.domain.model.response.MenuOption;
import com.demo.bbq.business.menu.infrastructure.repository.database.entity.MenuOptionEntity;
import com.demo.bbq.support.util.JsonFileReader;
import com.google.gson.Gson;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MenuOptionMapperTest {

    private MenuOptionMapper mapper = Mappers.getMapper(MenuOptionMapper.class);

    private MenuOptionEntity menuOptionEntity;

    private MenuOption menuOption;

    private MenuOptionSaveRequest menuOptionRequest;

    /**
     * Método que pre carga la data necesaria para ejecutar las pruebas unitarias.
     */
    @BeforeEach
    public void setup() throws IOException {

        menuOptionEntity = JsonFileReader
                .getAnElement("data/model/menuoption/entity/MenuOption.json", MenuOptionEntity.class);

        menuOption = JsonFileReader
            .getAnElement("data/model/menuoption/dto/response/MenuOptionResponse.json", MenuOption.class);

        menuOptionRequest= JsonFileReader
            .getAnElement("data/model/menuoption/dto/request/MenuOptionRequest.json", MenuOptionSaveRequest.class);
    }

    @Test
    public void fromEntityToResponse() {
        String expected = new Gson().toJson(menuOptionEntity);
        String actual = new Gson().toJson(mapper.fromEntityToResponse(menuOptionEntity));

        assertEquals(expected, actual);
    }

    @Test
    public void fromRequestToEntity() {
        MenuOptionEntity currentMenuOptionEntity = menuOptionEntity;
        menuOptionEntity.setId(null);

        String expected = new Gson().toJson(currentMenuOptionEntity);
        String actual = new Gson().toJson(mapper.fromSaveRequestToEntity(menuOptionRequest));

        assertEquals(expected, actual);
    }

}
