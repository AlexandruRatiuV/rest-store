package com.siit.rest.monitors.controller;


import com.siit.rest.store.monitor.domain.entity.MonitorEntity;
import com.siit.rest.store.monitor.domain.model.MonitorDto;
import com.siit.rest.store.monitor.domain.model.MonitorDtoUpdateRequest;
import com.siit.rest.store.monitor.mapper.MonitorDtoPostRequestToMonitorEntityMapper;
import com.siit.rest.store.monitor.mapper.MonitorEntityToMonitorDto;
import com.siit.rest.store.monitor.repository.MonitorRepository;
import com.siit.rest.store.monitor.service.MonitorService;

import com.siit.rest.store.mouse.domain.model.MouseDto;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MonitorsControllerTest {
    @InjectMocks
    private MonitorService monitorService;

    @Mock
    private MonitorRepository monitorRepository;
    @Mock
    private MonitorEntityToMonitorDto monitorEntityTomonitorDto;
    @Mock
    private MonitorDtoPostRequestToMonitorEntityMapper monitorDtoPostRequestTomonitorEntityMapper;


    @Test
    public void getAllmonitor_givenNomonitor_thenReturnEmptyList(){
        List<MonitorEntity> monitorEntities = new ArrayList<>();
        when(monitorRepository.findAll()).thenReturn(monitorEntities);

        var allmonitors = monitorService.getAllMonitor();

        assertThat(allmonitors).isNotNull();
        assertThat(allmonitors.isEmpty()).isEqualTo(true);
    }


    @Test
    public void getAllmonitors_givenExistingmonitors_thenReturnKList(){
        List<MonitorEntity> monitorEntities = new ArrayList<>();
        var monitorEntity = MonitorEntity.builder()
                .manufacturer("Asus")
                .model(13323)
                .type("Mechanical")
                .build();
        monitorEntities.add(monitorEntity);
        monitorEntities.add(monitorEntity);
        monitorEntities.add(monitorEntity);

        var dto = MonitorDto.builder().manufacturer("Asus").model(13323).type("Mechanical").build();

        when(monitorRepository.findAll()).thenReturn(monitorEntities);

        when(monitorEntityTomonitorDto.mapEntityToDto(ArgumentMatchers.any())).thenReturn(dto);

        var allmonitor = monitorService.getAllMonitor();

        assertThat(allmonitor).isNotNull();
        assertThat(allmonitor.size()).isEqualTo(3);
        assertThat(allmonitor.get(0)).isNotNull();
        assertThat(allmonitor.get(0).getManufacturer()).isEqualTo(monitorEntity.getManufacturer());
        assertThat(allmonitor.get(0).getType()).isEqualTo(monitorEntity.getType());
        assertThat(allmonitor.get(0).getModel()).isEqualTo(monitorEntity.getModel());
    }


    @Test
    public void given_existing_monitor_when_updating_then_monitor_is_updated_and_returned(){
        //Given
        int id = 2;
        String manufacturer = "test";
        var input = MonitorDtoUpdateRequest.builder()
                .id(id)
                .manufacturer(manufacturer)
                .model(150)
                .type("test2")
                .build();
        var monitorEntity = MonitorEntity.builder()
                .model(150)
                .manufacturer(manufacturer)
                .build();
        var monitorEntityMock = Mockito.mock(MonitorEntity.class);
        var dto = MonitorDto.builder()
                .manufacturer(manufacturer)
                .model(150)
                .build();

        when(monitorRepository.findById(id)).thenReturn(Optional.of(monitorEntityMock));
        when(monitorEntityTomonitorDto.mapEntityToDto(ArgumentMatchers.any())).thenReturn(dto);

        // When
        var result = monitorService.updateMonitor(input);

        //Then
        Mockito.verify(monitorEntityMock).setManufacturer(manufacturer);
        Mockito.verify(monitorEntityMock).setType(input.getType());
        Mockito.verify(monitorEntityMock).setModel(input.getModel());

        assertThat(result).isNotNull();
        assertThat(result).isSameAs(dto);
    }

    @Test
    public void given_non_existing_monitor_when_updating_then_monitor_not_found_exception_is_thrown(){
        //Given
        int id = 123;
        String manufacturer = "test";
        var input = MonitorDtoUpdateRequest.builder()
                .id(id)
                .manufacturer(manufacturer)
                .model(150)
                .type("test2")
                .build();
        when(monitorRepository.findById(id)).thenReturn(Optional.empty());
        // When
        var throwable = catchThrowable(() -> monitorService.updateMonitor(input));

        //Then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(Throwable.class);
        assertThat(throwable.getMessage()).isEqualTo("No entry found for given id: " + id);
    }

    @Test
    public void given_existing_monitor_when_updating_then_monitor_is_updated_and_returned_argument_captor_example(){
        //Given
        int id = 123;
        String manufacturer = "test";
        var input = MonitorDtoUpdateRequest.builder()
                .id(id)
                .manufacturer(manufacturer)
                .model(150)
                .type("test2")
                .build();
        var monitorEntity = MonitorEntity.builder().build();
        var monitorEntityMock = Mockito.spy(MonitorEntity.class);
        var dto = MonitorDto.builder()
                .manufacturer(manufacturer)
                .model(150)
                .build();

        when(monitorRepository.findById(id)).thenReturn(Optional.of(monitorEntity));
        when(monitorEntityTomonitorDto.mapEntityToDto(ArgumentMatchers.any())).thenReturn(dto);

        // When
        var result = monitorService.updateMonitor(input);

        //Then
        var argumentC = ArgumentCaptor.forClass(MonitorEntity.class);
        verify(monitorEntityTomonitorDto).mapEntityToDto(argumentC.capture());

        var monitorEntityUpdated = argumentC.getValue();
        assertThat(monitorEntityUpdated).isNotNull();
        assertThat(monitorEntityUpdated.getManufacturer()).isEqualTo(input.getManufacturer());
        assertThat(monitorEntityUpdated.getModel()).isEqualTo(input.getModel());
        assertThat(monitorEntityUpdated.getType()).isEqualTo(input.getType());


        assertThat(result).isNotNull();
        assertThat(result).isSameAs(dto);
    }
}
