package ru.kuznetsov.shop.address.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kuznetsov.shop.represent.dto.AddressDto;

import java.util.Collection;
import java.util.List;

public interface AddressControllerApi {

    @Operation(summary = "Поиск по id", description = "Получение сущности по id записи")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AddressDto.class)
                    ),
                    description = "Адрес"
            ),
            @ApiResponse(responseCode = "204",
                    content = @Content(
                            schema = @Schema(hidden = true)
                    ),
                    description = "Адрес не найден")
    })
    ResponseEntity<AddressDto> getById(
            @Parameter(description = "Уникальный идентификатор адреса для поиска", required = true,
                    schema = @Schema(
                            description = "Id адреса",
                            example = "123",
                            type = "integer",
                            format = "int64"
                    )
            )
            @PathVariable Long id);

    @Operation(summary = "Получение всех сущностей", description = "Получение всех сущностей")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AddressDto[].class)
                    ),
                    description = "Список адресов"
            ),
            @ApiResponse(
                    responseCode = "204",
                    content = @Content(
                            schema = @Schema(hidden = true)
                    ),
                    description = "Адресов не найдено"
            )
    })
    ResponseEntity<List<AddressDto>> getAll();

    @Operation(summary = "Создание адреса", description = "Создание адреса")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AddressDto.class)
                    ),
                    description = "Сущность создана"
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(
                            schema = @Schema(hidden = true)
                    ),
                    description = "Не корректно указаны данные"
            )
    })
    ResponseEntity<AddressDto> create(
            @Parameter(description = "Модель адреса для создания", required = true,
                    schema = @Schema(
                            implementation = AddressDto.class,
                            description = "Адрес"
                    ))
            @RequestBody AddressDto addressDto);

    @Operation(summary = "Создание нескольких адресов", description = "Единовременное создание нескольких сущностей")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AddressDto[].class)
                    ),
                    description = "Сущность создана"
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(
                            schema = @Schema(hidden = true)
                    ),
                    description = "Не корректно указаны данные"
            )
    })
    ResponseEntity<List<AddressDto>> createBatch(
            @Parameter(description = "Список моделей адресов для создания", required = true,
                    schema = @Schema(
                            implementation = AddressDto[].class,
                            description = "Адреса"
                    ))
            @RequestBody Collection<AddressDto> addressDtoCollection);

    @Operation(summary = "Удаление по id", description = "Удаление сущности по id записи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Адрес удалён"),
            @ApiResponse(responseCode = "404", description = "Адрес не найден")
    })
    void delete(
            @Parameter(description = "Уникальный идентификатор адреса для удаления", required = true,
                    schema = @Schema(
                            description = "Id адреса",
                            example = "123",
                            type = "integer",
                            format = "int64"
                    )
            )
            @PathVariable Long id);
}
