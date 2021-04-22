package com.siit.rest.mouses.controller;

import com.siit.rest.store.mouse.domain.entity.MouseEntity;
import com.siit.rest.store.mouse.domain.model.MouseDto;
import com.siit.rest.store.mouse.domain.model.MouseDtoUpdateRequest;
import com.siit.rest.store.mouse.mapper.MouseDtoPostRequestToMouseEntityMapper;
import com.siit.rest.store.mouse.mapper.MouseEntityToMouseDto;
import com.siit.rest.store.mouse.repository.MouseRepository;
import com.siit.rest.store.mouse.service.MouseService;

import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MouseControllerTest {
    @InjectMocks
    private MouseService mouseService;

    @Mock
    private MouseRepository mouseRepository;
    @Mock
    private MouseEntityToMouseDto mouseEntityTomouseDto;
    @Mock
    private MouseDtoPostRequestToMouseEntityMapper mouseDtoPostRequestTomouseEntityMapper;


    @Test
    public void getAllmouse_givenNomouse_thenReturnEmptyList(){
        List<MouseEntity> mouseEntities = new ArrayList<>();
        when(mouseRepository.findAll()).thenReturn(mouseEntities);

        var allmouses = mouseService.getAllMouse();

        assertThat(allmouses).isNotNull();
        assertThat(allmouses.isEmpty()).isEqualTo(true);
    }


    @Test
    public void getAllmouses_givenExistingmouses_thenReturnKList(){
        List<MouseEntity> mouseEntities = new ArrayList<>();
        var mouseEntity = MouseEntity.builder()
                .manufacturer("Asus")
                .model(13323)
                .type("Mechanical")
                .build();
        mouseEntities.add(mouseEntity);
        mouseEntities.add(mouseEntity);
        mouseEntities.add(mouseEntity);

        var dto = MouseDto.builder().manufacturer("Asus").model(13323).type("Mechanical").build();

        when(mouseRepository.findAll()).thenReturn(mouseEntities);

        when(mouseEntityTomouseDto.mapEntityToDto(ArgumentMatchers.any())).thenReturn(dto);

        var allmouse = mouseService.getAllMouse();

        assertThat(allmouse).isNotNull();
        assertThat(allmouse.size()).isEqualTo(3);
        assertThat(allmouse.get(0)).isNotNull();
        assertThat(allmouse.get(0).getManufacturer()).isEqualTo(mouseEntity.getManufacturer());
        assertThat(allmouse.get(0).getType()).isEqualTo(mouseEntity.getType());
        assertThat(allmouse.get(0).getModel()).isEqualTo(mouseEntity.getModel());
    }


    @Test
    public void given_existing_mouse_when_updating_then_mouse_is_updated_and_returned(){
        //Given
        int id = 2;
        String manufacturer = "test";
        var input = MouseDtoUpdateRequest.builder()
                .id(id)
                .manufacturer(manufacturer)
                .model(150)
                .type("test2")
                .build();
        var mouseEntity = MouseEntity.builder()
                .model(150)
                .manufacturer(manufacturer)
                .build();
        var mouseEntityMock = Mockito.mock(MouseEntity.class);
        var dto = MouseDto.builder()
                .manufacturer(manufacturer)
                .model(150)
                .build();

        when(mouseRepository.findById(id)).thenReturn(Optional.of(mouseEntityMock));
        when(mouseEntityTomouseDto.mapEntityToDto(ArgumentMatchers.any())).thenReturn(dto);

        // When
        var result = mouseService.updateMouse(input);

        //Then
        Mockito.verify(mouseEntityMock).setManufacturer(manufacturer);
        Mockito.verify(mouseEntityMock).setType(input.getType());
        Mockito.verify(mouseEntityMock).setModel(input.getModel());

        assertThat(result).isNotNull();
        assertThat(result).isSameAs(dto);
    }

    @Test
    public void given_non_existing_mouse_when_updating_then_mouse_not_found_exception_is_thrown(){
        //Given
        int id = 123;
        String manufacturer = "test";
        var input = MouseDtoUpdateRequest.builder()
                .id(id)
                .manufacturer(manufacturer)
                .model(150)
                .type("test2")
                .build();
        when(mouseRepository.findById(id)).thenReturn(Optional.empty());
        // When
        var throwable = catchThrowable(() -> mouseService.updateMouse(input));

        //Then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(Throwable.class);
        assertThat(throwable.getMessage()).isEqualTo("No entry found for given id: " + id);
    }

    @Test
    public void given_existing_mouse_when_updating_then_mouse_is_updated_and_returned_argument_captor_example(){
        //Given
        int id = 123;
        String manufacturer = "test";
        var input = MouseDtoUpdateRequest.builder()
                .id(id)
                .manufacturer(manufacturer)
                .model(150)
                .type("test2")
                .build();
        var mouseEntity = MouseEntity.builder().build();
        var mouseEntityMock = Mockito.spy(MouseEntity.class);
        var dto = MouseDto.builder()
                .manufacturer(manufacturer)
                .model(150)
                .build();

        when(mouseRepository.findById(id)).thenReturn(Optional.of(mouseEntity));
        when(mouseEntityTomouseDto.mapEntityToDto(ArgumentMatchers.any())).thenReturn(dto);

        // When
        var result = mouseService.updateMouse(input);

        //Then
        var argumentC = ArgumentCaptor.forClass(MouseEntity.class);
        verify(mouseEntityTomouseDto).mapEntityToDto(argumentC.capture());

        var mouseEntityUpdated = argumentC.getValue();
        assertThat(mouseEntityUpdated).isNotNull();
        assertThat(mouseEntityUpdated.getManufacturer()).isEqualTo(input.getManufacturer());
        assertThat(mouseEntityUpdated.getModel()).isEqualTo(input.getModel());
        assertThat(mouseEntityUpdated.getType()).isEqualTo(input.getType());


        assertThat(result).isNotNull();
        assertThat(result).isSameAs(dto);
    }
}
