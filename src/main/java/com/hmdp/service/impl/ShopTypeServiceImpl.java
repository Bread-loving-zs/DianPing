package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryTypeList() {
        //1.查Redis
        List<String> jsonList = stringRedisTemplate.opsForList().range("shopType:typeList", 0, -1);

        //2.判断是否存在
        if (jsonList != null && !jsonList.isEmpty()) {
            //3.存在，返回
            List<ShopType> shopTypeList = jsonList.stream()
                    .map(shopType -> JSONUtil.toBean(shopType, ShopType.class))
                    .collect(Collectors.toList());
            return Result.ok(shopTypeList);
        }
        //4.不存在，查数据库
        List<ShopType> shopTypeList = list();
        if (shopTypeList != null && !shopTypeList.isEmpty()) {
            //5.存在，存Redis后返回
            List<String> jsonStrList = shopTypeList.stream()
                    .map(JSONUtil::toJsonStr)
                    .collect(Collectors.toList());
            stringRedisTemplate.opsForList().rightPushAll("shopType:typeList", jsonStrList);
            return Result.ok(shopTypeList);
        }
        //6.不存在，返回失败
        return Result.fail("没有找到店铺类型");
    }
}
