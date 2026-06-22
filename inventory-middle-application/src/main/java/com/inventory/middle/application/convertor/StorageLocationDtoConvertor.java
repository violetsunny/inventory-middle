package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.StorageLocation;
import com.inventory.middle.domain.model.types.StorageLocationId;
import com.inventory.middle.client.dto.StorageLocationDto;
import com.inventory.middle.client.dto.command.StorageLocationCommand;
import com.inventory.middle.infra.persistence.entity.StorageLocationDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 存储地点表DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Mapper(componentModel = "spring")
public interface StorageLocationDtoConvertor {

    /**
     * dto --> entity
     * @param storagelocationDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    StorageLocation toStorageLocation(final StorageLocationDto storagelocationDto);

    /**
     * dto --> entity
     * @param storagelocationCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     StorageLocation toStorageLocation(final StorageLocationCommand storagelocationCommand);

    /**
     * entity --> dto
     * @param storagelocation
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     StorageLocationDto fromStorageLocation(final StorageLocation storagelocation);

    /**
     * cmd --> dto
     * @param storagelocationCommand
     * @return
     */
      StorageLocationDto fromStorageLocationCommand(final StorageLocationCommand storagelocationCommand);

    /**
     * do --> dto
     * @param storagelocationDo
     * @return
     */
      StorageLocationDto fromDo(final StorageLocationDo storagelocationDo);

      default StorageLocationId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new StorageLocationId(id);
      }

      default Long id2id(StorageLocationId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}