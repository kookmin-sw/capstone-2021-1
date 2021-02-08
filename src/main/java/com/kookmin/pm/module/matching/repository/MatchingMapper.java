package com.kookmin.pm.module.matching.repository;

import com.kookmin.pm.module.matching.dto.MatchingDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface MatchingMapper {
    public List<MatchingDetails> searchMatchingWithLocationInfo(Map<String, Object> searchCondition);
}
