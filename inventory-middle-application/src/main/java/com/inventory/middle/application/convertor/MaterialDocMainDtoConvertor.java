package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.MaterialDocMain;
import com.inventory.middle.domain.model.types.MaterialDocMainId;
import com.inventory.middle.client.dto.MaterialDocMainDto;
import com.inventory.middle.client.dto.command.MaterialDocMainCommand;
import com.inventory.middle.infra.persistence.entity.MaterialDocMainDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证主表DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Mapper(componentModel = "spring")
public interface MaterialDocMainDtoConvertor {

    /**
     * dto --> entity
     * @param materialdocmainDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    MaterialDocMain toMaterialDocMain(final MaterialDocMainDto materialdocmainDto);

    /**
     * dto --> entity
     * @param materialdocmainCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MaterialDocMain toMaterialDocMain(final MaterialDocMainCommand materialdocmainCommand);

    /**
     * entity --> dto
     * @param materialdocmain
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MaterialDocMainDto fromMaterialDocMain(final MaterialDocMain materialdocmain);

    /**
     * cmd --> dto
     * @param materialdocmainCommand
     * @return
     */
      MaterialDocMainDto fromMaterialDocMainCommand(final MaterialDocMainCommand materialdocmainCommand);

    /**
     * do --> dto
     * @param materialdocmainDo
     * @return
     */
      MaterialDocMainDto fromDo(final MaterialDocMainDo materialdocmainDo);

      default MaterialDocMainId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new MaterialDocMainId(id);
      }

      default Long id2id(MaterialDocMainId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}