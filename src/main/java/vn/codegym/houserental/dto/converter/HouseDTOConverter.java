package vn.codegym.houserental.dto.converter;

import org.modelmapper.Converter;
import vn.codegym.houserental.dto.HouseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import vn.codegym.houserental.dto.UserDTO;
import vn.codegym.houserental.model.House;

public class HouseDTOConverter implements Converter<House, HouseDTO> {

    @Override
    public HouseDTO convert(MappingContext<House, HouseDTO> mappingContext) {
        ModelMapper modelMapper = new ModelMapper();
        House house = mappingContext.getSource();
        HouseDTO houseDTO = modelMapper.map(house,HouseDTO.class);
        houseDTO.setUserDTO(modelMapper.map(house.getUser(), UserDTO.class));

        return houseDTO;
    }
}
