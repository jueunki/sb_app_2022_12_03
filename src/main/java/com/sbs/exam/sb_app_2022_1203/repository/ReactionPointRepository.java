package com.sbs.exam.sb_app_2022_1203.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionPointRepository {
  @Select("""
      <script>
      SELECT IFNULL(SUM(RP.point), 0) AS s
      FROM reactionPoint AS RP
      WHERE RP.relTypeCode = ${relTypeCode}
      AND RP.relId = #{relId}
      AND RP.memberId = #{memberId}
      </script>
      """)
  public int getSumReactionPointByMemberId(@Param("relTypeCode")String relTypeCode, @Param("relId") int relId, @Param("memberId") int memberId);

}
