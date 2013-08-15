package com.cplatform.sapi.rest;

import com.cplatform.sapi.DTO.EcKillDTO;
import com.cplatform.sapi.entity.product.ItemSale;
import com.cplatform.sapi.entity.product.SysFileImg;
import com.cplatform.sapi.entity.product.SysFileImgThumb;
import com.cplatform.sapi.entity.product.TSysType;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.service.product.ItemSaleService;
import com.cplatform.sapi.service.product.TSysTypeService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-13
 * Time: 下午1:12
 */
@Controller
@RequestMapping(value = "/api/v1/product")
public class ProductController {

    private Page<ItemSale> page = new Page<ItemSale>(100);
    private TSysTypeService typeService;
    private ItemSaleService itemSaleService;


    @RequestMapping(value = "category", method = RequestMethod.GET)
    @ResponseBody
    public List<TSysType> list() {
        return typeService.getByChannel(2L);

    }

    @RequestMapping(value = "spike", method = RequestMethod.GET)
    @ResponseBody
    public List<EcKillDTO> spike(HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);

        String beginTime = request.getParameter("startTime");
        filters.add(new PropertyFilter("GES_saleStartTime", beginTime == null ? "20130613100438" : beginTime));
        filters.add(new PropertyFilter("EQL_iseckill", "1"));
        filters.add(new PropertyFilter("EQL_isValid", "1"));
        List<ItemSale> itemSales = itemSaleService.searchItemSale(page, filters).getResult();

        List<EcKillDTO> killDTOs = Lists.newArrayList();
        for (ItemSale item : itemSales) {
            EcKillDTO dto = BeanMapper.map(item, EcKillDTO.class);
            List<String> thumbs=Lists.newArrayList();
            for(SysFileImg fileImg:item.getSysFileImgs()){
//                for(SysFileImgThumb sysFileImgThumb:fileImg.getSysFileImgThumbs()){
//                    if(sysFileImgThumb.getImgSize().equals("50x50")){
//                        thumbs.add(sysFileImgThumb.getImgWebPath());
//                    }
//                }
                thumbs.add(fileImg.getFileName());
            }
            dto.setThumbs(thumbs);
            killDTOs.add(dto);
        }
        return killDTOs;
    }

    @RequestMapping(value = "spikeDetail", method = RequestMethod.GET)
    @ResponseBody
    public EcKillDTO spikeDetail(HttpServletRequest request) {
        String itemId = request.getParameter("itemId");

        ItemSale itemSale = itemSaleService.getItemSale(Long.valueOf(itemId == null ? "4849" : itemId));
        return BeanMapper.map(itemSale, EcKillDTO.class);
    }

    @Autowired
    public void setTypeService(TSysTypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setItemSaleService(ItemSaleService itemSaleService) {
        this.itemSaleService = itemSaleService;
    }
}
