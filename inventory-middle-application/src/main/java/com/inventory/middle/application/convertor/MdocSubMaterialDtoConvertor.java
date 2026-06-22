package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.MdocSubMaterial;
import com.inventory.middle.domain.model.types.MdocSubMaterialId;
import com.inventory.middle.client.dto.MdocSubMaterialDto;
import com.inventory.middle.client.dto.command.MdocSubMaterialCommand;
import com.inventory.middle.infra.persistence.entity.MdocSubMaterialDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证子表-物料信息DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Mapper(componentModel = "spring")
public interface MdocSubMaterialDtoConvertor {

    /**
     * dto --> entity
     * @param mdocsubmaterialDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    MdocSubMaterial toMdocSubMaterial(final MdocSubMaterialDto mdocsubmaterialDto);

    /**
     * dto --> entity
     * @param mdocsubmaterialCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MdocSubMaterial toMdocSubMaterial(final MdocSubMaterialCommand mdocsubmaterialCommand);

    /**
     * entity --> dto
     * @param mdocsubmaterial
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MdocSubMaterialDto fromMdocSubMaterial(final MdocSubMaterial mdocsubmaterial);

    /**
     * cmd --> dto
     * @param mdocsubmaterialCommand
     * @return
     */
      MdocSubMaterialDto fromMdocSubMaterialCommand(final MdocSubMaterialCommand mdocsubmaterialCommand);

    /**
     * do --> dto
     * @param mdocsubmaterialDo
     * @return
     */
      MdocSubMaterialDto fromDo(final MdocSubMaterialDo mdocsubmaterialDo);

      default MdocSubMaterialId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new MdocSubMaterialId(id);
      }

      default Long id2id(MdocSubMaterialId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}