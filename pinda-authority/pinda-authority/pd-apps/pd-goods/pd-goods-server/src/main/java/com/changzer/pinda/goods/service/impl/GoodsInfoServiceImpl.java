package com.changzer.pinda.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changzer.pinda.goods.dao.GoodsInfoMapper;
import com.changzer.pinda.goods.entity.GoodsInfo;
import com.changzer.pinda.goods.service.GoodsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo> implements GoodsInfoService {
}
