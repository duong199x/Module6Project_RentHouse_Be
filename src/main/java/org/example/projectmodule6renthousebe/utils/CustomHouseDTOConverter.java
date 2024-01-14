package org.example.projectmodule6renthousebe.utils;

import org.example.projectmodule6renthousebe.dto.HouseDTO;
import org.example.projectmodule6renthousebe.dto.UserDTO;
import org.example.projectmodule6renthousebe.model.House;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

public class CustomHouseDTOConverter implements Converter<House, HouseDTO> {

    @Override
    public HouseDTO convert(MappingContext<House, HouseDTO> mappingContext) {
        ModelMapper modelMapper = new ModelMapper();
        House house = mappingContext.getSource();
        HouseDTO houseDTO = modelMapper.map(house,HouseDTO.class);
        houseDTO.setUserDTO(modelMapper.map(house.getUser(), UserDTO.class));

        return houseDTO;
    }
}
