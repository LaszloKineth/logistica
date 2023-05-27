package kinela.logistic.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import kinela.logistic.dto.SectionDTO;
import kinela.logistic.model.Section;

@Mapper(componentModel = "spring")
public interface SectionMapper {

	SectionDTO sectionToDto(Section section);
	Section dtoToSection(SectionDTO sectionDto);
	
	List<SectionDTO> sectionsToDto(List<Section> sections);
	List<Section> dotsToSections(List<SectionDTO> sectionsDTOs);
}