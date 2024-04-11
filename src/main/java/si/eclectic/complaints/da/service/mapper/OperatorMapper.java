package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.Operator;
import si.eclectic.complaints.da.service.dto.OperatorDTO;

/**
 * Mapper for the entity {@link Operator} and its DTO {@link OperatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface OperatorMapper extends EntityMapper<OperatorDTO, Operator> {}
