package com.siit.rest.keyboards.controller;

import com.siit.rest.store.keyboard.controller.KeyboardRestController;
import com.siit.rest.store.keyboard.domain.entity.KeyboardEntity;
import com.siit.rest.store.keyboard.domain.model.KeyboardDto;
import com.siit.rest.store.keyboard.domain.model.KeyboardDtoUpdateRequest;
import com.siit.rest.store.keyboard.mapper.KeyboardDtoPostRequestToKeyboardEntityMapper;
import com.siit.rest.store.keyboard.mapper.KeyboardEntityToKeyboardDto;
import com.siit.rest.store.keyboard.repository.KeyboardRepository;
import com.siit.rest.store.keyboard.service.KeyboardService;

import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class KeyboardControllerTest {
    @InjectMocks
    private KeyboardService keyboardService;

    @Mock
    private KeyboardRepository keyboardRepository;
    @Mock
    private KeyboardEntityToKeyboardDto keyboardEntityToKeyboardDto;
    @Mock
    private KeyboardDtoPostRequestToKeyboardEntityMapper keyboardDtoPostRequestToKeyboardEntityMapper;


    @Test
    public void getAllKeyboard_givenNoKeyboard_thenReturnEmptyList(){
        List<KeyboardEntity> keyboardEntities = new ArrayList<>();
        when(keyboardRepository.findAll()).thenReturn(keyboardEntities);

        var allKeyboards = keyboardService.getAllKeyboard();

        assertThat(allKeyboards).isNotNull();
        assertThat(allKeyboards.isEmpty()).isEqualTo(true);
    }


    @Test
    public void getAllKeyboards_givenExistingKeyboards_thenReturnKList(){
        List<KeyboardEntity> keyboardEntities = new ArrayList<>();
        var keyboardEntity = KeyboardEntity.builder()
                .manufacturer("Asus")
                .model(13323)
                .type("Mechanical")
                .build();
        keyboardEntities.add(keyboardEntity);
        keyboardEntities.add(keyboardEntity);
        keyboardEntities.add(keyboardEntity);

        var dto = KeyboardDto.builder().manufacturer("Asus").model(13323).type("Mechanical").build();

        when(keyboardRepository.findAll()).thenReturn(keyboardEntities);

        when(keyboardEntityToKeyboardDto.mapEntityToDto(ArgumentMatchers.any())).thenReturn(dto);

        var allKeyboard = keyboardService.getAllKeyboard();

        assertThat(allKeyboard).isNotNull();
        assertThat(allKeyboard.size()).isEqualTo(3);
        assertThat(allKeyboard.get(0)).isNotNull();
        assertThat(allKeyboard.get(0).getManufacturer()).isEqualTo(keyboardEntity.getManufacturer());
        assertThat(allKeyboard.get(0).getType()).isEqualTo(keyboardEntity.getType());
        assertThat(allKeyboard.get(0).getModel()).isEqualTo(keyboardEntity.getModel());
    }


    @Test
    public void given_existing_keyboard_when_updating_then_keyboard_is_updated_and_returned(){
        //Given
        int id = 2;
        String manufacturer = "test";
        var input = KeyboardDtoUpdateRequest.builder()
                .id(id)
                .manufacturer(manufacturer)
                .model(150)
                .type("test2")
                .build();
        var keyboardEntity = KeyboardEntity.builder()
                .model(150)
                .manufacturer(manufacturer)
                .build();
        var keyboardEntityMock = Mockito.mock(KeyboardEntity.class);
        var dto = KeyboardDto.builder()
                .manufacturer(manufacturer)
                .model(150)
                .build();

        when(keyboardRepository.findById(id)).thenReturn(Optional.of(keyboardEntityMock));
        when(keyboardEntityToKeyboardDto.mapEntityToDto(ArgumentMatchers.any())).thenReturn(dto);

        // When
        var result = keyboardService.updateKeyboard(input);

        //Then
        Mockito.verify(keyboardEntityMock).setManufacturer(manufacturer);
        Mockito.verify(keyboardEntityMock).setType(input.getType());
        Mockito.verify(keyboardEntityMock).setModel(input.getModel());

        assertThat(result).isNotNull();
        assertThat(result).isSameAs(dto);
    }

    @Test
    public void given_non_existing_keyboard_when_updating_then_keyboard_not_found_exception_is_thrown(){
        //Given
        int id = 123;
        String manufacturer = "test";
        var input = KeyboardDtoUpdateRequest.builder()
                .id(id)
                .manufacturer(manufacturer)
                .model(150)
                .type("test2")
                .build();
        when(keyboardRepository.findById(id)).thenReturn(Optional.empty());
        // When
        var throwable = catchThrowable(() -> keyboardService.updateKeyboard(input));

        //Then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(Throwable.class);
        assertThat(throwable.getMessage()).isEqualTo("No entry found for given id: " + id);
    }

    @Test
    public void given_existing_keyboard_when_updating_then_keyboard_is_updated_and_returned_argument_captor_example(){
        //Given
        int id = 123;
        String manufacturer = "test";
        var input = KeyboardDtoUpdateRequest.builder()
                .id(id)
                .manufacturer(manufacturer)
                .model(150)
                .type("test2")
                .build();
        var keyboardEntity = KeyboardEntity.builder().build();
        var keyboardEntityMock = Mockito.spy(KeyboardEntity.class);
        var dto = KeyboardDto.builder()
                .manufacturer(manufacturer)
                .model(150)
                .build();

        when(keyboardRepository.findById(id)).thenReturn(Optional.of(keyboardEntity));
        when(keyboardEntityToKeyboardDto.mapEntityToDto(ArgumentMatchers.any())).thenReturn(dto);

        // When
        var result = keyboardService.updateKeyboard(input);

        //Then
        var argumentC = ArgumentCaptor.forClass(KeyboardEntity.class);
        verify(keyboardEntityToKeyboardDto).mapEntityToDto(argumentC.capture());

        var keyboardEntityUpdated = argumentC.getValue();
        assertThat(keyboardEntityUpdated).isNotNull();
        assertThat(keyboardEntityUpdated.getManufacturer()).isEqualTo(input.getManufacturer());
        assertThat(keyboardEntityUpdated.getModel()).isEqualTo(input.getModel());
        assertThat(keyboardEntityUpdated.getType()).isEqualTo(input.getType());


        assertThat(result).isNotNull();
        assertThat(result).isSameAs(dto);
    }
}
