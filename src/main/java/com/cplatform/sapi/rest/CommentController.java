package com.cplatform.sapi.rest;

import com.cplatform.sapi.entity.profile.TItemComment;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PageRequest;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.service.CommentService;
import com.cplatform.sapi.service.ProfileService;
import com.cplatform.sapi.util.ListFormat;
import com.cplatform.sapi.util.MediaTypes;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评价咨询
 * User: chenke
 * Date: 13-10-30
 * Time: 下午4:02
 *
 */
@Controller
@RequestMapping(value = "/api/v2/comment")
public class CommentController {

    private Page<TItemComment> commentPage = new Page<TItemComment>(10);

    @Autowired
    private CommentService commentService;

    /**
     * 商品为对象，评论查询
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public Map comments(HttpServletRequest request) {
        return  ListFormat.format(buildPage(request, "goods", "1"));
    }

    /**
     * 商品为对象，咨询查询
     * @param request
     * @return
     */
    @RequestMapping(value = "questions", method = RequestMethod.GET)
    @ResponseBody
    public Map questions(HttpServletRequest request) {
        return  ListFormat.format(buildPage(request, "goods", "2"));
    }

    /**
     * 用户为对象，评论查询
     * @param request
     * @return
     */
    @RequestMapping(value = "ulist", method = RequestMethod.GET)
    @ResponseBody
    public Map userComments(HttpServletRequest request) {
        return  ListFormat.format(buildPage(request, "user", "1"));
    }

    /**
     * 用户为对象，咨询查询
     * @param request
     * @return
     */
    @RequestMapping(value = "uquestions", method = RequestMethod.GET)
    @ResponseBody
    public Map userQuestions(HttpServletRequest request) {
        return  ListFormat.format(buildPage(request, "user", "2"));
    }

    private Page<TItemComment> buildPage(HttpServletRequest request, String who, String type) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        if ("goods".equals(who)){
            String saleId = request.getParameter("goodId");
            Validate.notNull(saleId,"商品ID不能为空");
            filters.add(new PropertyFilter("EQL_itemSale.id",saleId));
        }else if ("user".equals(who)){
            String userId = request.getParameter("userId");
            Validate.notNull(userId,"用户ID不能为空");
            filters.add(new PropertyFilter("EQS_userId",userId));
        }
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if ("1".equals(type)){
            filters.add(new PropertyFilter("EQI_type","1"));
        }else if ("2".equals(type)){
            filters.add(new PropertyFilter("EQI_type","2"));
        }
        if (StringUtils.isNotBlank(pageNo)){
            commentPage.setPageNo(Integer.valueOf(pageNo));
        }
        if (StringUtils.isNotBlank(pageSize)){
            commentPage.setPageSize(Integer.valueOf(pageSize));
        }
        commentPage.setOrderBy("updateTime");
        commentPage.setOrderDir(PageRequest.Sort.DESC);

        return commentService.searchItemComment(commentPage,filters);
    }
}

