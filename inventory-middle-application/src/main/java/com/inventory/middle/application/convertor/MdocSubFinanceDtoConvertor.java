package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.MdocSubFinance;
import com.inventory.middle.domain.model.types.MdocSubFinanceId;
import com.inventory.middle.client.dto.MdocSubFinanceDto;
import com.inventory.middle.client.dto.command.MdocSubFinanceCommand;
import com.inventory.middle.infra.persistence.entity.MdocSubFinanceDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证-标签-财务DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Mapper(componentModel = "spring")
public interface MdocSubFinanceDtoConvertor {

    /**
     * dto --> entity
     * @param mdocsubfinanceDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    MdocSubFinance toMdocSubFinance(final MdocSubFinanceDto mdocsubfinanceDto);

    /**
     * dto --> entity
     * @param mdocsubfinanceCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MdocSubFinance toMdocSubFinance(final MdocSubFinanceCommand mdocsubfinanceCommand);

    /**
     * entity --> dto
     * @param mdocsubfinance
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     MdocSubFinanceDto fromMdocSubFinance(final MdocSubFinance mdocsubfinance);

    /**
     * cmd --> dto
     * @param mdocsubfinanceCommand
     * @return
     */
      MdocSubFinanceDto fromMdocSubFinanceCommand(final MdocSubFinanceCommand mdocsubfinanceCommand);

    /**
     * do --> dto
     * @param mdocsubfinanceDo
     * @return
     */
      MdocSubFinanceDto fromDo(final MdocSubFinanceDo mdocsubfinanceDo);

      default MdocSubFinanceId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new MdocSubFinanceId(id);
      }

      default Long id2id(MdocSubFinanceId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}