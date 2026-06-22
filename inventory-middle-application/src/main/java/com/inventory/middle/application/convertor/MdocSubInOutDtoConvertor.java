package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.MdocSubInOut;
import com.inventory.middle.domain.model.types.MdocSubInOutId;
import com.inventory.middle.client.dto.MdocSubInOutDto;
import com.inventory.middle.client.dto.command.MdocSubInOutCommand;
import com.inventory.middle.infra.persistence.entity.MdocSubInOutDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证子表-出入库信息DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Mapper(componentModel = "spring")
public interface MdocSubInOutDtoConvertor {

    /**
     * dto --> entity
     * @param mdocsubinoutDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    MdocSubInOut toMdocSubInOut(final MdocSubInOutDto mdocsubinoutDto);

    /**
     * dto --> entity
     * @param mdocsubinoutCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MdocSubInOut toMdocSubInOut(final MdocSubInOutCommand mdocsubinoutCommand);

    /**
     * entity --> dto
     * @param mdocsubinout
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MdocSubInOutDto fromMdocSubInOut(final MdocSubInOut mdocsubinout);

    /**
     * cmd --> dto
     * @param mdocsubinoutCommand
     * @return
     */
      MdocSubInOutDto fromMdocSubInOutCommand(final MdocSubInOutCommand mdocsubinoutCommand);

    /**
     * do --> dto
     * @param mdocsubinoutDo
     * @return
     */
      MdocSubInOutDto fromDo(final MdocSubInOutDo mdocsubinoutDo);

      default MdocSubInOutId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new MdocSubInOutId(id);
      }

      default Long id2id(MdocSubInOutId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}