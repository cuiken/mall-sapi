package com.cplatform.sapi.rest;

import com.cplatform.sapi.DTO.SysRegionDTO;
import com.cplatform.sapi.DTO.TMemberAddressesDTO;
import com.cplatform.sapi.DTO.UserAddressDTO;
import com.cplatform.sapi.entity.SysRegion;
import com.cplatform.sapi.entity.TMemberAddress;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.service.TMemberAddressService;
import com.cplatform.sapi.util.MediaTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-15
 * Time: 下午2:35
 */
@Controller
@RequestMapping(value = "/api/v1/address")
public class AddressController {
    private TMemberAddressService memberAddressService;

    @RequestMapping(value = "user", method = RequestMethod.GET)
    @ResponseBody
    public TMemberAddressesDTO userAddress(@RequestParam(value = "U_ID") Long userId) {

        List<TMemberAddress> addresses = memberAddressService.getAddressByUser(userId);

        return memberAddressService.convertToDTO(addresses);
    }

    @RequestMapping(value = "getAddressInfo", method = RequestMethod.GET)
    @ResponseBody
    public SysRegionDTO getAddressInfo(Long level, Long id) {
        level = (level == null ? 0L : level);
        List<SysRegion> regions = memberAddressService.getRegionByLevel(level, id);
        SysRegionDTO dto = new SysRegionDTO();
        dto.setData(BeanMapper.mapList(regions, SysRegionDTO.Data.class));
        return dto;
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteAddress(@PathVariable("id") Long id) {

        memberAddressService.deleteAddress(id);
        return "{\"FLAG\":\"0\",\"MSG\":\"操作成功\"}";
    }

    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST, consumes = MediaTypes.JSON)
    @ResponseBody
    public String saveAddress(@RequestBody UserAddressDTO dto) {

        memberAddressService.saveOrUpdateAddress(dto);

        return "{\"FLAG\":\"0\",\"MSG\":\"操作成功\"}";
    }

    @RequestMapping(value = "setDefault")
    @ResponseBody
    public String setDefault(@RequestParam(value = "id") Long id, @RequestParam(value = "userId") Long userId) {

        memberAddressService.setDefault(id, userId);

        return "{\"FLAG\":\"0\",\"MSG\":\"操作成功\"}";
    }

    @Autowired
    public void setMemberAddressService(TMemberAddressService memberAddressService) {
        this.memberAddressService = memberAddressService;
    }
}
